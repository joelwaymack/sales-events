package com.waymack.handlers;

import com.waymack.models.Order;
import com.microsoft.azure.functions.annotation.Cardinality;
import com.microsoft.azure.functions.annotation.CosmosDBOutput;
import com.microsoft.azure.functions.annotation.EventHubOutput;
import com.microsoft.azure.functions.annotation.EventHubTrigger;
import com.microsoft.azure.functions.annotation.FunctionName;
import com.microsoft.azure.functions.annotation.TimerTrigger;
import com.microsoft.azure.functions.ExecutionContext;
import com.microsoft.azure.functions.OutputBinding;

public class OrderHandler {

    @FunctionName("ProcessOrders")
    @EventHubOutput(
        name = "event",
        eventHubName = "", // blank because the value is included in the connection string
        connection = "ProcessOrderEventHubConnectionString")
    public Order[] ProcessOrders(
        @EventHubTrigger(
            name = "msg",
            eventHubName = "", // blank because the value is included in the connection string
            cardinality = Cardinality.MANY,
            connection = "NewOrderEventHubConnectionString")
            Order[] orders,
        final ExecutionContext context) {

            // Process orders.
            for (int i = 0; i < orders.length; i++) {
                orders[i].process();
                context.getLogger().info(orders[i].toString());
            }

            return orders;
    }

    @FunctionName("ShipOrders")
    @CosmosDBOutput(name = "database",
        databaseName = "Sales",
        collectionName = "Orders",
        connectionStringSetting = "AzureCosmosDBConnection")
    public Order[] ShipOrders(
        @EventHubTrigger(
            name = "msg",
            eventHubName = "", // blank because the value is included in the connection string
            cardinality = Cardinality.MANY,
            connection = "ProcessOrderEventHubConnectionString")
            Order[] orders,
        final ExecutionContext context) {

            // Ship orders.
            for (int i = 0; i < orders.length; i++) {
                orders[i].ship();
                context.getLogger().info(orders[i].toString());
            }
        
            return orders;
    }
}