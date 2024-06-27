package com.ibm.mq.spring.boot;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.TestComponent;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.messaging.Message;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.utility.DockerImageName;

import static java.lang.Thread.sleep;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@EnableAutoConfiguration
@Import(TestcontainersTest.Config.class)
public class TestcontainersTest {

    private static final String QNAME = "DEV.QUEUE.1";

    @Autowired
    JmsTemplate jmsTemplate;

    @Test
    public void test() throws InterruptedException {
        jmsTemplate.convertAndSend(QNAME, "Hello World");
        while (Listener.lastMessage == null) sleep(500);
        assertThat(Listener.lastMessage).isEqualTo("Hello World");
    }

    @TestConfiguration
    static class Config {
        @Bean
        @ServiceConnection
        public MQContainer mqContainer() {
            return new MQContainer(DockerImageName.parse("icr.io/ibm-messaging/mq:latest"));
        }

        @Bean
        public Listener listener() {
            return new Listener();
        }
    }

    @TestComponent
    static class Listener {
        static String lastMessage;

        @JmsListener(destination = QNAME)
        public void listen(Message<String> message) {
            lastMessage = message.getPayload();
        }
    }
}
