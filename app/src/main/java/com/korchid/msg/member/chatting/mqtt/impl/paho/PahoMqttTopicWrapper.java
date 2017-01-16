package com.korchid.msg.member.chatting.mqtt.impl.paho;

import com.korchid.msg.member.chatting.mqtt.interfaces.IMqttTopic;

import org.eclipse.paho.client.mqttv3.MqttTopic;

public class PahoMqttTopicWrapper implements IMqttTopic
{
	private MqttTopic topic;
	public PahoMqttTopicWrapper(MqttTopic topic)
	{
		this.topic = topic;
	}

	@Override
	public String getName()
	{
		return topic.getName();
	}

	@Override
	public int getQoS()
	{
		return 0;
	}
}
