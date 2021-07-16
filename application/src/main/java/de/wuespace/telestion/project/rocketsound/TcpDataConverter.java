package de.wuespace.telestion.project.rocketsound;

import de.wuespace.telestion.api.message.JsonMessage;
import de.wuespace.telestion.project.rocketsound.messages.base.*;
import de.wuespace.telestion.project.rocketsound.messages.sound.*;
import de.wuespace.telestion.services.connection.TcpConn;
import de.wuespace.telestion.services.database.DbResponse;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

@SuppressWarnings("ALL")
public final class TcpDataConverter extends AbstractVerticle {
	private static final Logger logger = Logger.getLogger(TcpDataConverter.class.getName());

	private static final String publishPrefix = "de.wuespace.telestion.services.database.MongoDatabaseService/out#save";

	@Override
	public void start(Promise<Void> startPromise) {
		logger.info("TcpDataConv started");

		getVertx().eventBus().consumer("tcpIncoming", h -> JsonMessage.on(TcpConn.Data.class, h, data -> {
			// pull old data from shared storage
			byte[] oldData = (byte[]) getVertx().sharedData().getLocalMap("BufferedData").getOrDefault("buffer", new byte[0]);
			byte[] newData = new byte[oldData.length + data.data().length];

			// copy old data to new data
			System.arraycopy(oldData, 0, newData, 0, oldData.length);
			// append incoming data to new data
			System.arraycopy(data.data(), 0, newData, oldData.length, data.data().length);

			// interpret received data
			ByteBuffer buffer = ByteBuffer.wrap(newData);
			buffer.order(ByteOrder.LITTLE_ENDIAN);
			for (int i = 0; i < buffer.limit() - 4; i++) {
				if (buffer.get(i) == (byte) 0xFF && buffer.get(i + 1) == (byte) 0xFF &&
						buffer.get(i + 2) == (byte) 0xFF && buffer.get(i + 3) == (byte) 0xFF) {
					int len = buffer.getInt(i + 4);

					if (i + len <= buffer.limit()) {
						parseMessage(buffer.slice(i, len));

						// cut last message
						newData = new byte[buffer.limit() - (i + len)];
						if (newData.length > 0) buffer.get(i + len, newData, 0, newData.length);
						buffer = ByteBuffer.wrap(newData);
						i = 0;
					}
				}
			}

			// push new data into shared storage
			getVertx().sharedData().getLocalMap("BufferedData").put("buffer", newData);
		}));

		startPromise.complete();
	}

	private void parseMessage(ByteBuffer buffer) {
		buffer.order(ByteOrder.LITTLE_ENDIAN);
		int START_BYTES = buffer.getInt();
		int MESSAGE_LENGTH = buffer.getInt();

		System.out.println("Parsing message with len: " + MESSAGE_LENGTH);

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

		// publish results
		vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.sound.Amplitude",
				new DbResponse(Amplitude.class, List.of(new Amplitude(amp, freq1, freq2).json())).json());
		vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.sound.Spectrum",
				new DbResponse(Spectrum.class, List.of(new Spectrum(Arrays.stream(fftBins).min().getAsDouble(), Arrays.stream(fftBins).max().getAsDouble(), fftBins).json())).json());
		vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.base.Velocity",
				new DbResponse(Velocity.class, List.of(new Velocity(esthVelo, messVelo).json())).json());
		vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.base.NineDofData",
				new DbResponse(NineDofData.class, List.of(new NineDofData(
						new Accelerometer(accX, accY, accZ),
						new Gyroscope(gyroX, gyroY, gyroZ),
						new Magnetometer(magX, magY, magZ)
				).json())).json());
		vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.base.BaroData",
				new DbResponse(BaroData.class, List.of(new BaroData(
						new Pressure(press),
						new Temperature(temp),
						new Altitude(alt)
				).json())).json());
		vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.base.GpsData",
				new DbResponse(GpsData.class, List.of(new GpsData(3, fix, latitude, longitude, System.currentTimeMillis()).json())).json());
		var stateIdx = 1;
		vertx.eventBus().publish(publishPrefix + "/de.wuespace.telestion.project.rocketsound.messages.base.FlightState",
				new DbResponse(FlightState.class, List.of(new FlightState(stateIdx,
						new String[]{"-", "preparation", "flight", "apogee", "landing", "recovery"}[stateIdx]).json())).json());
	}

	public static void main(String[] args) {
		Vertx vertx = Vertx.vertx();
		vertx.deployVerticle(new TcpDataConverter());
		vertx.eventBus().publish("tcpIncoming", new TcpConn.Data(null, new byte[]{
				(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0x50, (byte) 0x00, (byte) 0x00, (byte) 0x00
				, (byte) 0xB7, (byte) 0x5D, (byte) 0xE8, (byte) 0x3D, (byte) 0xED, (byte) 0xA0, (byte) 0x52, (byte) 0x3E
				, (byte) 0x81, (byte) 0x5B, (byte) 0x77, (byte) 0xBF, (byte) 0xC3, (byte) 0xF5, (byte) 0x68, (byte) 0x3F
				, (byte) 0x33, (byte) 0x33, (byte) 0x33, (byte) 0x40, (byte) 0x3E, (byte) 0x0A, (byte) 0x57, (byte) 0x3F
				, (byte) 0x9E, (byte) 0x41, (byte) 0x03, (byte) 0xBE, (byte) 0x29, (byte) 0xAE, (byte) 0x8A, (byte) 0x3E
		}).json());
		vertx.eventBus().publish("tcpIncoming", new TcpConn.Data(null, new byte[]{
				(byte) 0x88, (byte) 0x11, (byte) 0x02, (byte) 0x3F, (byte) 0x7F, (byte) 0xC6, (byte) 0xAC, (byte) 0x41
				, (byte) 0xFA, (byte) 0x6F, (byte) 0xA6, (byte) 0x47, (byte) 0x3E, (byte) 0x60, (byte) 0xBA, (byte) 0x44
				, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
				, (byte) 0x7F, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00
				, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0x0D, (byte) 0x0A
		}).json());
	}
}
