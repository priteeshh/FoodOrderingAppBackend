package com.upgrad.FoodOrderingApp.service.entity;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "state")
@NamedQueries({
        @NamedQuery(name = "getStateFromUUID", query = "select p from StateEntity p where p.uuid = :UUID"),
        @NamedQuery(name = "getAllState", query = "select p from StateEntity p")

})
public class StateEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "uuid")
    @Size(max = 200)
    private String uuid;

    @Column(name = "state_name")
    @Size(max = 20)
    private String stateName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public StateEntity() {
    }

    public StateEntity(@Size(max = 200) String uuid, @Size(max = 20) String stateName) {
        this.uuid = uuid;
        this.stateName = stateName;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }
}
