package com.sj.coherence.store;

import com.sj.model.UserModel;
import com.sj.repository.UserAddressDao;

import com.tangosol.net.cache.CacheStore;
import com.tangosol.util.Base;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;


import java.util.Collection;
import java.util.Map;

@Component
public class CoherenceUserCacheStore extends Base implements CacheStore {

    private static final Logger LOG = LoggerFactory.getLogger(CoherenceUserCacheStore.class);

    @Autowired
    private UserAddressDao userAddressDao;

    @Override
    @Transactional
    public void store(final Object key, final Object value) {
        final UserModel entity = (UserModel) value;
        final Long pk = (Long) key;
        erase(key);
        entity.setId(pk);
        userAddressDao.saveOrUpdateUser((UserModel) value);
        LOG.info("store");
    }

    @Override
    @Transactional
    public void storeAll(Map map) {

        LOG.info("storeAll");
    }

    @Override
    public void erase(Object o) {
        userAddressDao.delete((Long)o);
        LOG.info("erase");
    }

    @Override
    public void eraseAll(Collection collection) {

        LOG.info("eraseAll");
    }

    @Override
    public Object load(Object o) {
        LOG.info("load");
        return userAddressDao.findOne((Long)o);
    }

    @Override
    public Map loadAll(Collection collection) {
        LOG.info("loadAll");
        return null;
    }
}
