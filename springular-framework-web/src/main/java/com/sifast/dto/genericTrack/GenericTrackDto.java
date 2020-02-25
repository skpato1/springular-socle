package com.sifast.dto.genericTrack;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.OptBoolean;

import io.swagger.annotations.ApiModelProperty;

public class GenericTrackDto {

    @ApiModelProperty(position = 1)
    private int id;

    @ApiModelProperty(position = 2)
    private String entityName;

    @ApiModelProperty(position = 3)
    private String eventTypeDescription;

    @ApiModelProperty(required = true, dataType = "java.util.Date", example = "dd/MM/yyyy HH:mm:ss", position = 4)
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", lenient = OptBoolean.FALSE, timezone = "GMT+1")
    private LocalDateTime eventDate;

    @ApiModelProperty(position = 4)
    private String performedBy;

    @ApiModelProperty(position = 5)
    private String changedState;

    @ApiModelProperty(position = 6)
    private String changedProperties;

    @ApiModelProperty(position = 7)
    private int entityId;

    @ApiModelProperty(position = 8)
    private String previousState;

    public GenericTrackDto() {
        super();
    }

    public GenericTrackDto(int id, String eventType, LocalDateTime eventDate, String performedBy, String entityName, String changedProperties, String changedState) {
        super();
        this.id = id;
        this.eventTypeDescription = eventType;
        this.eventDate = eventDate;
        this.performedBy = performedBy;
        this.entityName = entityName;
        this.changedProperties = changedProperties;
        this.changedState = changedState;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEventTypeDescription() {
        return eventTypeDescription;
    }

    public void setEventTypeDescription(String eventTypeDescription) {
        this.eventTypeDescription = eventTypeDescription;
    }

    public LocalDateTime getEventDate() {
        return eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate;
    }

    public String getPerformedBy() {
        return performedBy;
    }

    public void setPerformedBy(String performedBy) {
        this.performedBy = performedBy;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public String getChangedState() {
        return changedState;
    }

    public void setChangedState(String changedState) {
        this.changedState = changedState;
    }

    public String getChangedProperties() {
        return changedProperties;
    }

    public void setChangedProperties(String changedProperties) {
        this.changedProperties = changedProperties;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getPreviousState() {
        return previousState;
    }

    public void setPreviousState(String previousState) {
        this.previousState = previousState;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GenericTrackDto [id=");
        builder.append(id);
        builder.append(", entityName=");
        builder.append(entityName);
        builder.append(", eventTypeDescription=");
        builder.append(eventTypeDescription);
        builder.append(", eventDate=");
        builder.append(eventDate);
        builder.append(", performedBy=");
        builder.append(performedBy);
        builder.append(", changedState=");
        builder.append(changedState);
        builder.append(", changedProperties=");
        builder.append(changedProperties);
        builder.append(", entityId=");
        builder.append(entityId);
        builder.append(", previousState=");
        builder.append(previousState);
        builder.append("]");
        return builder.toString();
    }

}