package alfatecsistemas.tdgov.gestionsede.repository;

import org.springframework.web.client.RestTemplate;

import alfatecsistemas.tdgov.gestionsede.model.Category;;


public class AreaRepositoryImpl {
	
	private static final String URI_AREAS = "http://172.20.12.101:8888/api/1.0/tdgov/project/categories";
	
	private RestTemplate restTemplate = new RestTemplate();
	
	public Category[] getAllAreas() {
		
		
		
		return null;
	}

}
