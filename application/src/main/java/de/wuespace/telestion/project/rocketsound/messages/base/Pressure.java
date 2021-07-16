package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record Pressure(@JsonProperty double pressure) implements JsonMessage {

	@SuppressWarnings("unused")
	public Pressure() {
		this(0.0);
	}
}
