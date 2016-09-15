metrics_cache
Sbring Boot that publishes metrics to TSDB dummy client integrated with Coherence and Hazelcast
Add below arduments to run application and check Jconsole
-Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=7799 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false  -Dhazelcast.jmx=true