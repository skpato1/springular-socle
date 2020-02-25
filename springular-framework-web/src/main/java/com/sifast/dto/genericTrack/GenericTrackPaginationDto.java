package com.sifast.dto.genericTrack;

import java.util.List;

public class GenericTrackPaginationDto {

	private long totalNumber;
	private List<GenericTrackDto> genericTrackDtos;

	public long getTotalNumber() {
		return totalNumber;
	}

	public void setTotalNumber(long totalNumber) {
		this.totalNumber = totalNumber;
	}

	public List<GenericTrackDto> getGenericTrackDtos() {
		return genericTrackDtos;
	}

	public void setGenericTrackDtos(List<GenericTrackDto> genericTrackDtos) {
		this.genericTrackDtos = genericTrackDtos;
	}

}
