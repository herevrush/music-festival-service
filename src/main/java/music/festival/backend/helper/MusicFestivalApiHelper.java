package music.festival.backend.helper;

import java.util.Collections;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import music.festival.backend.exception.BackendDataException;
import music.festival.backend.model.MusicFestival;

@Component
public class MusicFestivalApiHelper {

	private static final Logger logger = LogManager.getLogger(MusicFestivalApiHelper.class);

	private RestTemplate restTemplate;

	@Value("${music.festivals.service.url}")
	private String musicFestivalWebsiteUrl;

	@Autowired
	public MusicFestivalApiHelper(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public MusicFestival[] getAllMusicFestivalData() {
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);
		MusicFestival[] festivalData = null;
		logger.info("getAllMusicFestivalData URL {}", musicFestivalWebsiteUrl);
		ResponseEntity<String> response = restTemplate.exchange(musicFestivalWebsiteUrl, HttpMethod.GET, entity,
				String.class);
		HttpStatus status = response.getStatusCode();
		if (status == HttpStatus.OK || status == HttpStatus.ACCEPTED) {
			String responseStr = response.getBody();
			logger.info("API call response: {}", responseStr);
			if (!StringUtils.isEmpty(responseStr.replaceAll("\"", ""))) {
				ObjectMapper mapper = new ObjectMapper();
				mapper.enable(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY);
				try {
					festivalData = mapper.reader().forType(MusicFestival[].class).readValue(responseStr);
				} catch (JsonProcessingException e) {
					logger.error("Error Response = {}", e.getLocalizedMessage());
					throw new BackendDataException("Received Empty response " + e.getLocalizedMessage());
				}
			} else {
				logger.error("Empty Response.");
				throw new BackendDataException("Received Empty response ");
			}
		} else {
			logger.error("Error Response = {}", status.getReasonPhrase());
			throw new BackendDataException("Something went wrong. " + status.getReasonPhrase());
		}
		logger.info("festivalData = {}", (Object[]) festivalData);
		return festivalData;
	}
}
