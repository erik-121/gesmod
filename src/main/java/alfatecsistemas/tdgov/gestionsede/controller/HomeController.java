package alfatecsistemas.tdgov.gestionsede.controller;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
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

    @RequestMapping(value = "/areas", method = RequestMethod.POST)
    public void index() {

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

		HttpEntity<String> myEntity = new HttpEntity<String>(body);

		RestTemplate restTemplate = new RestTemplate();

		Token gainedToken = restTemplate.postForObject(URI_acces, myEntity, Token.class);
		
		log.info(body);

		System.out.println(gainedToken.getAccess_token());
	}
	
	@GetMapping(value = "/mostrar")
    public Token getToken() {

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

		HttpEntity<String> myEntity = new HttpEntity<String>(body);

		RestTemplate restTemplate = new RestTemplate();

		Token gainedToken = restTemplate.postForObject(URI_acces, myEntity, Token.class);

		System.out.println(gainedToken.getAccess_token());

		return gainedToken;
    }

}
