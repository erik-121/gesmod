package alfatecsistemas.tdgov.gestionsede.controller;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.Principal;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.security.auth.x500.X500Principal;
import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import org.bouncycastle.asn1.x500.RDN;
import org.bouncycastle.asn1.x500.X500Name;
import org.bouncycastle.asn1.x500.style.BCStyle;
import org.bouncycastle.asn1.x500.style.IETFUtils;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import alfatecsistemas.tdgov.gestionsede.model.Token;
import alfatecsistemas.tdgov.gestionsede.DemoApplication;
import alfatecsistemas.tdgov.gestionsede.model.Category;
import alfatecsistemas.tdgov.gestionsede.repository.TokenRepositoryImpl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Controller
public class HomeController {

	final Logger log = LoggerFactory.getLogger(DemoApplication.class);

	public static Token tokenId;

	@Value("${processmaker.server.ip}")
	private String serverIP;

	@Value("${processmaker.server.port}")
	private String serverPort;

	@Value("${processmaker.workspace}")
	private String workspace;
	
	

    @RequestMapping(value={"/login"}, method = RequestMethod.GET)
    public ModelAndView login(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
        return modelAndView;
    }

	/**
	 * Method that gets the categories from the REST API at ProcessMaker, and gives
	 * it back to the model and view.
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@GetMapping(value = "/showCategories")
	public String getAllAreas(Model model, @RequestParam(name = "username", required = false) String user)
			throws IOException {

		TokenRepositoryImpl token = new TokenRepositoryImpl();
		tokenId = token.getToken();

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder()
				.url("http://" + serverIP + ":" + serverPort + "/api/1.0/" + workspace + "/project/categories").get()
				.addHeader("Authorization", "Bearer " + tokenId.getAccess_token()).build();

		Response response = client.newCall(request).execute();
		String respuesta = response.body().string();

		Gson gson = new Gson();

		Category[] cats = gson.fromJson(respuesta, Category[].class);

		for (Category cat : cats) {
			System.out.println(cat.getCatName());
		}

		model.addAttribute("categories", cats);

		return "listAreas";

	}
    @RequestMapping(value={"/newCategory"}, method = RequestMethod.GET)
    public ModelAndView newCategory(){
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("newCategory");
        return modelAndView;
    }

	@PostMapping(value = "/createCategory")
	public String createNewCategory(@RequestParam(name = "newCategoryName") String newCategory) throws IOException {

		Category newOne = new Category();
		newOne.setCatName(newCategory);

		Gson gson = new Gson();
		String json = gson.toJson(newOne);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + tokenId.getAccess_token());

		HttpEntity<String> myEntity = new HttpEntity<String>(json, headers);

		RestTemplate restTemplate = new RestTemplate();

		try {
			ResponseEntity<String> entity = restTemplate.postForEntity(
					"http://" + serverIP + ":" + serverPort + "/api/1.0/" + workspace + "/project/category", myEntity,
					String.class);
		} catch (RestClientException e) {
			log.error("Error:  " + e.toString());
		}

		log.info("User ordered to create new Category: " + newCategory);

		return "redirect:/";

	}

	@ResponseBody
	@PostMapping(params = "removeCategory", path = { "/actions/delete", "/actions/delete/{id}" })
	public void removeCategory(@RequestParam("removeCategory") String index, HttpServletRequest request) {

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Bearer " + tokenId.getAccess_token());
		HttpEntity<String> myEntity = new HttpEntity<String>(headers);
		RestTemplate restTemplate = new RestTemplate();

		try {
			restTemplate.exchange(
					"http://" + serverIP + ":" + serverPort + "/api/1.0/" + workspace + "/project/category/" + index,
					HttpMethod.DELETE, myEntity, String.class);
			log.info("Borrada la categoría con UID:" + index);
		} catch (RestClientException e) {
			log.error("Error:  " + e.toString());
		}
		// order.items.remove(index);
		/*
		 * if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) { return
		 * "order::#items"; } else { return "order"; }
		 */
	}

	// @ResponseBody
	@GetMapping(params = "editCategory", path = { "/actions/edit", "actions/edit/{id}" })
	public String editCategory(Model model, @RequestParam("editCategory") String id, HttpServletRequest request) {

		return "editCategory";
	}

	@PostMapping(value = "/auth/middle")
	public String loginCert(@RequestParam("certificadoUsuario") String cert,@RequestParam("url") String url) throws CertificateException {
		
		byte encodedCert[] = Base64.getDecoder().decode(cert);
		ByteArrayInputStream inputStream = new ByteArrayInputStream(encodedCert);
		
		CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
	    X509Certificate certificate = (X509Certificate)certFactory.generateCertificate(inputStream);
		
		X500Name x500name = new JcaX509CertificateHolder(certificate).getSubject();
		RDN cn = x500name.getRDNs(BCStyle.CN)[0];

		String userName = IETFUtils.valueToString(cn.getFirst().getValue());
		
		return "redirect:/" + url + "?username=" + userName;
		
	}

}
