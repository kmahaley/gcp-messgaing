package edu.gcp.messaging.service;

import edu.gcp.messaging.data.PrivateMessage;

public interface MessagingService {

    /**
     * Publish message to GCP topic
     *
     * @param message message to bbe published
     */
    void publish(PrivateMessage message);

}
