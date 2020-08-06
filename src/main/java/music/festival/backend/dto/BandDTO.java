package music.festival.backend.dto;

import java.util.Set;
import java.util.TreeSet;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

public class BandDTO implements Comparable<BandDTO> {

	private String name;
	private Set<String> musicFestivals;

	public BandDTO() {
		musicFestivals = new TreeSet<>();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<String> getMusicFestivals() {
		return musicFestivals;
	}

	public void setMusicFestivals(Set<String> musicFestivals) {
		this.musicFestivals = musicFestivals;
	}

	@Override
	public int compareTo(BandDTO bandDto) {
		return this.getName().compareTo(bandDto.getName());
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this);
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj, true);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this, true);
	}

}
