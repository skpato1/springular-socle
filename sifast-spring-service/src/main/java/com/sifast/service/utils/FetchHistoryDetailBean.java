package com.sifast.service.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.EntityManagerFactory;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.sifast.enumeration.EventType;
import com.sifast.service.IGenericTrackService;
import com.sifast.utils.TrackIdentifier;

@Component(value = "FetchHistoryDetailBean")
public class FetchHistoryDetailBean {

	public static final Logger LOGGER = LoggerFactory.getLogger(FetchHistoryDetailBean.class);

	@Autowired
	private EntityManagerFactory entityManagerFactory;

	@Autowired
	private IGenericTrackService genericTrackService;

	SessionFactory sessionFactory;

	private StringBuilder entitiesPackage = new StringBuilder("com.sifast.access.control.model.");

	private static final String REMOVED_REGEX = "removed:'[A-Za-z]*\\/[\\d]*'";

	private static final String OLD_REF_REGEX = "oldRef:'[A-Za-z]*\\/[\\d]*'";

	private static final String NEW_REF_REGEX = "newRef:'[A-Za-z]*\\/[\\d]*'";

	private static final String ADDED_REGEX = "added:'[A-Za-z]*\\/[\\d]*'";

	private static final String MODIFIED_REGEX = ">>'[A-Za-z]*\\/[\\d]*'";

	private static final String LAST_REGEX = "'[A-Za-z]*\\/[\\d]*'>>";

	private static final String ID_ENTITY_NAME_REGEX = "[A-Za-z]*\\/[\\d]*";

	private static final String ID_REGEX = "\\d+";

	private static final StringBuilder ADDED_VALUE = new StringBuilder("Added Value");

	private static final StringBuilder REMOVED_VALUE = new StringBuilder("Deleted Value");

	private List<String> removedObjects = new ArrayList<>();

	private List<String> addedObjects = new ArrayList<>();

	public void setGenericTrackService(IGenericTrackService genericTrackService) {
		this.genericTrackService = genericTrackService;
	}

	public StringBuilder getEntitiesPackage() {
		return entitiesPackage;
	}

	public void setEntitiesPackage(StringBuilder entitiesPackage) {
		this.entitiesPackage = entitiesPackage;
	}

	public List<String> getRemovedObjects() {
		return removedObjects;
	}

	public void setRemovedObjects(List<String> removedObjects) {
		this.removedObjects = removedObjects;
	}

	public List<String> getAddedObjects() {
		return addedObjects;
	}

	public void setAddedObjects(List<String> addedObjects) {
		this.addedObjects = addedObjects;
	}

	public String showDescription(String changedStringObjects) {
		getDeletedAndAdedValues(changedStringObjects);
		for (int i = 0; i < addedObjects.size(); i++) {
			if (removedObjects.contains(addedObjects.get(i))) {
				removedObjects.remove(addedObjects.get(i));
				addedObjects.remove(addedObjects.get(i));
			}
		}
		StringBuilder addedString = convertListToStringWithDescriptionIfNotEmpty(ADDED_VALUE, addedObjects);
		StringBuilder removedString = convertListToStringWithDescriptionIfNotEmpty(REMOVED_VALUE, removedObjects);
		StringBuilder onHoverDisplayedString = removedString;
		if (!removedObjects.isEmpty()) {
			onHoverDisplayedString.append("<br>");
		}
		onHoverDisplayedString.append(addedString);
		return onHoverDisplayedString.toString();
	}

	private void getDeletedAndAdedValues(String changedStringObjects) {
		Pattern removedPattern = Pattern.compile(REMOVED_REGEX);
		Pattern addedPattern = Pattern.compile(ADDED_REGEX);
		Pattern comparePattern = Pattern.compile(MODIFIED_REGEX);
		Pattern numberPattern = Pattern.compile(LAST_REGEX);
		Pattern idAndEntityNamePattern = Pattern.compile(ID_ENTITY_NAME_REGEX);
		Pattern idPattern = Pattern.compile(ID_REGEX);
		Pattern oldRefPatern = Pattern.compile(OLD_REF_REGEX);
		Pattern newRefPattern = Pattern.compile(NEW_REF_REGEX);

		Matcher removedMatcher = removedPattern.matcher(changedStringObjects);
		Matcher addedMatcher = addedPattern.matcher(changedStringObjects);
		Matcher compareMatcher = comparePattern.matcher(changedStringObjects);
		Matcher numberMatcher = numberPattern.matcher(changedStringObjects);
		Matcher oldRefMatcher = oldRefPatern.matcher(changedStringObjects);
		Matcher newRefMatcher = newRefPattern.matcher(changedStringObjects);

		this.removedObjects.clear();
		this.addedObjects.clear();

		for (int i = 0; i < changedStringObjects.length(); i++) {
			if (removedMatcher.find()) {
				this.removedObjects.addAll(getAllMatchers(idAndEntityNamePattern, idPattern, removedMatcher));
			}
			if (addedMatcher.find()) {
				this.addedObjects.addAll(getAllMatchers(idAndEntityNamePattern, idPattern, addedMatcher));
			}
			if (compareMatcher.find()) {
				this.addedObjects.addAll(getAllMatchers(idAndEntityNamePattern, idPattern, compareMatcher));
			}
			if (numberMatcher.find()) {
				this.removedObjects.addAll(getAllMatchers(idAndEntityNamePattern, idPattern, numberMatcher));
			}
			if (oldRefMatcher.find()) {
				this.removedObjects.addAll(getAllMatchers(idAndEntityNamePattern, idPattern, oldRefMatcher));
			}
			if (newRefMatcher.find()) {
				this.addedObjects.addAll(getAllMatchers(idAndEntityNamePattern, idPattern, newRefMatcher));
			}
		}
	}

	public StringBuilder convertListToStringWithDescriptionIfNotEmpty(StringBuilder actionDescription,
			List<String> updatedObjects) {
		StringBuilder stringResult = new StringBuilder();
		if (!updatedObjects.isEmpty()) {
			stringResult = new StringBuilder(actionDescription).append(updatedObjects.toString());
		}
		return stringResult;
	}

	public List<String> getAllMatchers(Pattern idAndEntityNamePattern, Pattern idPattern, Matcher matcher) {
		List<String> objectList = new ArrayList<>();

		String actionAndIdAndEntityName = matcher.group(0);
		Matcher idAndEntityNameMatcher = idAndEntityNamePattern.matcher(actionAndIdAndEntityName);
		idAndEntityNameMatcher.find();
		String idAndEntityName = idAndEntityNameMatcher.group(0);
		Matcher idMatcher = idPattern.matcher(idAndEntityName);
		idMatcher.find();
		String id = idMatcher.group(0);
		String entityName = idAndEntityName.replace(id, "").replace("/", "");
		Class<?> classType = null;
		try {
			classType = Class.forName(entitiesPackage.append(entityName).toString());
		} catch (ClassNotFoundException e) {
			LOGGER.error("ERROR : {}", e);
		} finally {
			entitiesPackage = entitiesPackage.replace(entitiesPackage.indexOf(entityName), entitiesPackage.length(),
					"");
		}

		Session session = openSession();
		Object object = session.getReference(classType, Integer.parseInt(id));
		if (object == null) {
			object = genericTrackService.findTrackByEntityNameAndIdAndEventType(entityName, Integer.parseInt(id),
					EventType.DELETE);
			objectList.add(object.toString());
			return objectList;
		}
		TrackIdentifier objectProprerties = (TrackIdentifier) object;
		objectList.add(objectProprerties.getDescriptionForTracking());
		return objectList;
	}

	private Session openSession() {
		sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
		return sessionFactory.openSession();
	}
}
