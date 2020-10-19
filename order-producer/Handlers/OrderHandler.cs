using System;
using System.Collections.Generic;
using System.Threading.Tasks;
using Microsoft.Azure.WebJobs;
using Microsoft.Extensions.Logging;
using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;
using Waymack.Sales.Models;

#pragma warning disable CS1998

namespace Waymack.Sales.Handlers
{
    public static class OrderHandler
    {
        private static JsonSerializerSettings settings = new JsonSerializerSettings { ContractResolver = new CamelCasePropertyNamesContractResolver() };

        [FunctionName("NewOrders")]
        public async static Task Run([TimerTrigger("*/5 * * * * *")]TimerInfo myTimer,
            [EventHub("new-orders", Connection = "EventHubConnectionString")]IAsyncCollector<string> outputEvents,
            ILogger log)
        {
            try
            {
                var random = new Random();

                var orders = Order.CreateOrders(random.Next(1, 50));
                var orderTasks = new List<Task>();
                
                foreach(var order in orders)
                {
                    orderTasks.Add(outputEvents.AddAsync(JsonConvert.SerializeObject(order, settings)));
                }

                Task.WaitAll(orderTasks.ToArray());
                log.LogInformation($"Posted {orders.Count} orders");
            }
            catch (Exception e)
            {
                log.LogError(e, $"Failure: {e.Message}");
            }
        }
    }
}
