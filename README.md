# gcp-messgaing
Demo of google cloud platform pub sub

## Pre-requisites:
1) GCP project id
2) Service account with pub sub roles. credentials.json file
3) GCP Topic
4) GCP Subscription
5) Spring-cloud-gcp dependencies please visit https://spring.io/guides/gs/messaging-gcp-pubsub/

## How mechanism work:

- input channel adaptor subscribed to subscription
- create spring channel to pass all the messages from "input channel adaptor" to output channel where adaptor send messages
- inbound channel is linked with service activator where messages are processed

#### Publish Example:
There are two ways to publish
- Use Spring-Cloud-GCP `PubSubTemplate` to publish message

- Spring channel -> outbound adaptor -> GCP topic

#### Subscribe Example:
GCP subscription -> inbound adaptor -> Spring channel
