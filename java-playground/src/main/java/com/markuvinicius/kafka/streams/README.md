# Group Lifecycle Management Integration Tests
Repository dedicated to the integration tests of the Group Lifecycle Management Application

## Pre-requirements
The following pre-requirements are needed to configure and execute the integration tests:

* Docker engine
* Minikube
* Minikube configured with the ngm-repo secrets.
* Helm
* Helm configured with the ngm-repo

See the [Integration Test Repository](https://gitlabce.tools.aws.vodafone.com/IOTNGM/analytics-iot-integration-tests) to further details.

### Windows Users
WSL 2 is required to launch the Kubernetes cluster on top of docker engine through minikube. This dependency is caused by the lack of support to Linux containers on docker engine distributions outside the Docker Desktop application for Windows OS.

See [Microsoft Official Docs](https://docs.microsoft.com/en-us/windows/wsl/install) to details and installation guide.

## Running Integration Tests
Follow the steps bellow to launch the automated integration test suit on NGM Group Manager application.

All the commands should be run on the application repository directory.

1. Check/Add your test cases files specifications to the ./integrationTests/testCases folder.
   Test cases specification need to be compatible with a [QA Event Generator](https://gitlabce.tools.aws.vodafone.com/IOTNGM/qa-projects-ngm-events-generator) test class.
```
ls ./integrationTests
```
2. Build the application locally
```
mvn clean package
```
3. Run the test script
```
./integrationTests/runIntegrationTests.sh <namespace> <docker-registry>
```
Parameters: 
- namespace: (Optional) - The kubernetes namespace to deploy the applications against. The default value is "default"
- docker-registry: (Optional) - The docker registry to take the base images from. The default value is set to [NGM Nexus](internal-a755bb1a3b9a642a5bcd395dcb6c8ace-702009650.eu-central-1.elb.amazonaws.com:8082/iotngm/) 

During the test execution, some information may be requested if not automatically found (eg. docker credentials).

4. Check the result.

