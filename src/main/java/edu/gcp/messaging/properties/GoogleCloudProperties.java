package edu.gcp.messaging.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties("tdm.pubsub")
public class GoogleCloudProperties {

    private String topic;

    private String subscription;

    private String gcpProjectId;
}
