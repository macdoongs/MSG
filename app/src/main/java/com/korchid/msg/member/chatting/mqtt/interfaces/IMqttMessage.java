package com.korchid.msg.member.chatting.mqtt.interfaces;

import com.korchid.msg.member.chatting.mqtt.impl.MqttException;

public interface IMqttMessage
{
	public int getQoS();
	public byte[] getPayload() throws MqttException;
	public boolean isRetained();	
	public boolean isDuplicate();
}
