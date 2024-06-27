package com.ibm.mq.spring.boot;

import com.github.dockerjava.api.command.InspectContainerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.utility.DockerImageName;

// Note: This should go into spring-boot-starter-testcontainers, or its own lib
public class MQContainer extends GenericContainer<MQContainer> {

  private static final Logger LOG = LoggerFactory.getLogger(MQContainer.class);

  private static final String QUEUE_MANAGER = "QM1";
  private static final String APP_USER = "app";
  private static final String APP_PASSWORD = APP_USER;
  private static final String WEB_USER = "admin";
  private static final String WEB_PASSWORD = WEB_USER;
  private static final String APP_CHANNEL = "DEV.APP.SVRCONN";

  public MQContainer(DockerImageName imageName) {
    super(imageName);
    //TODO: validate docker image name
    withEnv("LICENSE", "accept");
    withEnv("MQ_QMGR_NAME", QUEUE_MANAGER);
    withEnv("MQ_APP_PASSWORD", APP_PASSWORD);
    withEnv("MQ_ADMIN_PASSWORD", WEB_PASSWORD);
    withExposedPorts(1414, 9443);
    waitingFor(Wait.forLogMessage(".*Started web server.*", 1));
  }

  @Override
  protected void containerIsStarted(InspectContainerResponse containerInfo) {
    LOG.info(
        "Started IBM MQ container. The Web UI is available under: https://localhost:{} (login: {}:{})",
        getWebServerPort(),
        WEB_USER,
        WEB_PASSWORD);
  }

  public int getPort() {
    return getMappedPort(1414);
  }

  public int getWebServerPort() {
    return getMappedPort(9443);
  }

  public String getConnName() {
    return String.format("%s(%d)", getHost(), getPort());
  }

  public String getQueueManager() {
    return QUEUE_MANAGER;
  }

  public String getAppUser() {
    return APP_USER;
  }

  public String getAppPassword() {
    return APP_PASSWORD;
  }

  public String getChannel() {
    return APP_CHANNEL;
  }
}
