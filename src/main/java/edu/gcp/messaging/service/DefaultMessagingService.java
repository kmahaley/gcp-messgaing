package edu.gcp.messaging.service;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gcp.messaging.data.PrivateMessage;
import edu.gcp.messaging.properties.GoogleCloudProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gcp.pubsub.core.PubSubOperations;
import org.springframework.integration.annotation.ServiceActivator;

@Slf4j
@Data
@AllArgsConstructor
public class DefaultMessagingService implements MessagingService {

    private GoogleCloudProperties gcpProperties;

    private PubSubOperations pubSubTemplate;

    private ObjectMapper objectMapper;

    private PubsubOutboundGateway messagingGateway;

    @Override
    public void publish(PrivateMessage message) {
        try {
            final String payload = objectMapper.writeValueAsString(message);
            log.info("Sending message: " + payload);


            pubSubTemplate.publish(gcpProperties.getTopic(), payload);

        } catch (JsonProcessingException ex) {
            log.info("Error in writing data");
        }


    }

    @Override
    public void publishOnSpringOutBoundChannel(PrivateMessage message) {
        try {
            final String payload = objectMapper.writeValueAsString(message);
            log.info("Sending message on spring outbound channel: " + payload);

            messagingGateway.sendToPubsub(payload);

        } catch (JsonProcessingException ex) {
            log.info("Error in writing data");
        }
    }

    /**
     * Received message from inbound channel adaptor
     * Method name should match the channel "pubsubInputChannel" check config
     *
     * @param payload message received from subscription
     */
    @ServiceActivator(inputChannel = "pubsubInputChannel")
    public void messageReceiver(String payload) {
        log.info("Message arrived! Payload: " + payload);
        try {

            final PrivateMessage privateMessage = objectMapper.readValue(payload, PrivateMessage.class);
            System.out.println(privateMessage);
        } catch (IOException ex) {
            log.info("Error in reading the payload");
        }
    }


    @ServiceActivator(inputChannel = "pubsubRawInputChannel")
    public void rawMessageReceiver(String payload) {
        log.info("Raw Message arrived! Payload: " + payload);

    }

}
