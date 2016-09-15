package com.sj.model;

import com.tangosol.io.ExternalizableLite;
import com.tangosol.util.ExternalizableHelper;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "USER")
public class UserModel extends AbstractModel implements Serializable, ExternalizableLite {

    public UserModel(){

    }

    public UserModel(String username){
        this.username  =username;
    }

    @Id
    private Long id;

    @Column(name = "USER_NAME")
    public String username;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "ID", referencedColumnName = "ID")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private Set<UserAddressModel> addresses = new HashSet<>();

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

    public Set<UserAddressModel> getAddresses() {
        return addresses;
    }

    public void setAddresses(Set<UserAddressModel> addresses) {
        this.addresses = addresses;
    }

    @Override
    public void readExternal(DataInput dataInput) throws IOException {
        this.username = ExternalizableHelper.readSafeUTF(dataInput);
    }

    @Override
    public void writeExternal(DataOutput dataOutput) throws IOException {
        ExternalizableHelper.writeSafeUTF(dataOutput, username);
    }
}
