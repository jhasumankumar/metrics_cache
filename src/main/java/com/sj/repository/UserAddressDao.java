package com.sj.repository;


import com.sj.model.UserModel;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Map;

public interface UserAddressDao extends GenericDao<UserModel> {

    void saveOrUpdateUser(UserModel userModel);

}
