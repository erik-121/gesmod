package alfatecsistemas.tdgov.gestionsede.controller;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import alfatecsistemas.tdgov.gestionsede.DemoApplication;
import alfatecsistemas.tdgov.gestionsede.model.Token;

@RestController
public class HomeController {

    @RequestMapping(value = "/showAreas", method = RequestMethod.POST)
    public String index() {

		final Logger log = LoggerFactory.getLogger(DemoApplication.class);

		final String URI_acces = "http://172.20.12.101:8888/tdgov/oauth2/token";

		JSONObject obj = new JSONObject();

		obj.put("grant_type","password");
		obj.put("scope","*");
		obj.put("client_id","AVLTFROHGPCTDCIQTFAKLZWPDXBHBKAB");
		obj.put("client_secret","2389151395ce7abe8b950b7015555635");
		obj.put("username","admin");
		obj.put("password","Oviedo$2000");

		String body = obj.toString();
		
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);		

		HttpEntity<String> myEntity = new HttpEntity<String>(body, headers);

		RestTemplate restTemplate = new RestTemplate();

		ResponseEntity entity = restTemplate.postForEntity(URI_acces, myEntity, String.class);

		if(entity.getStatusCode().value() != 200){
			log.error("El servidor no responde ");
			break;
		}else{
			List<String> acces_token = entity.getHeaders().get("access_token");
		}
		return acces_token;
	}
	
	@GetMapping(value = "/mostrar")
    public void getToken() {

		final Logger log = LoggerFactory.getLogger(DemoApplication.class);

		final String URI_acces = "http://172.20.12.101:8888/tdgov/oath2/token";

		JSONObject obj = new JSONObject();

		obj.put("grant_type","password");
		obj.put("scope","*");
		obj.put("client_id","AVLTFROHGPCTDCIQTFAKLZWPDXBHBKAB");
		obj.put("client_secret","2389151395ce7abe8b950b7015555635");
		obj.put("username","admin");
		obj.put("password","Oviedo$2000");

		String body = obj.toString();


    }

}
