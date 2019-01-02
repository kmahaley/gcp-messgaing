package edu.gcp.messaging.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.protobuf.ByteString;
import com.google.pubsub.v1.PubsubMessage;
import edu.gcp.messaging.data.PrivateMessage;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Using google client libraries
 */
@AllArgsConstructor
@Slf4j
public class DefaultGoogleMessagingService implements MessagingService {

    private Publisher publisher;

    private ObjectMapper objectMapper;

    @Override
    public void publish(PrivateMessage message) {
        log.info("Inside google library method");

        try {
            final String payload = objectMapper.writeValueAsString(message);
            log.info("Sending message: " + payload);

            ByteString data = ByteString.copyFromUtf8(payload);
            PubsubMessage pubsubMessage = PubsubMessage
                    .newBuilder()
                    .setData(data)
                    .build();

            publisher.publish(pubsubMessage);

        } catch (JsonProcessingException ex) {
            log.info("Error in writing data");
        }


    }

    @Override
    public void publishOnSpringOutBoundChannel(PrivateMessage message) {
        log.error("not implemented");
    }
}
