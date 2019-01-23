package edu.gcp.messaging.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gcp.messaging.properties.GoogleCloudProperties;
import edu.gcp.messaging.service.DefaultMessagingService;
import edu.gcp.messaging.service.MessagingService;
import edu.gcp.messaging.service.PubsubOutboundGateway;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.cloud.gcp.pubsub.integration.AckMode;
import org.springframework.cloud.gcp.pubsub.integration.inbound.PubSubInboundChannelAdapter;
import org.springframework.cloud.gcp.pubsub.integration.outbound.PubSubMessageHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

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
            ObjectMapper objectMapper, PubsubOutboundGateway pubsubOutboundGateway) {
        return new DefaultMessagingService(properties, pubSubOperations, objectMapper, pubsubOutboundGateway);
    }

    /**
     * Spring channel for receiving messages from GCP subscription
     *
     * @return Message channel
     */
    @Bean
    public MessageChannel pubsubInputChannel() {
        return new DirectChannel();
    }

    /**
     * Adaptor listens to the subscription and passed message to spring inbound channel
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

        return adapter;
    }

    /**
     * Spring outbound channel for sending messages to GCP topic
     *
     * @return Message channel
     */
    @Bean
    public MessageChannel pubsubOutputChannel() {
        return new DirectChannel();
    }

    /**
     * Send messages received from spring outbound channel to gcp topic
     *
     * @param pubsubTemplate spring gcp template
     * @param googleCloudProperties gcp properties
     *
     * @return handler
     */
    @Bean
    @ServiceActivator(inputChannel = "pubsubOutputChannel")
    public MessageHandler messageSender(PubSubOperations pubsubTemplate, GoogleCloudProperties googleCloudProperties) {
        return new PubSubMessageHandler(pubsubTemplate, googleCloudProperties.getTopic());
    }

    /**
     * Adaptor listens to the subscription and passed message to spring inbound channel
     *
     * @param inputChannel input channel used from above bean
     */
    @Bean
    public PubSubInboundChannelAdapter messageRawChannelAdapter(
            @Qualifier("pubsubRawInputChannel") MessageChannel inputChannel,
            PubSubOperations pubSubTemplate,
            GoogleCloudProperties googleCloudProperties) {

        PubSubInboundChannelAdapter adapter =
                new PubSubInboundChannelAdapter(pubSubTemplate, googleCloudProperties.getRawSubscription());
        adapter.setOutputChannel(inputChannel);

        return adapter;
    }

    /**
     * Spring outbound channel for sending messages to GCP topic
     *
     * @return Message channel
     */
    @Bean
    public MessageChannel pubsubRawInputChannel() {
        return new DirectChannel();
    }
}
