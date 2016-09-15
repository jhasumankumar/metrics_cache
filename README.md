#    Metrics Cache Sbring Boot that publishes metrics to TSDB dummy client integrated with Coherence and Hazelcast.

It uses 
- 1.  Coherence 12.2.1 (You can jar to your local repo to test)
- 2.  Hazalcast 3.7
- 3.  Spring Boot 1.4.0.RELEASE
- 4.  Java 8 (Some code that is old like loops and combination of generics and non-generics) :)
- 5.  Hsqldb 2.3.3
- 6.  Dropwizard 2.3.3
- 7.  Spring Boot Actuator 


It creates write behind cache and stores in DB from both Coherence and Hazalcast cache store. You can say it's wrong as same data is inserted twice but it's just a learning project

# Create Coherence cache factory use - 

-- context is a reference of ApplicationContext

 ExtensibleConfigurableCacheFactory.Dependencies deps =
                ExtensibleConfigurableCacheFactory.DependenciesHelper.newInstance("cache-config.xml");
        ExtensibleConfigurableCacheFactory factory =
                new ExtensibleConfigurableCacheFactory(deps);
        // this.cacheFactory = CacheFactory.getCacheFactoryBuilder().getConfigurableCacheFactory("cache-config.xml", classLoader);
        factory.getResourceRegistry().registerResource(BeanFactory.class, SpringNamespaceHandler.DEFAULT_FACTORY_NAME, context);
        
    # Coherence cache store config in cache-config.xm 
        
 # Hazalcast cache store config in application-context.xml - 
  - hz:map name="store" >
                <hz:map-store  enabled="true" implementation="hazalcastUserCacheStore"
                              write-delay-seconds="0" 

# TSDB

- Simple TSDB dummy API - 
-   @POST
    @Path("/tsdb/put")
    public Map sendMetrics(List<OpenTsdbData> snapshot) {
        for (OpenTsdbData op : snapshot) {
           LOG.info("Metrics Name -"+op.getMetric(),"Metrics value-"+op.getValue());
        }

        return new HashMap<>();
    }


# Add below arduments to run application and check Jconsole. I have used port JMX 7799 i.e. hardcoded to read Metrics from Jconsole and publish it to TSDB
-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=7799 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false  -Dhazelcast.jmx=true
