package com.sifast.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.sifast.dao.IGenericTrackDao;
import com.sifast.enumeration.EventType;
import com.sifast.model.GenericTrack;
import com.sifast.service.IGenericTrackService;

@Service("genericTrackService")
public class GenericTrackService extends GenericService<GenericTrack, Integer> implements IGenericTrackService {

	@Autowired
	private IGenericTrackDao genericTrackRepository;

	@Override
	public Page<GenericTrack> findTrackByEntityId(int entityId, int offset, int limit) {
		return genericTrackRepository.findTrackByEntityId(entityId, PageRequest.of(offset, limit));
	}

	@Override
	public Page<GenericTrack> findTrackByEntityName(String entityName, int offset, int limit) {
		return genericTrackRepository.findTrackByEntityName(entityName, PageRequest.of(offset, limit));
	}

	@Override
	public List<GenericTrack> findTrackByEntityNameAndIdAndEventType(String entityName, int entityId,
			EventType eventType) {
		return genericTrackRepository.findTrackByEntityNameAndIdAndEventType(entityName, entityId, eventType);
	}

	@Override
	public int countEntities() {
		return (int) genericTrackRepository.count();
	}

	@Override
	public Page<GenericTrack> findTrackByEntityNameAndEntityId(String entityName, int entityId, int offset, int limit) {
		return genericTrackRepository.findTrackByEntityNameAndEntityId(entityName, entityId,
				PageRequest.of(offset, limit));
	}
}
