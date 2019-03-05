package com.sj.resources.metrics;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.metrics.opentsdb.OpenTsdbData;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class TSDBResource {

    private static final Logger LOG = LoggerFactory.getLogger(TSDBResource.class);

    @POST
    @Path("/tsdb/put")
    public Map sendMetrics(List<OpenTsdbData> snapshot) {
        for (OpenTsdbData op : snapshot) {
           LOG.info("Metrics Name -"+op.getMetric(),"Metrics value-"+op.getValue());
        }
        return new HashMap<>();
    }


}
