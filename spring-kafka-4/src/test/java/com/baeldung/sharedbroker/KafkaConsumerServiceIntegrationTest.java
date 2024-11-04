package com.baeldung.sharedbroker;

import java.util.Collections;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.common.Node;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.context.EmbeddedKafka;

@SpringBootTest
@EmbeddedKafka(partitions = 1, topics = { "test-topic" })
class KafkaConsumerServiceIntegrationTest {

    private static final EmbeddedKafkaBroker broker = EmbeddedSharedKafkaBroker.getEmbeddedKafka();

    @Test
    public void testKafkaListener() throws ExecutionException, InterruptedException {
        String bootstrapServers = broker.getBrokersAsString();

        Map<String, Object> config = Collections.singletonMap(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        AdminClient adminClient = AdminClient.create(config);

        DescribeClusterResult clusterResult = adminClient.describeCluster();
        System.out.println("Cluster ID: " + clusterResult.clusterId()
            .get());
        for (Node node : clusterResult.nodes()
            .get()) {
            System.out.println("Broker: " + node.id() + " - " + node.host() + ":" + node.port());
        }
        adminClient.close();
    }

}