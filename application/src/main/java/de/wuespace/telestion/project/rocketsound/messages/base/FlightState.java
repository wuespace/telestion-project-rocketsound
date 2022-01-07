package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

@SuppressWarnings("unused")
public record FlightState(
		@JsonProperty int state,
		@JsonProperty String name
) implements JsonMessage {

	/**
	 * All available flight states.
	 */
	public static final String[] FLIGHT_STATES = new String[]{
			"-", "preparation", "flight", "apogee", "landing", "recovery"
	};

	public FlightState() {
		this(0, FLIGHT_STATES[0]);
	}
}
