package com.korchid.msg.member.chatting.mqtt.interfaces;

import com.korchid.msg.member.chatting.mqtt.impl.MqttException;

public interface IMqttClientFactory
{
	public IMqttClient create(String host, int port, String clientId, IMqttPersistence persistence) throws MqttException;
}
