package edu.gcp.messaging.service;

import edu.gcp.messaging.data.PrivateMessage;

public interface MessagingService {

    void publish(PrivateMessage message);

}
