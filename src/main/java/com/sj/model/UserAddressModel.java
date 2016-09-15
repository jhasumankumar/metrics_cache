package com.sj.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "USER_ADDRESS")
public class UserAddressModel extends AbstractModel implements Serializable {

    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "USER_NAME")
    public String username;

    @Column(name = "STREET")
    public String street;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }
}
