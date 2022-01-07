package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

@SuppressWarnings("unused")
public record Position(
		@JsonProperty double x,
		@JsonProperty double y,
		@JsonProperty double z
) implements JsonMessage {
	public Position() {
		this(0.0, 0.0, 0.0);
	}
}
