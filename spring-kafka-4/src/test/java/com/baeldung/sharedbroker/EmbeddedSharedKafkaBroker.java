package com.baeldung.sharedbroker;

import org.springframework.kafka.KafkaException;
import org.springframework.kafka.test.EmbeddedKafkaBroker;
import org.springframework.kafka.test.EmbeddedKafkaKraftBroker;
import org.springframework.kafka.test.EmbeddedKafkaZKBroker;

public final class EmbeddedSharedKafkaBroker {

    private static EmbeddedKafkaBroker embeddedKafka = new EmbeddedKafkaKraftBroker(1,0,"test-topic").brokerListProperty("spring.kafka.bootstrap-servers");

    private static boolean started;

    public static EmbeddedKafkaBroker getEmbeddedKafka() {
        if (!started) {
            try {
                embeddedKafka.afterPropertiesSet();
            } catch (Exception e) {
                throw new KafkaException("Embedded broker failed to start", e);
            }
            started = true;
        }
        return embeddedKafka;
    }

    private EmbeddedSharedKafkaBroker() {
        super();
    }
}
