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
package org.apache.stratos.kubernetes.client.interfaces;

import io.fabric8.kubernetes.api.model.ContainerPort;
import io.fabric8.kubernetes.api.model.ReplicationController;
import io.fabric8.kubernetes.api.model.ReplicationControllerList;

import java.util.List;

/**
 * Interface for Replication Controller handling methods
 */
public interface ReplicationControllerClientAPIInterface {
    /**
     * Create a Replication Controller
     * @param replicationControllerID       id of the replication controller
     * @param selectorLabel                 replication controller pod selector label
     * @param replicas                      number of pod replicas to be created
     * @param containerName                 name of the Docker Container
     * @param dockerImage                   name of the Docker Image to be used for pod creation
     * @param cpu                           number of cpu cores
     * @param memory                        memory allocation in mega bytes
     * @param ports                         Docker Container ports to be opened
     * @throws Exception
     **/
    void createReplicationController(String replicationControllerID, String selectorLabel,
            int replicas, String containerName, String dockerImage, int cpu,
            int memory, List<ContainerPort> ports) throws Exception;

    /**
     * Returns a Replication Controller specified by the Replication Controller id
     * @param replicationControllerID       id of the replication controller
     * @return Replication Controller specified by the id
     * @throws Exception
     **/
    ReplicationController getReplicationController(String replicationControllerID)
            throws Exception;

    /**
     * Returns a list of Replication Controllers already created
     * @return a list of Replication Controllers already created
     * @throws Exception
     **/
    ReplicationControllerList getReplicationControllers() throws Exception;

    /**
     * Deletes the Replication Controller specified by the Replication Controller id
     * @param replicationControllerID       id of the replication controller
     * @throws Exception
     **/
    void deleteReplicationController(String replicationControllerID)
            throws Exception;
}
