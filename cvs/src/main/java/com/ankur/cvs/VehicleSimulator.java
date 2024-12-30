package com.ankur.cvs;

import com.hivemq.client.mqtt.MqttClient;
import com.hivemq.client.mqtt.mqtt3.Mqtt3AsyncClient;

public class VehicleSimulator {

    private static final String MQTT_BROKER_URL = "8c60fc6d63404bf38ac16fceeab41528.s1.eu.hivemq.cloud";
    private static final String MQTT_BROKER_USERNAME = "cvsspringboot";
    private static final String MQTT_BROKER_PASSWORD = "cvsspringboot@1Z";
    private static final String MQTT_CLIENT_ID = "vehicle-health-monitor";
    private static final String MQTT_TOPIC = "vehicle/health";

    static Mqtt3AsyncClient client;
    
        public static void main(String[] args) {
            try {
    
                client = MqttClient.builder()
                                    .useMqttVersion3()
                                    .identifier(MQTT_CLIENT_ID)
                                    .serverHost(MQTT_BROKER_URL)
                                    .serverPort(8883)
                                    .buildAsync();
            

        client.connectWith()
        .simpleAuth()
            .username(MQTT_BROKER_USERNAME)
            .password(MQTT_BROKER_PASSWORD.getBytes())
            .applySimpleAuth()
        .send()
        .whenComplete((connAck, throwable) -> {
            if (throwable != null) {
                // handle failure
                System.out.println("1");
                System.out.println(throwable);
            } else {
                // setup subscribes or start publishing
                System.out.println("2");
            }
        });

        // client.subscribeWith()
        // .topicFilter(MQTT_TOPIC)
        // .callback(publish -> {
        //     // Process the received message
        // })
        // .send()
        // .whenComplete((subAck, throwable) -> {
        //     if (throwable != null) {
        //         // Handle failure to subscribe
        //     } else {
        //         // Handle successful subscription, e.g. logging or incrementing a metric
        //     }
        // });

        client.publishWith()
        .topic(MQTT_TOPIC)
        .payload("hello world".getBytes())
        .send()
        .whenComplete((publish, throwable) -> {
            if (throwable != null) {
                // handle failure to publish
                System.out.println("3");
                System.out.println(throwable);
            } else {
                // handle successful publish, e.g. logging or incrementing a metric
                System.out.println("4");
            }
        });

            // client.connect();
            // String payload = "{ \"vehicleId\": \"V123\", \"batteryLevel\": 85, \"speed\": 45, \"location\": \"28.6139,77.2090\" }";
            // MqttMessage message = new MqttMessage(payload.getBytes());
            // message.setQos(1);
            // client.publish(MQTT_TOPIC, message);
            // client.disconnect();

            client.disconnect();
            System.out.println("end");
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            client.disconnect();
        }
    }
}

