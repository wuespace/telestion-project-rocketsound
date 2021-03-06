package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

@SuppressWarnings("unused")
public record GpsData(
		@JsonProperty int satCount,
		@JsonProperty boolean fix,
		@JsonProperty double latitude,
		@JsonProperty double longitude,
		@JsonProperty long time
) implements JsonMessage {
	public GpsData() {
		this(0, false, 0.0f, 0.0f, 0);
	}
}
