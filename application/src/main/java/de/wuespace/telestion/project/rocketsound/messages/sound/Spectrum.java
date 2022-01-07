package de.wuespace.telestion.project.rocketsound.messages.sound;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

@SuppressWarnings("unused")
public record Spectrum(
		@JsonProperty double min,
		@JsonProperty double max,
		@JsonProperty double[] data
) implements JsonMessage {
	public Spectrum() {
		this(0.0, 0.0, null);
	}
}
