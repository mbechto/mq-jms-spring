package com.ibm.mq.spring.boot;

import org.springframework.boot.testcontainers.service.connection.ContainerConnectionDetailsFactory;
import org.springframework.boot.testcontainers.service.connection.ContainerConnectionSource;

// Note: This should go into spring-boot-starter-testcontainers, or its own lib
public class MQContainerConnectionDetailsFactory
    extends ContainerConnectionDetailsFactory<MQContainer, MQConnectionDetails> {

  @Override
  protected MQConnectionDetails getContainerConnectionDetails(
      ContainerConnectionSource<MQContainer> source) {
    return new MQContainerConnectionDetails(source);
  }

  private static final class MQContainerConnectionDetails
      extends ContainerConnectionDetails<MQContainer> implements MQConnectionDetails {

    private MQContainerConnectionDetails(ContainerConnectionSource<MQContainer> source) {
      super(source);
    }

    @Override
    public String getConnName() {
      return getContainer().getConnName();
    }

    @Override
    public String getQueueManager() {
      return getContainer().getQueueManager();
    }

    @Override
    public String getChannel() {
      return getContainer().getChannel();
    }

    @Override
    public String getUser() {
      return getContainer().getAppUser();
    }

    @Override
    public String getPassword() {
      return getContainer().getAppPassword();
    }
  }
}
