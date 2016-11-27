package com.korchid.msg.interfaces;

import com.korchid.msg.impl.MqttException;

public interface IMqttClientFactory
{
	public IMqttClient create(String host, int port, String clientId, IMqttPersistence persistence) throws MqttException;
}
