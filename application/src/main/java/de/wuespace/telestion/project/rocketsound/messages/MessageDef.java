package de.wuespace.telestion.project.rocketsound.messages;

import de.wuespace.telestion.project.rocketsound.messages.base.*;
import de.wuespace.telestion.project.rocketsound.messages.sound.*;
import de.wuespace.telestion.services.database.DbResponse;

import java.util.List;

public class MessageDef {
	public static void main(String[] args) {
		System.out.println("org.telestion.core.database.MongoDatabaseService/out#save/de.jvpichowski.rocketsound.messages.sound.Amplitude");
		System.out.println(new DbResponse(Amplitude.class, List.of(new Amplitude().json())).json());
		System.out.println("org.telestion.core.database.MongoDatabaseService/out#save/de.jvpichowski.rocketsound.messages.sound.Spectrum");
		System.out.println(new DbResponse(Spectrum.class, List.of(new Spectrum().json())).json());
		System.out.println("org.telestion.core.database.MongoDatabaseService/out#save/de.jvpichowski.rocketsound.messages.base.Velocity");
		System.out.println(new DbResponse(Velocity.class, List.of(new Velocity().json())).json());
		System.out.println("org.telestion.core.database.MongoDatabaseService/out#save/de.jvpichowski.rocketsound.messages.base.NineDofData");
		System.out.println(new DbResponse(NineDofData.class, List.of(new NineDofData(
				new Accelerometer(),
				new Gyroscope(),
				new Magnetometer()
		).json())).json());
		System.out.println("org.telestion.core.database.MongoDatabaseService/out#save/de.jvpichowski.rocketsound.messages.base.BaroData");
		System.out.println(new DbResponse(BaroData.class, List.of(new BaroData(
				new Pressure(),
				new Temperature(),
				new Altitude()
		).json())).json());
		System.out.println("org.telestion.core.database.MongoDatabaseService/out#save/de.jvpichowski.rocketsound.messages.base.GpsData");
		System.out.println(new DbResponse(GpsData.class, List.of(new GpsData().json())).json());
		System.out.println("org.telestion.core.database.MongoDatabaseService/out#save/de.jvpichowski.rocketsound.messages.base.FlightState");
		System.out.println(new DbResponse(FlightState.class, List.of(new FlightState().json())).json());
	}
}
