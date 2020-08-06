package music.festival.backend.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;

import music.festival.backend.exception.BackendDataException;
import music.festival.backend.model.Band;
import music.festival.backend.model.MusicFestival;

@ExtendWith(MockitoExtension.class)
public class MusicFestivalApiHelperTest {

	private static final String TEST_URL = "http://test";

	@Mock
	private RestTemplate restTemplate;

	@InjectMocks
	private MusicFestivalApiHelper musicFestivalApiHelper;

	private MusicFestival[] expetedMusicFestivals;
	private MusicFestival mf1;
	private MusicFestival mf2;

	@BeforeEach
	void init() {
		musicFestivalApiHelper = new MusicFestivalApiHelper(restTemplate);
		ReflectionTestUtils.setField(musicFestivalApiHelper, "musicFestivalWebsiteUrl", TEST_URL);

		expetedMusicFestivals = new MusicFestival[2];
		mf1 = new MusicFestival();
		mf1.setName("LOL-palooza");
		Band band1 = new Band();
		band1.setName("FrankJupiter");
		band1.setRecordLabel("PacificRecords");
		List<Band> bands1 = new ArrayList<>();
		bands1.add(band1);
		mf1.setBands(bands1);
		expetedMusicFestivals[0] = mf1;

		mf2 = new MusicFestival();
		mf2.setName("SmallNightIn");
		Band band2 = new Band();
		band2.setName("WildAntelope");
		band2.setRecordLabel("MarnerSis.Recording");
		Band band3 = new Band();
		band3.setName("GreenMildColdCapsicum");
		band3.setRecordLabel("MarnerSis.Recording");
		List<Band> bands2 = new ArrayList<>();
		bands2.add(band2);
		bands2.add(band3);
		mf2.setBands(bands2);
		expetedMusicFestivals[1] = mf2;
	}

	@Test()
	public void testGetMusicFestivalDataErrorResponse() throws JsonProcessingException {
		ResponseEntity<String> responseEntity = new ResponseEntity<String>("\"\"", HttpStatus.BAD_GATEWAY);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);

		when(restTemplate.exchange(TEST_URL, HttpMethod.GET, entity, String.class)).thenReturn(responseEntity);
		Assertions.assertThrows(BackendDataException.class, () -> {
			musicFestivalApiHelper.getAllMusicFestivalData();
		});
	}

	@Test()
	public void testGetMusicFestivalJaonErrorResponse() throws JsonProcessingException {
		ResponseEntity<String> responseEntity = new ResponseEntity<String>("\"Str\"", HttpStatus.ACCEPTED);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);

		when(restTemplate.exchange(TEST_URL, HttpMethod.GET, entity, String.class)).thenReturn(responseEntity);
		Assertions.assertThrows(BackendDataException.class, () -> {
			musicFestivalApiHelper.getAllMusicFestivalData();
		});
	}

	@Test()
	public void testGetMusicFestivalDataEmpty() throws JsonProcessingException {
		ResponseEntity<String> responseEntity = new ResponseEntity<String>("\"\"", HttpStatus.ACCEPTED);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);

		when(restTemplate.exchange(TEST_URL, HttpMethod.GET, entity, String.class)).thenReturn(responseEntity);
		Assertions.assertThrows(BackendDataException.class, () -> {
			musicFestivalApiHelper.getAllMusicFestivalData();
		});
	}

	@Test()
	public void testGetMusicFestivalData() throws JsonProcessingException {
		ResponseEntity<String> responseEntity = new ResponseEntity<String>(
				"[{\"name\":\"LOL-palooza\",\"bands\":[{\"name\":\"FrankJupiter\",\"recordLabel\":\"PacificRecords\"}]},{\"name\":\"SmallNightIn\",\"bands\":[{\"name\":\"WildAntelope\",\"recordLabel\":\"MarnerSis.Recording\"},{\"name\":\"GreenMildColdCapsicum\",\"recordLabel\":\"MarnerSis.Recording\"}]}]",
				HttpStatus.ACCEPTED);
		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
		HttpEntity<String> entity = new HttpEntity<>(headers);

		when(restTemplate.exchange(TEST_URL, HttpMethod.GET, entity, String.class)).thenReturn(responseEntity);
		MusicFestival[] musicFestivals = musicFestivalApiHelper.getAllMusicFestivalData();

		assertEquals(mf1.getName(), musicFestivals[0].getName());
		assertEquals(mf2.getName(), musicFestivals[1].getName());

		assertEquals(musicFestivals[0].getBands().get(0).getName(),
				expetedMusicFestivals[0].getBands().get(0).getName());
		assertEquals(musicFestivals[0].getBands().get(0).getRecordLabel(),
				expetedMusicFestivals[0].getBands().get(0).getRecordLabel());

		assertEquals(musicFestivals[1].getBands().get(0).getName(),
				expetedMusicFestivals[1].getBands().get(0).getName());
		assertEquals(musicFestivals[1].getBands().get(0).getRecordLabel(),
				expetedMusicFestivals[1].getBands().get(0).getRecordLabel());

		assertEquals(musicFestivals[1].getBands().get(1).getName(),
				expetedMusicFestivals[1].getBands().get(1).getName());
		assertEquals(musicFestivals[1].getBands().get(1).getRecordLabel(),
				expetedMusicFestivals[1].getBands().get(1).getRecordLabel());

	}
}
