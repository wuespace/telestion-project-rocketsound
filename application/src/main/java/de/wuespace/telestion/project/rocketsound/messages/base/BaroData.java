package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record BaroData(@JsonProperty Pressure press, @JsonProperty Temperature temp,
					   @JsonProperty Altitude alt) implements JsonMessage {

	@SuppressWarnings("unused")
	public BaroData() {
		this(null, null, null);
	}
}
