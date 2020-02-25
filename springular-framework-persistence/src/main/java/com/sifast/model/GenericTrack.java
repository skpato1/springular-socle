package com.sifast.model;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Cacheable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import com.sifast.enumeration.EventType;

@Entity
@Table(name = "generic_track")
@Cacheable
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class GenericTrack extends TimestampEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private static final String COLUMN_DEFINITION_TEXT = "TEXT";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Enumerated(EnumType.STRING)
    @Column(name = "event_type")
    private EventType eventType;

    @Column(name = "event_date")
    private LocalDateTime eventDate;

    @Column(name = "performed_by")
    private String performedBy;

    @Column(name = "entity_name")
    private String entityName;

    @Column(name = "all_changed_values", columnDefinition = COLUMN_DEFINITION_TEXT)
    private String allChangedValues;

    @Column(name = "previous_state", columnDefinition = COLUMN_DEFINITION_TEXT)
    private String previousState;

    @Column(name = "changed_state", columnDefinition = COLUMN_DEFINITION_TEXT)
    private String changedState;

    @Column(name = "entity_id")
    private int entityId;

    @Column(name = "changed_properties", columnDefinition = COLUMN_DEFINITION_TEXT)
    private String changedProperties;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public EventType getEventType() {
        return eventType;
    }

    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

    public LocalDateTime getEventDate() {
        return this.eventDate;
    }

    public void setEventDate(LocalDateTime eventDate) {
        this.eventDate = eventDate != null ? LocalDateTime.now() : null;
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

    public void setEntityName(String entity) {
        this.entityName = entity;
    }

    public String getPreviousState() {
        return previousState;
    }

    public void setPreviousState(String previousState) {
        this.previousState = previousState;
    }

    public String getChangedState() {
        return changedState;
    }

    public void setChangedState(String changedState) {
        this.changedState = changedState;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public String getAllChangedValues() {
        return allChangedValues;
    }

    public void setAllChangedValues(String allChangedValues) {
        this.allChangedValues = allChangedValues;
    }

    public String getChangedProperties() {
        return changedProperties;

    }

    public void setChangedProperties(String changedPropreties) {
        this.changedProperties = changedPropreties;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (allChangedValues == null ? 0 : allChangedValues.hashCode());
        result = prime * result + entityId;
        result = prime * result + id;
        result = prime * result + (performedBy == null ? 0 : performedBy.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof GenericTrack)) {
            return false;
        }
        GenericTrack other = (GenericTrack) obj;
        if (allChangedValues == null) {
            if (other.allChangedValues != null) {
                return false;
            }
        } else if (!allChangedValues.equals(other.allChangedValues)) {
            return false;
        }
        if (entityId != other.entityId) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        if (performedBy == null) {
            if (other.performedBy != null) {
                return false;
            }
        } else if (!performedBy.equals(other.performedBy)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("GenericTrack [id=");
        builder.append(id);
        builder.append(", eventType=");
        builder.append(eventType);
        builder.append(", eventDate=");
        builder.append(eventDate);
        builder.append(", performedBy=");
        builder.append(performedBy);
        builder.append(", entityName=");
        builder.append(entityName);
        builder.append(", allChangedValues=");
        builder.append(allChangedValues);
        builder.append(", previousState=");
        builder.append(previousState);
        builder.append(", changedState=");
        builder.append(changedState);
        builder.append(", entityId=");
        builder.append(entityId);
        builder.append(", changedProperties=");
        builder.append(changedProperties);
        builder.append("]");
        return builder.toString();
    }
}