package de.wuespace.telestion.project.rocketsound.messages;

import de.wuespace.telestion.api.message.JsonMessage;
import de.wuespace.telestion.project.rocketsound.messages.base.*;
import de.wuespace.telestion.project.rocketsound.messages.sound.*;
import de.wuespace.telestion.extension.mongodb.DbResponse;

import java.util.List;

public class MessageDef {
	public static final String PUBLISH_PREFIX = "org.telestion.core.database.MongoDatabaseService/out#save";

	public static void main(String[] args) {
		printMessage(new Amplitude());
		printMessage(new Spectrum());
		printMessage(new Velocity());
		printMessage(new NineDofData());
		printMessage(new BaroData());
		printMessage(new GpsData());
		printMessage(new FlightState());
	}

	private static void printMessage(JsonMessage message) {
		var name = message.getClass().getName();
		System.out.printf("%s/%s%n", PUBLISH_PREFIX, name);
		System.out.println(new DbResponse(List.of(message.json())).json());
	}
}
