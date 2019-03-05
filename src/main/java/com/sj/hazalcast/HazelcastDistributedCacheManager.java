package com.sj.hazalcast;


import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.hazelcast.core.DistributedObject;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import com.hazelcast.spring.context.SpringAware;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
@SpringAware
public class HazelcastDistributedCacheManager implements DistributedCacheManager, InitializingBean, ApplicationContextAware {

    private volatile ConcurrentMap<String, IMap> caches = new ConcurrentHashMap<String, IMap>();

    private HazelcastInstance hazelcastInstance;

    private transient ApplicationContext context;

    public HazelcastDistributedCacheManager(){

    }

    public void afterPropertiesSet() throws Exception {
        this.hazelcastInstance = (HazelcastInstance)this.context.getBean("instance");
        final Collection<DistributedObject> distributedObjects = this.hazelcastInstance.getDistributedObjects();
        for (DistributedObject distributedObject : distributedObjects) {
            if (distributedObject instanceof IMap) {
                final IMap map = (IMap) distributedObject;
                caches.put(map.getName(), map);
            }
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public <K, V> IMap<K, V> getCache(String cacheName) {
        if (caches.get(cacheName) == null) {
            synchronized (this) {
                if (caches.get(cacheName) == null) {
                    final IMap<K, V> map = this.hazelcastInstance.getMap(cacheName);
                    caches.putIfAbsent(cacheName, map);

                    return map;
                }
            }
        }
        return caches.get(cacheName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.context = applicationContext;
    }
}
