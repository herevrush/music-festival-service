package music.festival.backend.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import music.festival.backend.dto.BandDTO;
import music.festival.backend.dto.RecordLabel;
import music.festival.backend.helper.MusicFestivalApiHelper;
import music.festival.backend.model.MusicFestival;
import music.festival.backend.service.MusicFestivalService;

@Service
public class MusicFestivalServiceImpl implements MusicFestivalService {

	private static final String UNKNOWN = "Unknown";
	private static final Logger logger = LogManager.getLogger(MusicFestivalServiceImpl.class);

	private MusicFestivalApiHelper musicFestivalHelper;

	@Autowired
	public MusicFestivalServiceImpl(MusicFestivalApiHelper musicFestivalHelper) {
		this.musicFestivalHelper = musicFestivalHelper;
	}

	@Override
	public List<RecordLabel> getAllByRecordLabels() {
		MusicFestival[] festivals;
		festivals = musicFestivalHelper.getAllMusicFestivalData();
		Stream<MusicFestival> festivalStream = Arrays.stream(festivals);
		Map<String, RecordLabel> recordLabelMap = new TreeMap<>();
		festivalStream.forEach(festival -> {
			String festivalName = StringUtils.isEmpty(festival.getName()) ? UNKNOWN : festival.getName();
			festival.getBands().forEach(band -> {
				String recordLabelName = StringUtils.isEmpty(band.getRecordLabel()) ? UNKNOWN : band.getRecordLabel();
				if (recordLabelName != null) {
					RecordLabel recordLabel = getRecordLabelDto(recordLabelMap, recordLabelName);
					BandDTO bandDTO = getBandDto(recordLabel, band.getName());
					bandDTO.getMusicFestivals().add(festivalName);
				}
			});
		});
		logger.info("Data returned :{}", recordLabelMap.values());
		return new ArrayList<>(recordLabelMap.values());

	}

	private RecordLabel getRecordLabelDto(Map<String, RecordLabel> recordLabelMap, String recordLabelName) {
		RecordLabel recordLabel = null;
		if (recordLabelMap.containsKey(recordLabelName)) {
			recordLabel = recordLabelMap.get(recordLabelName);
		} else {
			recordLabel = new RecordLabel();
			recordLabel.setName(recordLabelName);
			recordLabelMap.put(recordLabelName, recordLabel);
		}
		return recordLabel;
	}

	private BandDTO getBandDto(RecordLabel recordLabel, String bandName) {
		BandDTO bandDto = null;
		Optional<BandDTO> bandsMatched = recordLabel.getBands().stream()
				.filter(bandObj -> bandObj.getName().equals(bandName)).findFirst();

		if (bandsMatched.isPresent()) {
			bandDto = bandsMatched.get();
		} else {
			bandDto = new BandDTO();
			bandDto.setName(bandName);
			recordLabel.getBands().add(bandDto);
			Collections.sort(recordLabel.getBands());
		}
		return bandDto;
	}
}
