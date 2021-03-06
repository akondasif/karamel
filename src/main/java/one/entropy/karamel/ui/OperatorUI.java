package one.entropy.karamel.ui;

import io.fabric8.kubernetes.api.model.Service;
import io.fabric8.kubernetes.api.model.apps.Deployment;
import io.fabric8.kubernetes.api.model.apps.ReplicaSet;
import io.quarkus.qute.Template;
import io.quarkus.qute.TemplateInstance;
import io.vavr.control.Try;
import one.entropy.karamel.api.KaramelAPI;
import one.entropy.karamel.data.PodInfo;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.Collection;
import java.util.List;

@Path("/")
public class OperatorUI {

    @Inject
    KaramelAPI karamelAPI;

    @Inject
    Template operators;

    @GET
    @Consumes(MediaType.TEXT_HTML)
    @Produces(MediaType.TEXT_HTML)
    @Path("operators")
    public TemplateInstance operators() {
        Collection<Deployment> clusterDeployments = karamelAPI.getClusterDeployments();
        Collection<Deployment> entityDeployments = karamelAPI.getEntityDeployments();
        Collection<Deployment> connectDeployments = karamelAPI.getConnectDeployments();

        Collection<ReplicaSet> clusterReplicaSets = karamelAPI.getClusterReplicaSets();
        Collection<ReplicaSet> entityReplicaSets = karamelAPI.getEntityReplicaSets();
        Collection<ReplicaSet> connectReplicaSets = karamelAPI.getConnectReplicaSets();

        Collection<PodInfo> clusterPods = karamelAPI.getClusterPods();
        Collection<PodInfo> entityPods = karamelAPI.getEntityPods();
        Collection<PodInfo> connectPods = karamelAPI.getConnectPods();

        Collection<Service> connectServices = karamelAPI.getConnectServices();
        return operators
                .data("clusterDeployments", clusterDeployments)
                .data("entityDeployments", entityDeployments)
                .data("connectDeployments", connectDeployments)
                .data("clusterReplicaSets", clusterReplicaSets)
                .data("entityReplicaSets", entityReplicaSets)
                .data("connectReplicaSets", connectReplicaSets)
                .data("clusterPods", clusterPods)
                .data("entityPods", entityPods)
                .data("connectPods", connectPods)
                .data("connectServices", connectServices)
                .data("page", "operators");
    }
}
