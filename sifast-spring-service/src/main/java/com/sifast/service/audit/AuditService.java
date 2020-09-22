package com.sifast.service.audit;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.javers.core.Javers;
import org.javers.core.diff.Change;
import org.javers.core.diff.changetype.ReferenceChange;
import org.javers.core.diff.changetype.ValueChange;
import org.javers.repository.jql.QueryBuilder;
import org.javers.shadow.Shadow;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sifast.service.dto.VersionDTO;
import com.sifast.service.dto.VersionsDiffDTO;

@Service
public class AuditService {

	@Autowired
	private final Javers javers;

	public AuditService(Javers javers) {

		this.javers = javers;
	}

	public <T> void commitDelete(String author, T currentVersion) {
		javers.commitShallowDelete(author, currentVersion);
	}

	public <T> void commit(String author, T currentVersion) {
		javers.commit(author, currentVersion);
	}

	public <T> List<Change> getVersionsFinders(T entity) {
		return findVersions(entity);
	}

	public <T> List<VersionDTO<T>> getVersionsWithoutRelationships(T currentVersion, Object id) {

		List<Shadow<T>> ds = getShadows(currentVersion.getClass(), id);
		AtomicInteger index = new AtomicInteger();
		return ds.stream().map(d -> {
			VersionDTO<T> version = new VersionDTO<>();
			version.setEntity(d.get());
			version.setVersion(index.getAndIncrement());
			version.setAuthor(d.getCommitMetadata().getAuthor());
			version.setCreatedAt(d.getCommitMetadata().getCommitDate());
			if (!javers.compare(currentVersion, d.get()).hasChanges()) {
				version.setCurrentVersion(true);
			}
			return version;
		}).collect(Collectors.toList());
	}

	public <T> List<VersionDTO<T>> getVersions(T currentVersion) {

		List<Shadow<T>> ds = getAnyDomainObject();
		AtomicInteger index = new AtomicInteger();
		return ds.stream().map(d -> {
			VersionDTO<T> version = new VersionDTO<>();
			version.setEntity(d.get());
			version.setVersion(index.getAndIncrement());
			version.setAuthor(d.getCommitMetadata().getAuthor());
			version.setCreatedAt(d.getCommitMetadata().getCommitDate());
			if (!javers.compare(currentVersion, d.get()).hasChanges()) {
				version.setCurrentVersion(true);
			}
			return version;
		}).collect(Collectors.toList());
	}

	public <T> List<VersionDTO<T>> getVersions() {

		List<Shadow<T>> ds = getAnyDomainObject();
		AtomicInteger index = new AtomicInteger();
		return ds.stream().map(d -> {
			VersionDTO<T> version = new VersionDTO<>();
			version.setEntity(d.get());
			version.setVersion(index.getAndIncrement());
			version.setAuthor(d.getCommitMetadata().getAuthor());
			version.setCreatedAt(d.getCommitMetadata().getCommitDate());

			return version;
		}).collect(Collectors.toList());
	}

	public <T> T getVersion(Class<T> entity, Object id, int version) {
		List<Shadow<T>> shadows = getShadows(entity, id);
		return shadows.get(version).get();
	}

	private <T> List<Shadow<T>> getShadows(Class<?> entity, Object id) {
		QueryBuilder jqlQuery = QueryBuilder.byInstanceId(id, entity);
		List<Shadow<T>> shadows = javers.findShadows(jqlQuery.build());
		Collections.reverse(shadows);
		return shadows;
	}

	public <T> List<Shadow<T>> getDeepShadows(Class<?> entity, Object id) {
		QueryBuilder jqlQuery = QueryBuilder.byInstanceId(id, entity).withChildValueObjects().withScopeDeepPlus()
				.withNewObjectChanges();
		List<Shadow<T>> shadows = javers.findShadows(jqlQuery.build());
		Collections.reverse(shadows);
		return shadows;
	}

	public <T> List<Shadow<T>> getAnyDomainObject() {
		QueryBuilder jqlQuery = QueryBuilder.anyDomainObject();
		List<Shadow<T>> shadows = javers.findShadows(jqlQuery.build());
		Collections.reverse(shadows);
		return shadows;
	}

	public <T> List<Change> findVersions(T entity) {
		QueryBuilder jqlQuery = QueryBuilder.byClass(entity.getClass()).withScopeDeepPlus(2).withChildValueObjects();
		return javers.findChanges(jqlQuery.build());
	}

	public List<VersionsDiffDTO> compareTwoObjectsOftheSameType(Object a, Object b) {

		List<Change> changes = javers.compare(a, b).getChanges();
		return changes.parallelStream().map(change -> {
			VersionsDiffDTO diff = null;
			if (change instanceof ReferenceChange) {
				diff = new VersionsDiffDTO();
				diff.setPropertyName(((ReferenceChange) change).getPropertyName());
				diff.setPropertyNameWithPath(((ReferenceChange) change).getPropertyNameWithPath());
				diff.setLeft(((ReferenceChange) change).getLeft());
				diff.setRight(((ReferenceChange) change).getRight());
			} else if (change instanceof ValueChange) {
				diff = new VersionsDiffDTO();
				diff.setPropertyName(((ValueChange) change).getPropertyName());
				diff.setPropertyNameWithPath(((ValueChange) change).getPropertyNameWithPath());
				diff.setLeft(((ValueChange) change).getLeft());
				diff.setRight(((ValueChange) change).getRight());
			}

			return diff;
		}).filter(Objects::nonNull).collect(Collectors.toList());
	}

	public <T> List<VersionsDiffDTO> compare(Class<?> entity, Object id, int left, int right) {
		List<Shadow<T>> shadows = getShadows(entity, id);
		T v1 = shadows.get(left).get();
		T v2 = shadows.get(right).get();
		List<Change> changes = javers.compare(v1, v2).getChanges();
		return changes.parallelStream().map(change -> {
			VersionsDiffDTO diff = null;
			if (change instanceof ReferenceChange) {
				diff = new VersionsDiffDTO();
				diff.setPropertyName(((ReferenceChange) change).getPropertyName());
				diff.setPropertyNameWithPath(((ReferenceChange) change).getPropertyNameWithPath());
				diff.setLeft(((ReferenceChange) change).getLeft());
				diff.setRight(((ReferenceChange) change).getRight());
			} else if (change instanceof ValueChange) {
				diff = new VersionsDiffDTO();
				diff.setPropertyName(((ValueChange) change).getPropertyName());
				diff.setPropertyNameWithPath(((ValueChange) change).getPropertyNameWithPath());
				diff.setLeft(((ValueChange) change).getLeft());
				diff.setRight(((ValueChange) change).getRight());
			}

			return diff;
		}).filter(Objects::nonNull).collect(Collectors.toList());
	}

}
