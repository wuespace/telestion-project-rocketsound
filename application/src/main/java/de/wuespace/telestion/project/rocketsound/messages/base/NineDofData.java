package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record NineDofData(@JsonProperty Accelerometer acc, @JsonProperty Gyroscope gyro,
						  @JsonProperty Magnetometer mag) implements JsonMessage {

	@SuppressWarnings("unused")
	public NineDofData() {
		this(null, null, null);
	}
}
