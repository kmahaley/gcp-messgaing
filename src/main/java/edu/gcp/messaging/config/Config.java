package edu.gcp.messaging.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gcp.messaging.properties.GoogleCloudProperties;
import edu.gcp.messaging.service.DefaultMessagingService;
import edu.gcp.messaging.service.MessagingService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;

@Configuration
@EnableConfigurationProperties({GoogleCloudProperties.class})
public class Config {

    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper();
    }

    @Bean
    public MessagingService messagingService(
            GoogleCloudProperties properties, PubSubOperations pubSubOperations,
            ObjectMapper objectMapper) {
        return new DefaultMessagingService(properties, pubSubOperations, objectMapper);
    }

    @Bean
    public MessageChannel pubsubInputChannel() {
        return new DirectChannel();
    }

    /**
     *
     * @param inputChannel input channel used from above bean
     */
    @Bean
    public PubSubInboundChannelAdapter messageChannelAdapter(
            @Qualifier("pubsubInputChannel") MessageChannel inputChannel,
            PubSubOperations pubSubTemplate,
            GoogleCloudProperties googleCloudProperties) {

        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, googleCloudProperties.getSubscription());
        adapter.setOutputChannel(inputChannel);
        adapter.setAckMode(AckMode.MANUAL);

        return adapter;
    }
}
