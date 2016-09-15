package com.sj.service;


import com.sj.model.UserModel;

import java.util.List;

public interface UserService {

    boolean saveUser(UserModel userModel);
    UserModel getUser(Long id);

}
