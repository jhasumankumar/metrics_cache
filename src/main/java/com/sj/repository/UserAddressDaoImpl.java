package com.sj.repository;

import com.sj.model.UserModel;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository("userAddressDao")
public class UserAddressDaoImpl extends GenericDaoImpl<UserModel> implements UserAddressDao{


    @Override
    public void saveOrUpdateUser(UserModel userModel) {
        if (null != userModel) {
            if (userModel.getId() == null || userModel.getId() == 0) {
                this.save(userModel);
            } else {
                this.em.merge(userModel);
            }
        }
    }
}