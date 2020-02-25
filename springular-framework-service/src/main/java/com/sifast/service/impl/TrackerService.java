package com.sifast.service.impl;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.persistence.EntityManagerFactory;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Change;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.sifast.common.ApiMessage;
import com.sifast.enumeration.EventType;
import com.sifast.model.GenericTrack;
import com.sifast.model.User;
import com.sifast.service.IGenericTrackService;
import com.sifast.service.ITrackerUtil;
import com.sifast.service.IUserService;
import com.sifast.service.dto.JaversCompareResultDto;
import com.sifast.service.dto.LocalDateAdapter;
import com.sifast.service.utils.HibernateProxyUtil;
import com.sifast.service.utils.UserUtils;
import com.sifast.utils.TrackIdentifier;

@Aspect
@Component
@Transactional
public class TrackerService {

    private static final String EMPTY_OPERATION = "[]";

    private static final Logger LOGGER = LoggerFactory.getLogger(TrackerService.class);

    @Autowired
    private IGenericTrackService genericTrackService;

    @Autowired
    private IUserService userService;

    SessionFactory sessionFactory;

    private GenericTrack genericTrack;

    private Javers javers = JaversBuilder.javers().build();

    private Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().registerTypeAdapter(LocalDate.class, new LocalDateAdapter()).create();

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public GenericTrack getGenericTrack() {
        return genericTrack;
    }

    public void setGenericTrack(GenericTrack genericTrack) {
        this.genericTrack = genericTrack;
    }

    public Javers getJavers() {
        return javers;
    }

    public void setJavers(Javers javers) {
        this.javers = javers;
    }

    public Gson getGson() {
        return gson;
    }

    public void setGson(Gson gson) {
        this.gson = gson;
    }

    @AfterReturning(pointcut = "execution(* com.sifast.service.impl.GenericService.save*(..)) && @args(com.sifast.annotations.Trackable)", returning = "savedObject")
    public void saveTracker(JoinPoint joinPoint, Object savedObject) {
        User user = getConnectedUser();
        genericTrack = new GenericTrack();
        genericTrack.setEventDate(LocalDateTime.now());
        genericTrack.setEventType(EventType.SAVE);
        genericTrack.setEntityName(savedObject.getClass().getSimpleName());
        genericTrack.setAllChangedValues(getGson().toJson(savedObject));
        genericTrack.setChangedState(getGson().toJson(savedObject));
        TrackIdentifier trackedIdentifierObject = ITrackerUtil.getITrackIdentifier(savedObject);
        genericTrack.setEntityId(trackedIdentifierObject.getIdentifierForTracking());
        genericTrack.setPerformedBy(new StringBuilder().append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
        genericTrackService.save(genericTrack);
    }

    @Around("execution(* com.sifast.service.impl.GenericService.validate*(..)) && @args(com.sifast.annotations.Trackable)")
    public Object validateTracker(ProceedingJoinPoint joinPoint) {
        return updateOrValidateTracker(joinPoint, EventType.VALIDATE);
    }

    @Around("execution(* com.sifast.service.impl.GenericService.update*(..)) &&  @args(com.sifast.annotations.Trackable)")
    public Object updateTracker(ProceedingJoinPoint joinPoint) {
        return updateOrValidateTracker(joinPoint, EventType.UPDATE);
    }

    @Before("execution(* com.sifast.service.impl.GenericService.delete*(..)) &&  @args(com.sifast.annotations.Trackable)")
    public void deleteTracker(JoinPoint joinPoint) {
        User user = getConnectedUser();
        genericTrack = new GenericTrack();
        Object deletedObject = joinPoint.getArgs()[0];
        genericTrack.setEventDate(LocalDateTime.now());
        genericTrack.setEventType(EventType.DELETE);
        genericTrack.setEntityName(deletedObject.getClass().getSimpleName());
        TrackIdentifier trackedIdentifierObject = ITrackerUtil.getITrackIdentifier(deletedObject);
        genericTrack.setEntityId(trackedIdentifierObject.getIdentifierForTracking());
        genericTrack.setAllChangedValues(getGson().toJson(deletedObject));
        genericTrack.setPerformedBy(new StringBuilder().append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
        genericTrackService.save(genericTrack);
    }

    private Session openSession() {
        sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        return sessionFactory.openSession();
    }

    private User getConnectedUser() {
        User connectedUser = UserUtils.getConnectedUser(userService);
        if (connectedUser == null) {
            connectedUser = new User();
            connectedUser.setFirstName(ApiMessage.SYSTEM);
            connectedUser.setLastName(ApiMessage.EMPTY_MESSAGE);
        }
        return connectedUser;
    }

    private Object updateOrValidateTracker(ProceedingJoinPoint joinPoint, EventType eventType) {
        genericTrack = new GenericTrack();
        Object objectToBeUpdated = joinPoint.getArgs()[0];
        TrackIdentifier trackedIdentifierObject = ITrackerUtil.getITrackIdentifier(objectToBeUpdated);
        Session session = openSession();
        Serializable idOldObject = trackedIdentifierObject.getIdentifierForTracking();
        Object oldObject = session.getReference(joinPoint.getArgs()[0].getClass(), idOldObject);
        Object unproxiedObject = HibernateProxyUtil.initializeAndUnproxy(oldObject);
        List<Change> diffBtwOldAndNewObjectForProprites = javers.compare(unproxiedObject, objectToBeUpdated).getChanges(change -> change.toString().contains("Value"));
        genericTrack.setPreviousState(getGson().toJson(unproxiedObject));
        Object updatedObject = "";
        try {
            updatedObject = joinPoint.proceed();
        } catch (Throwable e) {
            LOGGER.error("ERROR {}", e);
        }
        List<JaversCompareResultDto> cleanChangedPropreties = ITrackerUtil.getOnlyChangedProperties(diffBtwOldAndNewObjectForProprites);
        String changedObjectDto = new Gson().toJson(cleanChangedPropreties);
        genericTrack.setChangedProperties(changedObjectDto);
        String cleanChangedValues = diffBtwOldAndNewObjectForProprites.toString().replaceAll("Diff:", "").replaceAll("ValueChange", "").replaceAll("globalId.*?[,]", "")
                .replace("globalId:", "").replaceAll("oldVal", "oldValue").replaceAll("newVal", "newValue").replaceAll("ListChange", "").replaceAll("containerChanges", "");
        genericTrack.setChangedProperties(changedObjectDto);
        genericTrack.setAllChangedValues(cleanChangedValues);
        genericTrack.setEventDate(LocalDateTime.now());
        genericTrack.setEventType(eventType);
        genericTrack.setEntityName(joinPoint.getArgs()[0].getClass().getSimpleName());
        genericTrack.setEntityId(trackedIdentifierObject.getIdentifierForTracking());
        User user = getConnectedUser();
        genericTrack.setPerformedBy(new StringBuilder().append(user.getFirstName()).append(" ").append(user.getLastName()).toString());
        genericTrack.setChangedState(getGson().toJson(updatedObject));
        if (!(EMPTY_OPERATION.equals(genericTrack.getAllChangedValues()) && EMPTY_OPERATION.equals(genericTrack.getChangedProperties()))) {
            genericTrackService.save(genericTrack);
        }
        session.close();
        return updatedObject;

    }

}
