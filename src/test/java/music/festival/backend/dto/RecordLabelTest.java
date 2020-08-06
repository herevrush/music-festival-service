package music.festival.backend.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RecordLabelTest {

	private static final String BAND = "Myband";
	private static final String BAND_1 = "band1";
	private static final String RECORD_LABEL_1 = "record label 1";
	private static final String MUSIC_FEST_1 = "yourMusicFest1";
	private static final String MUSIC_FEST_2 = "myMusicFest2";

	private RecordLabel recordLabel;

	@BeforeEach
	void init() {
		recordLabel = new RecordLabel();
		recordLabel.setName(RECORD_LABEL_1);
		BandDTO band = new BandDTO();
		band.setName(BAND);
		Set<String> musicFests = new TreeSet<>();
		musicFests.add(MUSIC_FEST_1);
		band.setMusicFestivals(musicFests);
		BandDTO band1 = new BandDTO();
		band1.setName(BAND_1);
		Set<String> musicFests1 = new TreeSet<>();
		musicFests1.add(MUSIC_FEST_1);
		musicFests1.add(MUSIC_FEST_2);
		band1.setMusicFestivals(musicFests1);
		List<BandDTO> bands = new ArrayList<>();
		bands.add(band);
		recordLabel.setBands(bands);
		recordLabel.getBands().add(band1);
	}

	@Test
	public void testRecordLabelCreation() {
		assertEquals(RECORD_LABEL_1, recordLabel.getName());
		assertEquals(BAND, recordLabel.getBands().get(0).getName());
		assertTrue(recordLabel.getBands().get(0).getMusicFestivals().contains(MUSIC_FEST_1));

		assertEquals(BAND_1, recordLabel.getBands().get(1).getName());
		assertTrue(recordLabel.getBands().get(1).getMusicFestivals().contains(MUSIC_FEST_1));
		assertTrue(recordLabel.getBands().get(1).getMusicFestivals().contains(MUSIC_FEST_2));

	}

	@Test
	public void testBandsSorting() {
		Collections.sort(recordLabel.getBands());
		assertEquals(BAND, recordLabel.getBands().get(0).getName());
		assertTrue(recordLabel.getBands().get(0).getMusicFestivals().contains(MUSIC_FEST_1));

		assertEquals(BAND_1, recordLabel.getBands().get(1).getName());
		assertTrue(recordLabel.getBands().get(1).getMusicFestivals().contains(MUSIC_FEST_1));
		assertTrue(recordLabel.getBands().get(1).getMusicFestivals().contains(MUSIC_FEST_2));

	}
}
