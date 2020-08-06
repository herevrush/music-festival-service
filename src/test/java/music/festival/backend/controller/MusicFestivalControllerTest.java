package music.festival.backend.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.AutoConfigureWebClient;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import music.festival.backend.dto.BandDTO;
import music.festival.backend.dto.RecordLabel;
import music.festival.backend.exception.BackendDataException;
import music.festival.backend.service.impl.MusicFestivalServiceImpl;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = MusicFestivalController.class)
@AutoConfigureWebClient
public class MusicFestivalControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MusicFestivalServiceImpl musicFestivalService;

	@InjectMocks
	private MusicFestivalController musicFestivalController;

	private List<RecordLabel> recordLabels;

	@BeforeEach
	void init() {
		musicFestivalController = new MusicFestivalController(musicFestivalService);
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
	public void testControllerReturnsError() throws Exception {
		when(musicFestivalService.getAllByRecordLabels()).thenThrow(BackendDataException.class);
		mockMvc.perform(get("/music-festivals").contentType("application/json")).andExpect(status().is4xxClientError());
	}

	@Test
	public void testControllerReturnsAllOk() throws Exception {
		when(musicFestivalService.getAllByRecordLabels()).thenReturn(recordLabels);
		mockMvc.perform(get("/music-festivals").contentType("application/json")).andExpect(status().isOk())
				.andExpect(content().string(
						"[{\"name\":\"MarnerSis.Recording\",\"bands\":[{\"name\":\"GreenMildColdCapsicum\",\"musicFestivals\":[\"SmallNightIn\"]},{\"name\":\"WildAntelope\",\"musicFestivals\":[\"SmallNightIn\"]}]},{\"name\":\"PacificRecords\",\"bands\":[{\"name\":\"FrankJupiter\",\"musicFestivals\":[\"LOL-palooza\"]}]}]"));
	}

}
