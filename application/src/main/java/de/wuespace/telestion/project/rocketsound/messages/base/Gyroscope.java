package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record Gyroscope(@JsonProperty double x, @JsonProperty double y, @JsonProperty double z) implements JsonMessage {

	@SuppressWarnings("unused")
	public Gyroscope() {
		this(0.0, 0.0, 0.0);
	}
}
