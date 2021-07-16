package de.wuespace.telestion.project.rocketsound.messages.sound;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record Amplitude(@JsonProperty double amplitude, @JsonProperty double freq1,
						@JsonProperty double freq2) implements JsonMessage {
	@SuppressWarnings("unused")
	public Amplitude() {
		this(0.0, 0.0, 0.0);
	}
}
