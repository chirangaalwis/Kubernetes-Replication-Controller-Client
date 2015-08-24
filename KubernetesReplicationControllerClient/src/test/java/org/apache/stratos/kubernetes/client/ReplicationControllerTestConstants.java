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

public class ReplicationControllerTestConstants {
    protected static final String KUBERNETES_SERVICE_HOST = "127.0.0.1";
    protected static final String KUBERNETES_SERVICE_PORT = "8080";
    protected static final String SELECTOR_LABEL = "helloworld";
    protected static final String REPLICATION_CONTROLLER_ID = "helloworldrc";
    // Container specific
    protected static final String DEFAULT_DOCKER_IMAGE = "helloworld";
    protected static final String CONTAINER_NAME = "helloworld";
    protected static final int CPU_CORES = 1;
    protected static final int MEMORY_ALLOCATION = 512;
    protected static final int EXPOSED_PORT = 8080;
}
