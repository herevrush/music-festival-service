package music.festival.backend.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BandTest {

	private static final String BAND = "band";
	private static final String RECORD_LABEL_1 = "record label 1";

	@Test
	public void testSimpleBandCreation() {
		Band band = new Band();
		band.setName(BAND);
		band.setRecordLabel(RECORD_LABEL_1);
		assertEquals(BAND, band.getName());
		assertEquals(RECORD_LABEL_1, band.getRecordLabel());
	}
}
