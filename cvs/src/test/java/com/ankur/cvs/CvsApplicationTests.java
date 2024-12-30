package com.ankur.cvs;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hivemq.client.mqtt.mqtt5.Mqtt5AsyncClient;

@SpringBootTest
class CvsApplicationTests {

	@Test
	void contextLoads() {
	}

	private static final String MQTT_BROKER_URL = "tcp://8c60fc6d63404bf38ac16fceeab41528.s1.eu.hivemq.cloud";
    private static final String MQTT_BROKER_USERNAME = "admin";
    private static final String MQTT_BROKER_PASSWORD = "hivemq";
    private static final String MQTT_CLIENT_ID = "vehicle-health-monitor";
    private static final String MQTT_TOPIC = "vehicle/health";

	@Autowired
	private Mqtt5AsyncClient mqtt5AsyncClient;
	
	@Test
	public void maainTest() {
        try {
            MqttClient client = new MqttClient(MQTT_BROKER_URL, MQTT_CLIENT_ID);
            client.connect();
            String payload = "{ \"vehicleId\": \"V123\", \"batteryLevel\": 85, \"speed\": 45, \"location\": \"28.6139,77.2090\" }";
            MqttMessage message = new MqttMessage(payload.getBytes());
            message.setQos(1);
            client.publish(MQTT_TOPIC, message);
            client.disconnect();
            System.out.println("end");
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

}
