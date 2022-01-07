package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

@SuppressWarnings("unused")
public record NineDofData(
		@JsonProperty Accelerometer acc,
		@JsonProperty Gyroscope gyro,
		@JsonProperty Magnetometer mag
) implements JsonMessage {
	public NineDofData() {
		this(new Accelerometer(), new Gyroscope(), new Magnetometer());
	}
}
