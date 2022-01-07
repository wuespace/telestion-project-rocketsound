package de.wuespace.telestion.project.rocketsound;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;
import de.wuespace.telestion.api.verticle.TelestionConfiguration;
import de.wuespace.telestion.api.verticle.TelestionVerticle;
import de.wuespace.telestion.api.verticle.trait.WithEventBus;
import de.wuespace.telestion.project.rocketsound.messages.base.*;
import de.wuespace.telestion.project.rocketsound.messages.sound.*;
import de.wuespace.telestion.extension.mongodb.DbResponse;
import io.vertx.core.Vertx;

import java.time.Duration;
import java.util.List;

@SuppressWarnings("unused")
public final class MockRocketPublisher extends TelestionVerticle<MockRocketPublisher.Configuration>
		implements WithEventBus {

	public static final String PUBLISH_PREFIX = TcpDataConverter.PUBLISH_PREFIX;

	public static final String[] FLIGHT_STATES = FlightState.FLIGHT_STATES;

	public static void main(String[] args) {
		var vertx = Vertx.vertx();
		vertx.deployVerticle(new MockRocketPublisher());
	}

	public record Configuration(
			@JsonProperty int interval
	) implements TelestionConfiguration {
		public Configuration() {
			this(1);
		}
	}

	@Override
	public void onStart() {
		setDefaultConfig(new Configuration());
		var duration = Duration.ofSeconds(getConfig().interval());
		vertx.setPeriodic(duration.toMillis(), this::sendFakeData);
	}

	private void sendFakeData(Long intervalId) {
		logger.info("Publish new fake data");

		// some random state index
		var stateIdx = (int) (Math.random() * 6);

		publishData(new Amplitude(Math.random(), Math.random(), Math.random()));
		publishData(new Spectrum(0, 10, new double[]{
				Math.random(), Math.random(), Math.random(), Math.random(), Math.random(),
				Math.random(), Math.random(), Math.random(), Math.random(), Math.random()}
		));
		publishData(new Velocity(Math.random(), Math.random()));
		publishData(new NineDofData(
				new Accelerometer(Math.random(), Math.random(), Math.random()),
				new Gyroscope(Math.random(), Math.random(), Math.random()),
				new Magnetometer(Math.random(), Math.random(), Math.random())
		));
		publishData(new BaroData(
				new Pressure(Math.random()),
				new Temperature(Math.random()),
				new Altitude(Math.random())
		));
		publishData(new GpsData(
				1, false,
				49.70518 + Math.random() * 0.002,
				9.89143 + Math.random() * 0.002,
				System.currentTimeMillis()
		));
		publishData(new FlightState(stateIdx, FLIGHT_STATES[stateIdx]));
	}

	private void publishData(JsonMessage data) {
		var name = data.getClass().getName();
		publish(PUBLISH_PREFIX + "/" + name, new DbResponse(List.of(data.json())));
	}
}
