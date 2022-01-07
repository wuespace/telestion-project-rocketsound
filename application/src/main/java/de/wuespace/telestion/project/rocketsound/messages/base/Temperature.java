package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

@SuppressWarnings("unused")
public record Temperature(@JsonProperty double temperature) implements JsonMessage {
	public Temperature() {
		this(0.0);
	}
}
