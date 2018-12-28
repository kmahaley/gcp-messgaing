package edu.gcp.messaging.service;

import org.springframework.integration.annotation.MessagingGateway;

@MessagingGateway(defaultRequestChannel = "pubsubOutputChannel")
public interface PubsubOutboundGateway {

    /**
     * Send message to GCP topic
     *
     * @param text payload
     */
    void sendToPubsub(String text);
}
