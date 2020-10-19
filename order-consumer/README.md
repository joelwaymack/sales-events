# Azure Functions in Java with an Event Hub triggers and Cosmos DB output binding

Shows how to use Azure Functions with Java to handle Event Hub events and store analysis results in a Cosmos DB. 

## Prerequisites

* [Azure CLI](https://docs.microsoft.com/cli/azure/install-azure-cli)
* [Java Developer Kit](https://aka.ms/azure-jdks), version 8
* [Maven](https://maven.apache.org)
* [Azure Functions Core Tools](https://www.npmjs.com/package/azure-functions-core-tools)

## Setup
1. Run the infrastucture setup script.
2. Run the sample locally. To process events, make sure you run the producer as well.

    ```
    mvn clean package
    mvn azure-functions:run
    ```

3. Deploy the functions to Azure.

    ```bash
    mvn azure-functions:deploy
    ```