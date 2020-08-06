package music.festival.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class MusicFestivalTest {

	private static final String BAND = "band";
	private static final String BAND_1 = "band1";
	private static final String RECORD_LABEL_1 = "record label 1";
	private static final String RECORD_LABEL_2 = "record label 2";
	private static final String MUSIC_FEST_1 = "myMusicFest1";

	@Test
	public void testSimpleMusicFestivalObjectCreation() {
		MusicFestival musicFestival = new MusicFestival();
		Band band = new Band();
		band.setName(BAND);
		band.setRecordLabel(RECORD_LABEL_2);
		Band band1 = new Band();
		band1.setName(BAND_1);
		band1.setRecordLabel(RECORD_LABEL_1);
		List<Band> bands = new ArrayList<>();
		bands.add(band);
		musicFestival.setName(MUSIC_FEST_1);
		musicFestival.setBands(bands);
		musicFestival.getBands().add(band1);

		assertEquals(MUSIC_FEST_1, musicFestival.getName());
		assertTrue(musicFestival.getBands().contains(band));
		assertTrue(musicFestival.getBands().contains(band1));
	}
}
