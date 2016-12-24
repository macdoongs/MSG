package com.korchid.msg.mqtt.impl.paho;

import com.korchid.msg.mqtt.impl.MqttException;
import com.korchid.msg.mqtt.interfaces.IMqttMessage;

import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PahoMqttMessageWrapper implements IMqttMessage
{
	private MqttMessage message;
	
	public PahoMqttMessageWrapper(MqttMessage message)
	{
		this.message = message;
	}
	
	@Override
	public byte[] getPayload() throws MqttException
	{
		try
		{
			return message.getPayload();
		}
		catch (org.eclipse.paho.client.mqttv3.MqttException e)
		{
			throw new MqttException(e);
		}
	}

	@Override
	public int getQoS()
	{
		return message.getQos();
	}	
	
	public String toString(){
		return "PahoMqttMessageWrapper{"+message.toString()+"}";
	}

	@Override
	public boolean isRetained()
	{
		return message.isRetained();
	}

	@Override
	public boolean isDuplicate()
	{
		return message.isDuplicate();
	}
}
