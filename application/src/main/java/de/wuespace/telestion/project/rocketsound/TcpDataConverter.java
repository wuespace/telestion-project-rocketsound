package de.wuespace.telestion.project.rocketsound;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;
import de.wuespace.telestion.api.verticle.TelestionConfiguration;
import de.wuespace.telestion.api.verticle.TelestionVerticle;
import de.wuespace.telestion.api.verticle.trait.WithEventBus;
import de.wuespace.telestion.api.verticle.trait.WithSharedData;
import de.wuespace.telestion.project.rocketsound.messages.base.*;
import de.wuespace.telestion.project.rocketsound.messages.sound.*;
import de.wuespace.telestion.services.connection.TcpConn;
import de.wuespace.telestion.extension.mongodb.DbResponse;
import io.vertx.core.Vertx;
import io.vertx.core.shareddata.LocalMap;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("unused")
public final class TcpDataConverter extends TelestionVerticle<TcpDataConverter.Configuration>
		implements WithEventBus, WithSharedData {

	public static final String PUBLISH_PREFIX = "de.wuespace.telestion.services.database.MongoDatabaseService/out#save";

	public static final String[] FLIGHT_STATES = FlightState.FLIGHT_STATES;

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		// publish random data when verticle is deployed
		vertx.deployVerticle(new TcpDataConverter()).onSuccess(res -> {
			vertx.eventBus().publish("tcpIncoming", new TcpConn.Data(null, new byte[]{
					(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x50, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0xB7, (byte) 0x5D, (byte) 0xE8, (byte) 0x3D, (byte) 0xED, (byte) 0xA0,
					(byte) 0x52, (byte) 0x3E, (byte) 0x81, (byte) 0x5B, (byte) 0x77, (byte) 0xBF, (byte) 0xC3,
					(byte) 0xF5, (byte) 0x68, (byte) 0x3F, (byte) 0x33, (byte) 0x33, (byte) 0x33, (byte) 0x40,
					(byte) 0x3E, (byte) 0x0A, (byte) 0x57, (byte) 0x3F, (byte) 0x9E, (byte) 0x41, (byte) 0x03,
					(byte) 0xBE, (byte) 0x29, (byte) 0xAE, (byte) 0x8A, (byte) 0x3E
			}).json());
			vertx.eventBus().publish("tcpIncoming", new TcpConn.Data(null, new byte[]{
					(byte) 0x88, (byte) 0x11, (byte) 0x02, (byte) 0x3F, (byte) 0x7F, (byte) 0xC6, (byte) 0xAC,
					(byte) 0x41, (byte) 0xFA, (byte) 0x6F, (byte) 0xA6, (byte) 0x47, (byte) 0x3E, (byte) 0x60,
					(byte) 0xBA, (byte) 0x44, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x7F, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00,
					(byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0x0D, (byte) 0x0A
			}).json());
		});
	}

	public record Configuration(
			@JsonProperty String inAddress
	) implements TelestionConfiguration {
		public Configuration() {
			this("tcpIncoming");
		}
	}

	@Override
	public void onStart() {
		setDefaultConfig(new Configuration());
		register(getConfig().inAddress(), this::handle, TcpConn.Data.class);
		logger.info("TcpDataConv started");
	}

	private void handle(TcpConn.Data data) {
		var oldData = getData();
		var newData = new byte[oldData.length + data.data().length];

		// copy old data to new data
		System.arraycopy(oldData, 0, newData, 0, oldData.length);
		// append incoming data to new data
		System.arraycopy(data.data(), 0, newData, oldData.length, data.data().length);

		// interpret received data
		newData = interpret(newData);

		// store it back in local storage
		setData(newData);
	}

	private byte[] interpret(byte[] data) {
		var result = data;

		ByteBuffer buffer = ByteBuffer.wrap(result);
		buffer.order(ByteOrder.LITTLE_ENDIAN);

		for (int i = 0; i < buffer.limit() - 4; i++) {
			// check if the first 4 bytes contain the prefix/start of an encoded message (here 0xFF,0xFF,0xFF,0xFF)
			if (buffer.get(i) == (byte) 0xFF && buffer.get(i + 1) == (byte) 0xFF &&
					buffer.get(i + 2) == (byte) 0xFF && buffer.get(i + 3) == (byte) 0xFF) {

				int len = buffer.getInt(i + 4);

				// if the buffer has more content than the reported message length, lets parse the data
				if (i + len <= buffer.limit()) {
					parseMessage(buffer.slice(i, len));

					// remove parsed message from result
					result = new byte[buffer.limit() - (i + len)];
					if (result.length > 0) buffer.get(i + len, result, 0, result.length);
					buffer = ByteBuffer.wrap(result);
					i = 0;
				}
			}
		}

		return result;
	}

	private void parseMessage(ByteBuffer buffer) {
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		int START_BYTES = buffer.getInt();
		int MESSAGE_LENGTH = buffer.getInt();

		logger.debug("Parsing message with len: {}", MESSAGE_LENGTH);

		// extract data piece by piece from buffer
		float accX = buffer.getFloat();
		float accY = buffer.getFloat();
		float accZ = buffer.getFloat();
		float gyroX = buffer.getFloat();
		float gyroY = buffer.getFloat();
		float gyroZ = buffer.getFloat();
		float magX = buffer.getFloat();
		float magY = buffer.getFloat();
		float magZ = buffer.getFloat();
		float temp = buffer.getFloat();
		float press = buffer.getFloat();
		float alt = buffer.getFloat();
		float latitude = buffer.getFloat();
		float longitude = buffer.getFloat();
		float amp = buffer.getFloat();
		float freq1 = buffer.getFloat();
		float freq2 = buffer.getFloat();
		double[] fftBins = new double[16];
		for (int i = 0; i < fftBins.length; i++) {
			fftBins[i] = buffer.getFloat();
		}

		// check if last int is valid EOL
		int EOL = buffer.getInt();
		if (EOL != 0xa0dffff) {
			System.out.println("Found defect message " + Integer.toHexString(EOL));
			return;
		}

		///
		/// GPS
		///
		boolean fix = true;
		// overwrite GPS data if sensor found no position
		if (latitude == 0 && longitude == 0) {
			latitude = 54.324429f;
			longitude = 11.024064f;
			fix = false;
		}

		///
		/// Sounding Rocket
		///
		if (freq1 > 0.15f) {
			freq1 = 0.15f;
		}
		if (freq2 > 0.15f) {
			freq2 = 0.15f;
		}

		//freq1 = amp;
		//freq2 = amp;

		///
		/// Position and Velocity
		///
		float esthVelo = amp + freq1 + freq2;
		float messVelo = 0; //(float) vertx.sharedData().getLocalMap("DataConv").getOrDefault("velo", 0.0f);

		float acc = accX;
		if (acc > -0.05f && acc < 0.05f) {
			acc = -1.0f;
		}
		if (acc > -0.15f && acc < -0.95f) {
			messVelo = 0;
		}
		messVelo += 10 * (1.0f + acc);
		//vertx.sharedData().getLocalMap("DataConv").put("velo", messVelo);

		// TODO: Get current state from telemetry message
		var stateIdx = 1;

		publishData(new Amplitude(amp, freq1, freq2));
		publishData(new Spectrum(
				Arrays.stream(fftBins).min().getAsDouble(),
				Arrays.stream(fftBins).max().getAsDouble(),
				fftBins
		));
		publishData(new Velocity(esthVelo, messVelo));
		publishData(new NineDofData(
				new Accelerometer(accX, accY, accZ),
				new Gyroscope(gyroX, gyroY, gyroZ),
				new Magnetometer(magX, magY, magZ)
		));
		publishData(new BaroData(
				new Pressure(press),
				new Temperature(temp),
				new Altitude(alt)
		));
		publishData(new GpsData(3, fix, latitude, longitude, System.currentTimeMillis()));
		publishData(new FlightState(stateIdx, FLIGHT_STATES[stateIdx]));
	}

	private void publishData(JsonMessage data) {
		var name = data.getClass().getName();
		publish(PUBLISH_PREFIX + "/" + name, new DbResponse(List.of(data.json())));
	}

	private byte[] getData() {
		return localMap().getOrDefault("buffer", new byte[]{});
	}

	private void setData(byte[] data) {
		localMap().put("buffer", data);
	}

	private LocalMap<String, byte[]> localMap() {
		return localMap("buffered-data");
	}
}
