
package edu.gcp.messaging.config;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.pubsub.v1.Publisher;
import com.google.pubsub.v1.ProjectTopicName;
import edu.gcp.messaging.properties.GoogleCloudProperties;
import edu.gcp.messaging.service.DefaultGoogleMessagingService;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * GCP configs
 */
@Configuration
@EnableConfigurationProperties({GoogleCloudProperties.class})
public class GcpConfig {


    @Bean
    public Publisher projectTopicName(GoogleCloudProperties properties) throws IOException {
        final ProjectTopicName topicName = ProjectTopicName.of(properties.getGcpProjectId(), properties.getTopic());

        return Publisher.newBuilder(topicName).build();
    }

    @Bean
    public DefaultGoogleMessagingService defaultGoogleMessagingService(Publisher publisher, ObjectMapper objectMapper) {
        return new DefaultGoogleMessagingService(publisher, objectMapper);
    }
}
