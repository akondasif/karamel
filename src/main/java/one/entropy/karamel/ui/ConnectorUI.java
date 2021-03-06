package one.entropy.karamel.ui;

import java.util.Collection;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.ReplicaSet;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import one.entropy.karamel.api.KaramelAPI;
import one.entropy.karamel.data.PodInfo;

@Path("/")
public class ConnectorUI {

    @Inject
    KaramelAPI karamelAPI;

    @Inject
    Template connectors;

    @GET
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    @Path("connectors")
    public TemplateInstance operators() {
        Collection<Deployment> connectDeployments = karamelAPI.getConnectDeployments();

        Collection<ReplicaSet> connectReplicaSets = karamelAPI.getConnectReplicaSets();

        Collection<PodInfo> connectPods = karamelAPI.getConnectPods();

        Collection<Service> connectServices = karamelAPI.getConnectServices();

        return connectors
                .data("connectDeployments", connectDeployments)
                .data("connectReplicaSets", connectReplicaSets)
                .data("connectPods", connectPods)
                .data("connectServices", connectServices)
                .data("page", "connectors");
    }
}
