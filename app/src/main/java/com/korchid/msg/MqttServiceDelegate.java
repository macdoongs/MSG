package com.korchid.msg;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.korchid.msg.service.MqttService;
import com.korchid.msg.service.MqttService.ConnectionStatus;

import java.util.ArrayList;
import java.util.List;

public class MqttServiceDelegate
{
	private static final String TAG = "MqttServiceDelegate";
	//public static String topic = "";

	public interface MessageHandler{
		public void handleMessage(String topic, byte[] payload);
	}
	
	public interface StatusHandler{
		public void handleStatus(ConnectionStatus status, String reason);
	}
	
	public static void startService(Context context, String topic){
		Log.d(TAG, "startService");
		Log.d(TAG, "topic : " + topic);
		Intent svc = new Intent(context, MqttService.class);
		svc.putExtra(MqttService.MQTT_MSG_RECEIVED_MSG, topic);
		MqttService.mqttTopic = topic;

		context.startService(svc); 
	}
	
	public static void stopService(Context context){
		Log.d(TAG, "stopService");
		Intent svc = new Intent(context, MqttService.class);
		context.stopService(svc); 
	}
	
	public static void publish(Context context, String topic, byte[] payload)
	{
		Log.d(TAG, "publish");
		Intent actionIntent = new Intent(context, MqttService.class);
        actionIntent.setAction(MqttService.MQTT_PUBLISH_MSG_INTENT);
        actionIntent.putExtra(MqttService.MQTT_PUBLISH_MSG_TOPIC, topic);
        actionIntent.putExtra(MqttService.MQTT_PUBLISH_MSG, payload);
        context.startService(actionIntent);
	}
	
	public static class StatusReceiver extends BroadcastReceiver
	{
		private List<StatusHandler> statusHandlers = new ArrayList<StatusHandler>();
		
		public void registerHandler(StatusHandler handler){
			Log.d(TAG, "StatusReceiver : registerHandler");

			if(!statusHandlers.contains(handler)){
				statusHandlers.add(handler);
			}
		}
		
		public void unregisterHandler(StatusHandler handler){
			Log.d(TAG, "StatusReceiver : unregisterHandler");

			if(statusHandlers.contains(handler)){
				statusHandlers.remove(handler);
			}
		}
		
		public void clearHandlers(){
			Log.d(TAG, "StatusReceiver : clearHandlers");

			statusHandlers.clear();
		}
		
		public boolean hasHandlers(){
			Log.d(TAG, "StatusReceiver : hasHandlers");

			return statusHandlers.size() > 0;
		}
		
		@Override
		public void onReceive(Context context, Intent intent)
		{
			Log.d(TAG, "StatusReceiver : onReceive");

			Bundle notificationData = intent.getExtras();
			ConnectionStatus statusCode = 
					ConnectionStatus.class.getEnumConstants()[notificationData.getInt(
							MqttService.MQTT_STATUS_CODE)];	
	        String statusMsg = notificationData.getString(
	        		MqttService.MQTT_STATUS_MSG);	
			
	        for(StatusHandler statusHandler : statusHandlers){
	        	statusHandler.handleStatus(statusCode, statusMsg);
	        }
		} 
	}
	
	public static class MessageReceiver extends BroadcastReceiver
	{  		
		private List<MessageHandler> messageHandlers = new ArrayList<MessageHandler>();
		
		public void registerHandler(MessageHandler handler){
			Log.d(TAG, "MessageReceiver : registerHandler");

			if(!messageHandlers.contains(handler)){
				messageHandlers.add(handler);
			}
		}
		
		public void unregisterHandler(MessageHandler handler){
			Log.d(TAG, "MessageReceiver : unregisterHandler");

			if(messageHandlers.contains(handler)){
				messageHandlers.remove(handler);
			}
		}
		
		public void clearHandlers(){
			Log.d(TAG, "MessageReceiver : clearHandlers");

			messageHandlers.clear();
		}
		
		public boolean hasHandlers(){
			Log.d(TAG, "MessageReceiver : hasHandlers");

			return messageHandlers.size() > 0;
		}
		
	    @Override
	    public void onReceive(Context context, Intent intent)
	    {
			Log.d(TAG, "MessageReceiver : onReceive");

	        Bundle notificationData = intent.getExtras();
	        String topic = notificationData.getString(MqttService.MQTT_MSG_RECEIVED_TOPIC);
			byte[] payload  = notificationData.getByteArray(MqttService.MQTT_MSG_RECEIVED_MSG);

			Log.d(TAG, "Delegate Topic : " + topic);
			Log.d(TAG, "Delegate payload : " + payload.toString());



	        
	        for(MessageHandler messageHandler : messageHandlers){
	        	messageHandler.handleMessage(topic, payload);
	        }
	    }  
	} 
}
