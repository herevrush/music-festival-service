package music.festival.backend.service;

import java.util.List;

import music.festival.backend.dto.RecordLabel;

public interface MusicFestivalService {

	List<RecordLabel> getAllByRecordLabels();
}
