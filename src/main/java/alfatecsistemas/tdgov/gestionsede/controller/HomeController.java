package alfatecsistemas.tdgov.gestionsede.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;

import com.google.gson.Gson;

import org.apache.commons.logging.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

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

	/**
	 * Sample method for using spring MVC
	 * 
	 * @param model
	 * @return
	 */
	@GetMapping(value = "/login")
	public String showTable(Model model) {

		model.addAttribute("username", "Hola");

		return "login";
	}

	/**
	 * Method that gets the categories from the REST API at ProcessMaker, and gives it back to the model and view.
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/showAreas")
	public String getAllAreas(Model model, @RequestParam(name = "user") String user) throws IOException {

		TokenRepositoryImpl token = new TokenRepositoryImpl();
		tokenId = token.getToken();

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("http://"+serverIP+":"+serverPort+"/api/1.0/"+workspace+"/project/categories").get()
				.addHeader("Authorization", "Bearer " + tokenId.getAccess_token()).build();

		Response response = client.newCall(request).execute();
		String respuesta = response.body().string();

		Gson gson = new Gson();

		Category[] cats = gson.fromJson(respuesta, Category[].class);

		for(Category cat : cats){
			System.out.println(cat.getCatName());
		}

		model.addAttribute("categories", cats);

		model.addAttribute("username", user);
	
		return "listAreas";

	}
	@PostMapping(value = "/newArea")
	public String newArea(Model model,@RequestParam(name = "newAreaName")String newArea) throws IOException {

		Category newOne = new Category();
		newOne.setCatName(newArea);

		Gson gson = new Gson();
		String json = gson.toJson(newOne);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		headers.add("Authorization", "Bearer " + tokenId.getAccess_token());
		
		HttpEntity<String> myEntity = new HttpEntity<String>(json, headers);

		RestTemplate restTemplate = new RestTemplate();

		try {
			ResponseEntity<String> entity = restTemplate.postForEntity("http://"+serverIP+":"+serverPort+"/api/1.0/"+workspace+"/project/category", myEntity, String.class);
		} catch (RestClientException  e) {
			log.error("Error:  " + e.toString());
		}
		
		log.info("User ordered to create new Area: " + newArea);

		return "login";

	}
	
	@ResponseBody
	@PostMapping(params = "removeCategory", path = {"/actions", "/actions/{id}"})
    public String removeCategory(@RequestParam("removeCategory") String index, HttpServletRequest request) {

		System.out.println("Se BORRA virtualmente la categoría:" + index);

		return "Se BORRA virtualmente la categoría:" + index;
        //order.items.remove(index);
        /*if (AJAX_HEADER_VALUE.equals(request.getHeader(AJAX_HEADER_NAME))) {
            return "order::#items";
        } else {
            return "order";
        }*/
    }
	
}
