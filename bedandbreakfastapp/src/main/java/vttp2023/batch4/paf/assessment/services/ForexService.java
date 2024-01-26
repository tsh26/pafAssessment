package vttp2023.batch4.paf.assessment.services;

import org.bson.json.JsonObject;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


@Service
public class ForexService {

	// TODO: Task 5 

	// Constructing the URL for REST API: https://api.frankfurter.app/latest?from=AUD&to=SGD
	private final String BASE_URL="https://api.frankfurter.app/latest?from=";
	private final RestTemplate restTemplate = new RestTemplate();

	public float convert(String from, String to, float amount) {
		String url = BASE_URL + from +"&to=" + to;

		ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
		
		JsonObject jsonObject = new JsonObject(response.getBody());

		return -1000f;
	}

}
