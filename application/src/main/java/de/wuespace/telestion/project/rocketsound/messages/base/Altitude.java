package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

@SuppressWarnings("unused")
public record Altitude(@JsonProperty double altitude) implements JsonMessage {
	public Altitude() {
		this(0.0);
	}
}
