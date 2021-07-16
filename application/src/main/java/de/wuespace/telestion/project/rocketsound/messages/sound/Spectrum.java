package de.wuespace.telestion.project.rocketsound.messages.sound;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record Spectrum(
		@JsonProperty double min,
		@JsonProperty double max,
		@JsonProperty double[] data) implements JsonMessage {
	@SuppressWarnings("unused")
	public Spectrum() {
		this(0.0, 0.0, null);
	}
}
