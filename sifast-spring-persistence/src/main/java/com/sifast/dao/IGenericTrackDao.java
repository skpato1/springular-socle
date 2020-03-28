package com.sifast.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sifast.enumeration.EventType;
import com.sifast.model.GenericTrack;

public interface IGenericTrackDao extends IGenericDao<GenericTrack, Integer> {

	Page<GenericTrack> findTrackByEntityId(int entityId, Pageable pageable);

	Page<GenericTrack> findTrackByEntityName(String entityName, Pageable pageable);

	List<GenericTrack> findTrackByEntityNameAndIdAndEventType(String entityName, int entityId, EventType eventType);

	Page<GenericTrack> findTrackByEntityNameAndEntityId(String entityName, int entityId, Pageable pageable);

}
