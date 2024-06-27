package com.ibm.mq.spring.boot;

import org.springframework.boot.autoconfigure.service.connection.ConnectionDetails;

public interface MQConnectionDetails extends ConnectionDetails {

    String getConnName();

    String getQueueManager();

    String getChannel();

    String getUser();

    String getPassword();
}
