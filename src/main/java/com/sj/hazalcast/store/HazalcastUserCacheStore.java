package com.sj.hazalcast.store;

import java.util.Collection;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.MapLoaderLifecycleSupport;
import com.hazelcast.core.MapStore;

import com.sj.model.UserModel;
import com.sj.repository.UserAddressDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class HazalcastUserCacheStore implements MapStore<Long, UserModel>, MapLoaderLifecycleSupport {

    private final Logger logger = LoggerFactory.getLogger(HazalcastUserCacheStore.class);
    @Autowired
    @Qualifier("userAddressDao")
    private UserAddressDao userAddressDao;

    @Override
    public void init(HazelcastInstance instance, Properties properties, String mapName) {
        logger.info("Initializing {} cache", mapName);

    }

    @Override
    public void destroy() {
        // Nothing to do.
    }

    @Override
    public UserModel load(final Long key) {
        logger.info("load");
        return userAddressDao.findOne(key);
    }

    @Override
    public Map<Long, UserModel> loadAll(final Collection<Long> keys) {
        logger.info("loadAll");
        return null;
    }

    @Override
    public Set<Long> loadAllKeys() {
        logger.info("Loading all keys from cache");

        logger.info("loadAllKeys");
        return null;
    }

    @Override
    @Transactional
    public void store(final Long key, final UserModel value) {
        delete(key);
        value.setId(key);
        userAddressDao.saveOrUpdateUser(value);
        logger.info("store");
    }

    @Override
    public void storeAll(final Map<Long, UserModel> map) {
        for (Map.Entry<Long, UserModel> entry : map.entrySet()) {
            this.store(entry.getKey(), entry.getValue());
        }
        logger.info("storeAll");
    }

    @Override
    public void delete(final Long key) {
        logger.info("Deleting {} from cache", key);

    }

    @Override
    public void deleteAll(final Collection<Long> keys) {
        for (Long key : keys) {
            this.delete(key);
        }
    }

}
