package music.festival.backend.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.core.JsonProcessingException;

import music.festival.backend.dto.BandDTO;
import music.festival.backend.dto.RecordLabel;
import music.festival.backend.exception.BackendDataException;
import music.festival.backend.helper.MusicFestivalApiHelper;
import music.festival.backend.model.Band;
import music.festival.backend.model.MusicFestival;

@ExtendWith(MockitoExtension.class)
public class MusicFestivalServiceImplTest {

	@Mock
	private MusicFestivalApiHelper musicFestivalHelper;

	@InjectMocks
	private MusicFestivalServiceImpl MusicFestivalService;

	private MusicFestival[] musicFestivals;
	private MusicFestival mf1;
	private MusicFestival mf2;

	private List<RecordLabel> recordLabels;

	@BeforeEach
	void init() {
		MusicFestivalService = new MusicFestivalServiceImpl(musicFestivalHelper);
		musicFestivals = new MusicFestival[2];
		mf1 = new MusicFestival();
		mf1.setName("LOL-palooza");
		Band band1 = new Band();
		band1.setName("FrankJupiter");
		band1.setRecordLabel("PacificRecords");
		List<Band> bands1 = new ArrayList<>();
		bands1.add(band1);
		mf1.setBands(bands1);
		musicFestivals[0] = mf1;

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
		musicFestivals[1] = mf2;

		recordLabels = new ArrayList<>();
		RecordLabel rl1 = new RecordLabel();
		rl1.setName("MarnerSis.Recording");
		ArrayList<BandDTO> bs1 = new ArrayList<>();
		BandDTO b1 = new BandDTO();
		b1.getMusicFestivals().add("SmallNightIn");
		b1.setName("GreenMildColdCapsicum");
		bs1.add(b1);
		BandDTO b2 = new BandDTO();
		b2.getMusicFestivals().add("SmallNightIn");
		b2.setName("WildAntelope");
		bs1.add(b2);
		rl1.setBands(bs1);

		RecordLabel rl2 = new RecordLabel();
		rl2.setName("PacificRecords");
		ArrayList<BandDTO> bs2 = new ArrayList<>();
		BandDTO b11 = new BandDTO();
		b11.getMusicFestivals().add("LOL-palooza");
		b11.setName("FrankJupiter");
		bs2.add(b11);
		rl2.setBands(bs2);
		recordLabels.add(rl1);
		recordLabels.add(rl2);
	}

	@Test
	public void testGetAllByRecordLabelsError() throws JsonProcessingException {

		when(musicFestivalHelper.getAllMusicFestivalData()).thenThrow(BackendDataException.class);
		Assertions.assertThrows(BackendDataException.class, () -> {
			MusicFestivalService.getAllByRecordLabels();
		});
	}

	@Test()
	public void testGetMusicFestivalData() throws JsonProcessingException {
		when(musicFestivalHelper.getAllMusicFestivalData()).thenReturn(musicFestivals);
		List<RecordLabel> actualRecordLabels = MusicFestivalService.getAllByRecordLabels();

		System.out.println(actualRecordLabels);
		System.out.println(recordLabels);
		assertEquals(recordLabels.get(0).getName(), actualRecordLabels.get(0).getName());
		assertEquals(recordLabels.get(1).getName(), actualRecordLabels.get(1).getName());
		assertEquals(recordLabels.get(0).getBands().get(0).getName(),
				actualRecordLabels.get(0).getBands().get(0).getName());
		assertEquals(recordLabels.get(0).getBands().get(0).getMusicFestivals(),
				actualRecordLabels.get(0).getBands().get(0).getMusicFestivals());

		assertEquals(recordLabels.get(0).getBands().get(1).getName(),
				actualRecordLabels.get(0).getBands().get(1).getName());
		assertEquals(recordLabels.get(0).getBands().get(1).getMusicFestivals(),
				actualRecordLabels.get(0).getBands().get(1).getMusicFestivals());

		assertEquals(recordLabels.get(1).getBands().get(0).getName(),
				actualRecordLabels.get(1).getBands().get(0).getName());
		assertEquals(recordLabels.get(1).getBands().get(0).getMusicFestivals(),
				actualRecordLabels.get(1).getBands().get(0).getMusicFestivals());

	}
}
