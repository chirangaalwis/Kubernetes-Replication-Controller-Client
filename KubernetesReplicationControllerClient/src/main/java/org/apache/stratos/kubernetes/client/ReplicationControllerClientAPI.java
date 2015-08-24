/*
* Copyright (c) 2015, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
*
* Licensed under the Apache License, Version 2.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.apache.org/licenses/LICENSE-2.0
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.apache.stratos.kubernetes.client;

import io.fabric8.kubernetes.api.KubernetesClient;
import io.fabric8.kubernetes.api.KubernetesFactory;
import io.fabric8.kubernetes.api.model.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.stratos.kubernetes.client.interfaces.ReplicationControllerClientAPIInterface;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReplicationControllerClientAPI implements ReplicationControllerClientAPIInterface {
    private final KubernetesClient kubernetesClient;

    private static final Log LOG = LogFactory.getLog(ReplicationControllerClientAPI.class);

    public ReplicationControllerClientAPI(String endpointURL) {
        kubernetesClient = new KubernetesClient(new KubernetesFactory(endpointURL));
    }

    public void createReplicationController(String replicationControllerID, String selectorLabel, int replicas,
            String containerName, String dockerImage, int cpu, int memory, List<ContainerPort> ports) throws Exception {

        int memoryInMB = 1024 * 1024 * memory;
        if (LOG.isDebugEnabled()) {
            LOG.debug(String.format(
                    "Creating kubernetes replication-controller: [rc-id] %s " + "[container-name] %s [docker-image] %s "
                            + "[cpu] %d [memory] %d MB [ports] %s", replicationControllerID, containerName, dockerImage,
                    cpu, memoryInMB, ports));
        }

        // Create replication-controller definition
        ReplicationController replicationController = new ReplicationController();

        replicationController.setApiVersion(ReplicationController.ApiVersion.V_1);
        replicationController.setKind(KubernetesConstants.KIND_REPLICATION_CONTROLLER);

        ObjectMeta replicationControllerMetaData = new ObjectMeta();
        replicationControllerMetaData.setName(replicationControllerID);
        replicationController.setMetadata(replicationControllerMetaData);

        ReplicationControllerSpec replicationControllerSpec = new ReplicationControllerSpec();
        replicationControllerSpec.setReplicas(replicas);

        // Setup label selectors for the replication controller
        Map<String, String> selectors = new HashMap<String, String>();
        selectors.put(KubernetesConstants.LABEL_NAME_REPLICATION_CONTROLLER, selectorLabel);
        replicationControllerSpec.setSelector(selectors);

        PodTemplateSpec podTemplateSpec = new PodTemplateSpec();

        ObjectMeta podMetaData = new ObjectMeta();
        podMetaData.setLabels(selectors);
        podTemplateSpec.setMetadata(podMetaData);

        PodSpec podSpec = new PodSpec();

        List<Container> containers = new ArrayList<Container>();
        // Create container definition
        Container container = new Container();
        container.setName(containerName);
        container.setImage(dockerImage);
        container.setPorts(ports);

        // Set resource limits

        // TODO: Has to be checked - Pod Status General Error
        /*ResourceRequirements resources = new ResourceRequirements();
        Map<String, Quantity> limits = new HashMap<String, Quantity>();
        limits.put(KubernetesConstantsExtended.RESOURCE_CPU, new Quantity(String.valueOf(cpu)));
        limits.put(KubernetesConstantsExtended.RESOURCE_MEMORY, new Quantity(String.valueOf(memoryInMB)));
        resources.setLimits(limits);
        container.setResources(resources);*/

        // Add container definition to the list of containers
        containers.add(container);

        podSpec.setContainers(containers);

        // Add Pod Spec to the Pod Template Spec
        podTemplateSpec.setSpec(podSpec);
        // Add Pod Template Spec to the ReplicationController Spec
        replicationControllerSpec.setTemplate(podTemplateSpec);
        // Add Replication Controller Spec to the Replication Controller instance
        replicationController.setSpec(replicationControllerSpec);

        // Create the replication-controller
        kubernetesClient.createReplicationController(replicationController);
    }

    public ReplicationController getReplicationController(String replicationControllerID) throws Exception {
        return kubernetesClient.getReplicationController(replicationControllerID);
    }

    public ReplicationControllerList getReplicationControllers() throws Exception {
        return kubernetesClient.getReplicationControllers();
    }

    // TODO: To be checked - does not delete the set of pods created
    public void deleteReplicationController(String replicationControllerID) throws Exception {
        kubernetesClient.deleteReplicationController(replicationControllerID);
    }
}
