package com.korchid.msg.mqtt.interfaces;

import com.korchid.msg.mqtt.impl.MqttException;

public interface IMqttClientFactory
{
	public IMqttClient create(String host, int port, String clientId, IMqttPersistence persistence) throws MqttException;
}
