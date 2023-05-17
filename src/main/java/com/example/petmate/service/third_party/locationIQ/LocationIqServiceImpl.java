package com.example.petmate.service.third_party.locationIQ;

import com.example.petmate.model.response.LocationResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Slf4j
public class LocationIqServiceImpl implements LocationIqService {

	@Value("${locationiq.get-location}")
	private String getLocationUrl2;

	@Value("${geloky.get-location}")
	private String getLocationUrl;

	private final RestTemplate restTemplate;

	public LocationIqServiceImpl(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	@Override
	public LocationResponse getTheLocation(String address) {
		String encodedAddress = URLEncoder.encode(address, StandardCharsets.UTF_8);
		encodedAddress = encodedAddress.replace("+", "%20");

		HttpHeaders headers = new HttpHeaders();
		headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
		HttpEntity<?> httpEntity = new HttpEntity<>(headers);

		List<LocationResponse> response = executeRequest(new ParameterizedTypeReference<List<LocationResponse>>() {
		}, getLocationUrl, httpEntity, HttpMethod.GET, encodedAddress).orElse(null);

		return response.get(0);
	}

	private <T> Optional<T> executeRequest(ParameterizedTypeReference<T> reference, String url, HttpEntity entity,
			HttpMethod method, Object... uriVariables) {
		try {
			ResponseEntity<T> response = restTemplate.exchange(url, method, entity, reference, uriVariables);
			return Optional.of(response).map(HttpEntity::getBody);
		} catch (Exception ex) {
			log.error("error while executing tripLogService request for url {}, {}", url, uriVariables, ex);
		}
		return Optional.empty();
	}
}
