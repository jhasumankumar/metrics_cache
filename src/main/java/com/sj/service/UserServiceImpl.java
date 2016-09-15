package com.sj.service;




import com.sj.hazalcast.DistributedCacheManager;
import com.sj.model.UserModel;
import com.sj.repository.UserAddressDao;
import com.tangosol.net.NamedCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.ConcurrentMap;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    @Qualifier("coherenceCacheContext")
    private com.sj.coherence.CacheContext coherenceCacheContext;
    @Autowired
    private DistributedCacheManager distributedCacheManager;


    @Override
    @Transactional
    public boolean saveUser(UserModel userModel) {
        NamedCache namedCache = coherenceCacheContext.getCache("com.sj.model.data");
        namedCache.put(userModel.getId(), userModel);

        ConcurrentMap<Long, UserModel> cache = distributedCacheManager.getCache("store");
        cache.put(userModel.getId(), userModel);
       // userBagDao.save(userModel);
        return true;
    }

    @Override
    public UserModel getUser(Long id) {
        NamedCache namedCache = coherenceCacheContext.getCache("com.sj.model.data");
        UserModel userModel = (UserModel)namedCache.get(id);

        ConcurrentMap<Integer, UserModel> cache = distributedCacheManager.getCache("store");
        userModel = cache.get(id);
        //return userBagDao.findOne(id);
        return userModel;
    }




}
