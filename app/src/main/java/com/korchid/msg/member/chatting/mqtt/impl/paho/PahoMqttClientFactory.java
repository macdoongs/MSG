package com.korchid.msg.member.chatting.mqtt.impl.paho;

import com.korchid.msg.member.chatting.mqtt.impl.MqttException;
import com.korchid.msg.member.chatting.mqtt.interfaces.IMqttClient;
import com.korchid.msg.member.chatting.mqtt.interfaces.IMqttClientFactory;
import com.korchid.msg.member.chatting.mqtt.interfaces.IMqttPersistence;

public class PahoMqttClientFactory implements IMqttClientFactory
{	
	@Override
	public IMqttClient create(String host, int port, String clientId,
							  IMqttPersistence persistence) throws MqttException
	{
		PahoMqttClientPersistence persistenceImpl = null;
		if(persistence != null){
			persistenceImpl = new PahoMqttClientPersistence(persistence);
		}
		
		// TODO Auto-generated method stub
		return new PahoMqttClientWrapper(
			"tcp://"+host+":"+port, clientId, persistenceImpl);
	}
}
