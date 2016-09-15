package com.sj.coherence;


import java.util.List;

import com.oracle.coherence.spring.SpringNamespaceHandler;
import com.tangosol.net.*;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Component("coherenceCacheContext")
public class CacheContext implements ApplicationContextAware {

    private DefaultCacheServer cacheServer;
    private List<Service> coherenceServices;
    private ExtensibleConfigurableCacheFactory cacheFactory;
    private final ClassLoader classLoader = this.getClass().getClassLoader();
    private transient ApplicationContext context;

    public CacheContext() {

    }

    public NamedCache getCache(String cacheName) {
        return cacheFactory.ensureCache(cacheName, classLoader);
    }

    public DefaultCacheServer getCacheServer() {
        return cacheServer;
    }

    public ConfigurableCacheFactory getCacheFactory() {
        return cacheFactory;
    }

    public List<Service> getCoherenceServices() {
        return coherenceServices;
    }

    @PreDestroy
    public void shutdown() {
        cacheServer.shutdownServer();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
        ExtensibleConfigurableCacheFactory.Dependencies deps =
                ExtensibleConfigurableCacheFactory.DependenciesHelper.newInstance("cache-config.xml");
        ExtensibleConfigurableCacheFactory factory =
                new ExtensibleConfigurableCacheFactory(deps);
        // this.cacheFactory = CacheFactory.getCacheFactoryBuilder().getConfigurableCacheFactory("cache-config.xml", classLoader);
        factory.getResourceRegistry().registerResource(BeanFactory.class, SpringNamespaceHandler.DEFAULT_FACTORY_NAME, context);
        this.cacheFactory = factory;
        this.cacheServer = new DefaultCacheServer(cacheFactory);
        this.coherenceServices = cacheServer.startServices();
    }
}