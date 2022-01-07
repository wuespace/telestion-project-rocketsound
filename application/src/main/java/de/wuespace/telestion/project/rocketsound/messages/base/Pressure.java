package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

@SuppressWarnings("unused")
public record Pressure(@JsonProperty double pressure) implements JsonMessage {
	public Pressure() {
		this(0.0);
	}
}
