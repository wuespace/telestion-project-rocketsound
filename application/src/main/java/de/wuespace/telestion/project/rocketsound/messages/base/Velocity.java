package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record Velocity(@JsonProperty double measured, @JsonProperty double estimated) implements JsonMessage {

	@SuppressWarnings("unused")
	public Velocity() {
		this(0.0f, 0.0f);
	}
}
