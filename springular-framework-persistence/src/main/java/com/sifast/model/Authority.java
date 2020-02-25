package com.sifast.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.javers.core.metamodel.annotation.TypeName;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sifast.utils.TrackIdentifier;

@Entity
@Table(name = "authority")
@TypeName("Authority")
public class Authority extends TimestampEntity implements TrackIdentifier {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "designation")
    private String designation;

    @Column(name = "description")
    private String description;

    @ManyToMany(mappedBy = "authorities")
    @JsonIgnore
    private Set<Role> roles = new HashSet<>();

    @OneToOne
    private Authority parent;

    private boolean isDisplayed;

    @Column(name = "is_deleted")
    private boolean isDeleted;

    public Authority() {
        super();
    }

    public Authority(String designation) {
        super();
        this.designation = designation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public Authority getParent() {
        return parent;
    }

    public void setParent(Authority parent) {
        this.parent = parent;
    }

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public void setDisplayed(boolean isDisplayed) {
        this.isDisplayed = isDisplayed;
    }

    public void setRoles(Set<Role> rolees) {
        this.roles = rolees;
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
        result = prime * result + (description == null ? 0 : description.hashCode());
        result = prime * result + (designation == null ? 0 : designation.hashCode());
        result = prime * result + id;
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
        if (getClass() != obj.getClass()) {
            return false;
        }
        Authority other = (Authority) obj;
        if (description == null) {
            if (other.description != null) {
                return false;
            }
        } else if (!description.equals(other.description)) {
            return false;
        }
        if (designation == null) {
            if (other.designation != null) {
                return false;
            }
        } else if (!designation.equals(other.designation)) {
            return false;
        }
        if (id != other.id) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Authority [id=");
        builder.append(id);
        builder.append(", designation=");
        builder.append(designation);
        builder.append(", description=");
        builder.append(description);
        builder.append(", roles=");
        builder.append(roles);
        builder.append("]");
        return builder.toString();
    }

    @Override
    public int getIdentifierForTracking() {
        return this.getId();
    }

    @Override
    public String getDescriptionForTracking() {
        return this.toString();
    }

}
