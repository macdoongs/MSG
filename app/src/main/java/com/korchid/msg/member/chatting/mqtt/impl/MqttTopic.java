package com.korchid.msg.member.chatting.mqtt.impl;

import android.os.Parcel;
import android.os.Parcelable;

import com.korchid.msg.member.chatting.mqtt.interfaces.IMqttTopic;

import java.io.Serializable;

public class MqttTopic implements IMqttTopic, Parcelable, Serializable
{
	private String name;
	private int qos;
	
	public MqttTopic(String name){
		setName(name);
	}
	
	@Override
	public String getName()
	{
		return this.name;
	}
	
	public void setName(String name)
	{
		this.name = name;
	}

	@Override
	public int getQoS()
	{
		return this.qos;
	}
	
	public void setQoS(int qos)
	{
		this.qos = qos;
	}

	// Parcelable을 구현하는 코드, 반드시 아래의 코드들은 추가해주어야 한다.
	// Parcelable 구조체에서 MqttTopic 읽어오는 함수.
	public MqttTopic(Parcel in) {
		this.name = in.readString();
		this.qos = in.readInt();
	}

	@Override
	public int describeContents() {
		return 0;
	}

	// Parcelable 구조체에 기록하는 함수.
	// 주의 할 점은 반드시 생성자에서 읽어오는 순서와, 기록하는 순서가 같아야 한다.
	@Override
	public void writeToParcel(Parcel parcel, int i) {
		parcel.writeString(this.name);
		parcel.writeInt(this.qos);
	}

	// Parcelable을 생성하는 코드. 반드시 추가해주어야 한다.
	public static final Parcelable.Creator<MqttTopic> CREATOR = new Parcelable.Creator<MqttTopic>() {
		public MqttTopic createFromParcel(Parcel in) {
			return new MqttTopic(in);
		}
		public MqttTopic[] newArray (int size) {
			return new MqttTopic[size];
		}
	};
}
