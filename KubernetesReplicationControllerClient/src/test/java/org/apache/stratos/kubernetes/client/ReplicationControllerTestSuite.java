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

import io.fabric8.kubernetes.api.model.ContainerPort;
import io.fabric8.kubernetes.api.model.ReplicationController;
import io.fabric8.kubernetes.api.model.ReplicationControllerList;
import org.apache.stratos.kubernetes.client.interfaces.ReplicationControllerClientAPIInterface;

import java.util.ArrayList;
import java.util.List;

public class ReplicationControllerTestSuite {
    private final ReplicationControllerClientAPIInterface REPLICATION_CONTROLLER_CLIENT;

    public ReplicationControllerTestSuite() {
        REPLICATION_CONTROLLER_CLIENT = new ReplicationControllerClientAPI(
                "http://" + ReplicationControllerTestConstants.KUBERNETES_SERVICE_HOST + ":"
                        + ReplicationControllerTestConstants.KUBERNETES_SERVICE_PORT);

        //        REPLICATION_CONTROLLER_CLIENT = new ReplicationControllerClientAPI("http://localhost:8080");
    }

    public void createReplicationController(int replicas) {
        List<ContainerPort> exposedPorts = new ArrayList<ContainerPort>();
        ContainerPort port = new ContainerPort();
        port.setContainerPort(ReplicationControllerTestConstants.EXPOSED_PORT);
        exposedPorts.add(port);

        try {
            REPLICATION_CONTROLLER_CLIENT
                    .createReplicationController(ReplicationControllerTestConstants.REPLICATION_CONTROLLER_ID,
                            ReplicationControllerTestConstants.SELECTOR_LABEL, replicas,
                            ReplicationControllerTestConstants.CONTAINER_NAME,
                            ReplicationControllerTestConstants.DEFAULT_DOCKER_IMAGE,
                            ReplicationControllerTestConstants.CPU_CORES,
                            ReplicationControllerTestConstants.MEMORY_ALLOCATION, exposedPorts);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void deleteReplicationController(String replicationControllerID) {
        try {
            REPLICATION_CONTROLLER_CLIENT.deleteReplicationController(replicationControllerID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getReplicationControllers() {
        try {
            ReplicationControllerList list = REPLICATION_CONTROLLER_CLIENT.getReplicationControllers();
            List<ReplicationController> replicationControllers = list.getItems();

            for(ReplicationController controller : replicationControllers) {
                System.out.println(controller.getMetadata().getName());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
