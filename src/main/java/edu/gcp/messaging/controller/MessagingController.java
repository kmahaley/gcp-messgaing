package edu.gcp.messaging.controller;


import edu.gcp.messaging.data.PrivateMessage;
import edu.gcp.messaging.service.DefaultGoogleMessagingService;
import edu.gcp.messaging.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/messages")
public class MessagingController {

    @Autowired
    MessagingService messagingService;

    @Autowired
    DefaultGoogleMessagingService googleMessagingService;

    @PostMapping("/publish")
    public void publishMessage(@RequestBody PrivateMessage message){

        messagingService.publish(message);

    }


    @PostMapping("/publish/out-bound-channel")
    public void publishOnSpringOutBoundChannel(@RequestBody PrivateMessage message){

        messagingService.publishOnSpringOutBoundChannel(message);

    }

    @PostMapping("/publish/gcp")
    public void publishMessageUsing(@RequestBody PrivateMessage message){

        googleMessagingService.publish(message);

    }

}
