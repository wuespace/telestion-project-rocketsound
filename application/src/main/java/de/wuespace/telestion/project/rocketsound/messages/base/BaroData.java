package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

@SuppressWarnings("unused")
public record BaroData(
		@JsonProperty Pressure press,
		@JsonProperty Temperature temp,
		@JsonProperty Altitude alt
) implements JsonMessage {
	public BaroData() {
		this(new Pressure(), new Temperature(), new Altitude());
	}
}
