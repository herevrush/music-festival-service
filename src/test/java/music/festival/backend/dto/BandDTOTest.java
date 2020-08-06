package music.festival.backend.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Set;
import java.util.TreeSet;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class BandDTOTest {

	private static final String BAND = "band";
	private static final String MUSIC_FEST_1 = "myMusicFest1";

	private static final String MUSIC_FEST_2 = "MusicFest2";

	@Test
	public void testSimpleBandDtoObjectCreation() {
		BandDTO band = new BandDTO();
		band.setName(BAND);
		Set<String> musicFests = new TreeSet<>();
		musicFests.add(MUSIC_FEST_1);
		band.setMusicFestivals(musicFests);
		band.getMusicFestivals().add(MUSIC_FEST_2);
		assertEquals(BAND, band.getName());
		assertTrue(band.getMusicFestivals().contains(MUSIC_FEST_1));
		assertTrue(band.getMusicFestivals().contains(MUSIC_FEST_2));
	}
}
