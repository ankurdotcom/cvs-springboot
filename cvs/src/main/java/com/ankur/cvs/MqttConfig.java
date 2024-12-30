package com.ankur.cvs;

import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.core.MessageProducer;
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory;
import org.springframework.integration.mqtt.core.MqttPahoClientFactory;
import org.springframework.integration.mqtt.inbound.MqttPahoMessageDrivenChannelAdapter;
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler;
import org.springframework.integration.mqtt.support.DefaultPahoMessageConverter;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.MessageHandler;

import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;

// @Configuration
public class MqttConfig {

    // private static final String MQTT_BROKER_URL = "tcp://your-mqtt-broker:1883";
    private static final String MQTT_BROKER_URL = "tcp://8c60fc6d63404bf38ac16fceeab41528.s1.eu.hivemq.cloud";
    private static final String MQTT_BROKER_USERNAME = "admin";
    private static final String MQTT_BROKER_PASSWORD = "hivemq";
    private static final String MQTT_CLIENT_ID = "vehicle-health-monitor";
    private static final String MQTT_TOPIC = "vehicle/health";

    //@Bean
    public MqttPahoClientFactory mqttClientFactory() {
        DefaultMqttPahoClientFactory factory = new DefaultMqttPahoClientFactory();
        MqttConnectOptions options = new MqttConnectOptions();
        options.setServerURIs(new String[]{MQTT_BROKER_URL});
        options.setUserName(MQTT_BROKER_USERNAME);
        options.setPassword(MQTT_BROKER_PASSWORD.toCharArray());
        factory.setConnectionOptions(options);
        return factory;
    }

    //@Bean
    public MessageChannel mqttInputChannel() {
        return new DirectChannel();
    }

    //@Bean
    public MessageProducer inbound() {
        MqttPahoMessageDrivenChannelAdapter adapter =
            new MqttPahoMessageDrivenChannelAdapter(MQTT_CLIENT_ID, mqttClientFactory(), MQTT_TOPIC);
        adapter.setCompletionTimeout(5000);
        adapter.setConverter(new DefaultPahoMessageConverter());
        adapter.setQos(1);
        adapter.setOutputChannel(mqttInputChannel());
        return adapter;
    }

    //@Bean
    //@ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            String payload = (String) message.getPayload();
            // Process the incoming vehicle health data
            System.out.println("Received message: " + payload);
            // TODO: Parse and store the data as needed
        };
    }

}

