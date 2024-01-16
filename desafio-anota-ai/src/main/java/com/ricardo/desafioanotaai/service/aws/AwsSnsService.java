package com.ricardo.desafioanotaai.service.aws;


import com.amazonaws.services.sns.AmazonSNS;
import com.amazonaws.services.sns.model.Topic;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class AwsSnsService {

    AmazonSNS snsClient;
    Topic catalogTopic;

    public AwsSnsService(AmazonSNS amazonSNS, @Qualifier("catalogEventTopic") Topic catalogTopic) { //o qualifier esta mno awsSns
        this.snsClient = amazonSNS;
        this.catalogTopic = catalogTopic;
    }

    public void publish(MessageDTO message) {
        this.snsClient.publish(catalogTopic.getTopicArn(), message.toString());
    }


}
