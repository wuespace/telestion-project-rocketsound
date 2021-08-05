package de.wuespace.telestion.project.rocketsound;

import de.wuespace.telestion.project.rocketsound.messages.base.*;
import de.wuespace.telestion.project.rocketsound.messages.sound.*;
import de.wuespace.telestion.extension.mongodb.DbResponse;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;

import java.time.Duration;
import java.util.List;
import java.util.logging.Logger;

public final class MockRocketPublisher extends AbstractVerticle {
	private static final Logger logger = Logger.getLogger(MockRocketPublisher.class.getName());

	private static final String publishPrefix = "de.wuespace.telestion.services.database.MongoDatabaseService/out#save";

	@Override
	public void start(Promise<Void> startPromise) {
		vertx.setPeriodic(Duration.ofSeconds(1).toMillis(), h -> {
			logger.info("Publish new fake data");

			vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.sound.Amplitude",
					new DbResponse(List.of(new Amplitude(Math.random(), Math.random(), Math.random()).json())).json());
			vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.sound.Spectrum",
					new DbResponse(List.of(new Spectrum(0, 10, new double[]{
							Math.random(), Math.random(), Math.random(), Math.random(), Math.random(),
							Math.random(), Math.random(), Math.random(), Math.random(), Math.random()}).json())).json());
			vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.base.Velocity",
					new DbResponse(List.of(new Velocity(Math.random(), Math.random()).json())).json());
			vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.base.NineDofData",
					new DbResponse(List.of(new NineDofData(
							new Accelerometer(Math.random(), Math.random(), Math.random()),
							new Gyroscope(Math.random(), Math.random(), Math.random()),
							new Magnetometer(Math.random(), Math.random(), Math.random())
					).json())).json());
			vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.base.BaroData",
					new DbResponse(List.of(new BaroData(
							new Pressure(Math.random()),
							new Temperature(Math.random()),
							new Altitude(Math.random())
					).json())).json());
			vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.base.GpsData",
					new DbResponse(List.of(new GpsData(1, false, 49.70518 + Math.random() * 0.002, 9.89143 + Math.random() * 0.002, System.currentTimeMillis()).json())).json());
			var stateIdx = (int) (Math.random() * 6);
			vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.base.FlightState",
					new DbResponse(List.of(new FlightState(stateIdx,
							new String[]{"-", "preparation", "flight", "apogee", "landing", "recovery"}[stateIdx]).json())).json());
		});

		startPromise.complete();
	}
}
