package com.sifast.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.domain.Page;

import com.sifast.enumeration.EventType;
import com.sifast.model.GenericTrack;

@Transactional
public interface IGenericTrackService extends IGenericService<GenericTrack, Integer> {

	Page<GenericTrack> findTrackByEntityId(int entityId, int offset, int limit);

	Page<GenericTrack> findTrackByEntityName(String entityName, int offset, int limit);

	List<GenericTrack> findTrackByEntityNameAndIdAndEventType(String entityName, int entityId, EventType eventType);

	Page<GenericTrack> findTrackByEntityNameAndEntityId(String entityName, int entityId, int offset, int limit);

}