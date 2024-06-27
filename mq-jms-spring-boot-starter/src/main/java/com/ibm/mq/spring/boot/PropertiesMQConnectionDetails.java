package com.ibm.mq.spring.boot;

class PropertiesMQConnectionDetails implements MQConnectionDetails {

    private final MQConfigurationProperties properties;

    public PropertiesMQConnectionDetails(MQConfigurationProperties properties) {
        this.properties = properties;
    }

    @Override
    public String getConnName() {
        return properties.getConnName();
    }

    @Override
    public String getQueueManager() {
        return properties.getQueueManager();
    }

    @Override
    public String getChannel() {
        return properties.getChannel();
    }

    @Override
    public String getUser() {
        return properties.getUser();
    }

    @Override
    public String getPassword() {
        return properties.getPassword();
    }
}
