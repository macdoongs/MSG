package com.korchid.msg.mqtt.impl.paho;

import com.korchid.msg.mqtt.interfaces.IMqttPersistence;

import org.eclipse.paho.client.mqttv3.MqttClientPersistence;
import org.eclipse.paho.client.mqttv3.MqttPersistable;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;

import java.util.Enumeration;

public class PahoMqttClientPersistence implements MqttClientPersistence
{
	public PahoMqttClientPersistence(IMqttPersistence persistence)
	{
		// TODO Auto-generated constructor stub
	}

	@Override
	public void clear() throws MqttPersistenceException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void close() throws MqttPersistenceException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public boolean containsKey(String arg0) throws MqttPersistenceException
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public MqttPersistable get(String arg0) throws MqttPersistenceException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Enumeration keys() throws MqttPersistenceException
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void open(String arg0, String arg1) throws MqttPersistenceException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void put(String arg0, MqttPersistable arg1)
		throws MqttPersistenceException
	{
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String arg0) throws MqttPersistenceException
	{
		// TODO Auto-generated method stub

	}

}
