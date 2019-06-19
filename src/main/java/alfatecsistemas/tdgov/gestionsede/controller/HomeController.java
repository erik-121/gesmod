package alfatecsistemas.tdgov.gestionsede.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import alfatecsistemas.tdgov.gestionsede.DemoApplication;
import alfatecsistemas.tdgov.gestionsede.model.Area;
import alfatecsistemas.tdgov.gestionsede.model.GetAreas;
import alfatecsistemas.tdgov.gestionsede.model.Token;
import alfatecsistemas.tdgov.gestionsede.repository.TokenRepositoryImpl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Controller
public class HomeController {

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
	@GetMapping(value = "/test")
	public String showTable(Model model) {

		model.addAttribute("titulo", "Hola");

		return "tableview";
	}

	//@PostMapping(value = "/showAreas")
	public String getAllAreas2(Model model) {

		final Logger log = LoggerFactory.getLogger(DemoApplication.class);

		TokenRepositoryImpl token = new TokenRepositoryImpl();
		tokenId = token.getToken();

		final String URI_access = "http://"+serverIP+":"+serverPort+"/api/1.0/"+workspace+"/project/categories";

		RestTemplate restTemplate = new RestTemplate();

		try {
			HttpHeaders headers = new HttpHeaders();
			HttpEntity<String> myEntity = new HttpEntity<String>(headers);
			headers.add("Authorization", "Bearer " + tokenId.getAccess_token());

			ResponseEntity<JSONArray> response = restTemplate.exchange(URI_access, HttpMethod.GET, myEntity, JSONArray.class);
			System.out.println("Result - status (" + response.getStatusCode() + ") has body: " + response.hasBody());

		} catch (RestClientException e) {
			log.error("Error occurred while getting status of the microservice by URI {}", URI_access, e);
		}
		return null;

	}
	/**
	 * Method that gets the categories from the REST API at ProcessMaker, and gives it back to the model and view.
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@PostMapping(value = "/showAreas")
	public String getAllAreas(Model model) throws IOException {

		TokenRepositoryImpl token = new TokenRepositoryImpl();
		tokenId = token.getToken();

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("http://"+serverIP+":"+serverPort+"/api/1.0/"+workspace+"/project/categories").get()
				.addHeader("Authorization", "Bearer " + tokenId.getAccess_token()).build();

		Response response = client.newCall(request).execute();
		String respuesta = response.body().string();
		respuesta = "{lista:"+respuesta+"}";
		JSONObject jsonObject = new JSONObject(respuesta);
		model.addAttribute("response", jsonObject);

	

		return "areasView";

	}
}
