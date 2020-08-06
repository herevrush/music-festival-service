package music.festival.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import music.festival.backend.dto.RecordLabel;
import music.festival.backend.service.MusicFestivalService;

@RestController
@RequestMapping("/music-festivals")
public class MusicFestivalController {

	private MusicFestivalService musicFestivalService;

	@Autowired
	public MusicFestivalController(MusicFestivalService musicFestivalService) {
		this.musicFestivalService = musicFestivalService;
	}

	@GetMapping
	public ResponseEntity<List<RecordLabel>> getAllByRecordLabels() {
		List<RecordLabel> recordLabels = musicFestivalService.getAllByRecordLabels();
		return new ResponseEntity<>(recordLabels, HttpStatus.OK);
	}

}
