package alfatecsistemas.tdgov.gestionsede.controller;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import alfatecsistemas.tdgov.gestionsede.DemoApplication;
import alfatecsistemas.tdgov.gestionsede.model.Area;
import alfatecsistemas.tdgov.gestionsede.model.Token;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@RestController
public class HomeController {

	Token tokenAuth;

	@RequestMapping("/token")
	public void getToken() {

		final Logger log = LoggerFactory.getLogger(DemoApplication.class);

		final String URI_access = "http://172.20.12.101:8888/tdgov/oauth2/token";

		JSONObject obj = new JSONObject();

		obj.put("grant_type", "password");
		obj.put("scope", "*");
		obj.put("client_id", "AVLTFROHGPCTDCIQTFAKLZWPDXBHBKAB");
		obj.put("client_secret", "2389151395ce7abe8b950b7015555635");
		obj.put("username", "admin");
		obj.put("password", "Oviedo$2000");

		String body = obj.toString();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> myEntity = new HttpEntity<String>(body, headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity<String> entity = restTemplate.postForEntity(URI_access, myEntity, String.class);

		if (!entity.getStatusCode().equals(HttpStatus.OK)) {
			log.error("Problemas con la petición al servidor. Http status:" + entity.getStatusCode());

		} else {
			String bodyResponse = entity.getBody();
			String headersResponse = entity.getHeaders().toString();
			log.info("----Response body----");
			log.info(bodyResponse);
			log.info("----Reponse headers----");
			log.info(headersResponse);

			try {
				tokenAuth = new ObjectMapper().readValue(bodyResponse, Token.class);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	//@RequestMapping()
	public String getAllAreas2() {

		final Logger log = LoggerFactory.getLogger(DemoApplication.class);

		final String URI_access = "http://172.20.12.101:8888/api/1.0/tdgov/project/categories";

		HttpHeaders headers = new HttpHeaders();
		headers.set("Authorization", "Bearer " + tokenAuth.getAccess_token());

		HttpEntity<String> myEntity = new HttpEntity<String>(headers);

		RestTemplate restTemplate = new RestTemplate();

		// String entity = " KK";

		try {
			ResponseEntity<String> entity = restTemplate.getForEntity(URI_access, String.class, myEntity);
		} catch (RestClientException e) {
			log.error("Error occurred while getting status of the microservice by URI {}", URI_access, e);
		}

		String entity = "";
		return " " + tokenAuth.getAccess_token() + "\n" + entity;

	}

	@RequestMapping(value = "/showAreas", method = RequestMethod.POST)
	public String getAllAreas() throws IOException {

		OkHttpClient client = new OkHttpClient();

		Request request = new Request.Builder().url("http://172.20.12.101:8888/api/1.0/tdgov/project/categories").get()
				.addHeader("Authorization", "Bearer " + tokenAuth.getAccess_token())
				.addHeader("User-Agent", "PostmanRuntime/7.13.0").addHeader("Accept", "*/*")
				.addHeader("Cache-Control", "no-cache")
				.addHeader("Postman-Token", "76c2c46a-4454-491d-8f1e-e69f791230e4,79f69674-af82-4647-b852-2d760ffad500")
				.addHeader("Host", "172.20.12.101:8888").addHeader("accept-encoding", "gzip, deflate")
				.addHeader("Connection", "keep-alive").addHeader("cache-control", "no-cache").build();

		Response response = client.newCall(request).execute();
		return response.body().string();

		//Area areas = new ObjectMapper().readValue(response.body().string(), Area.class);

		//JSONObject jsonObj = new JSONObject(response.body().string());

		//return jsonObj;
	}
}
