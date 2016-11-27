package com.korchid.msg.impl.paho;

import com.korchid.msg.impl.MqttException;
import com.korchid.msg.interfaces.IMqttClient;
import com.korchid.msg.interfaces.IMqttClientFactory;
import com.korchid.msg.interfaces.IMqttPersistence;

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
