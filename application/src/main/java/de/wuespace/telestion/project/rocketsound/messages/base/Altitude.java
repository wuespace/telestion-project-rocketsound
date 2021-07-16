package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record Altitude(@JsonProperty double altitude) implements JsonMessage {

	@SuppressWarnings("unused")
	public Altitude() {
		this(0.0);
	}
}
