package alfatecsistemas.tdgov.gestionsede.repository;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import alfatecsistemas.tdgov.gestionsede.DemoApplication;
import alfatecsistemas.tdgov.gestionsede.model.Token;

public class TokenRepositoryImpl {

    @Value("${processmaker.server.ip}")
	private String serverIP;

	@Value("${processmaker.server.port}")
	private String serverPort;

	@Value("${processmaker.workspace}")
	private String workspace;

    public Token getToken() {

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

        try {
            ResponseEntity<String> entity = restTemplate.postForEntity(URI_access, myEntity, String.class);

            if (!entity.getStatusCode().equals(HttpStatus.OK)) {
                log.error("Problemas con la petición al servidor. Http status:" + entity.getStatusCode());
                return null;
            } else {
                String bodyResponse = entity.getBody();
                String headersResponse = entity.getHeaders().toString();
                log.info("Getting Token...");
                //log.info("----Response body----");
                //log.info(bodyResponse);
                log.info("----Reponse headers----");
                log.info(headersResponse);
    
                try {
                    Token tokenAuth = new ObjectMapper().readValue(bodyResponse, Token.class);
                    log.info("Token obtained OK");
                    return tokenAuth;
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }

        } catch (HttpStatusCodeException exception) {
            int statusCode = exception.getStatusCode().value();
            log.error("No se ha podido contactar con el servidor. Error code: " + statusCode, exception);
        }
        return null;



    }

}