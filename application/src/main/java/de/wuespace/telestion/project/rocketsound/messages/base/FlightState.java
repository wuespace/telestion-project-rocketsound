package de.wuespace.telestion.project.rocketsound.messages.base;

import com.fasterxml.jackson.annotation.JsonProperty;
import de.wuespace.telestion.api.message.JsonMessage;

public record FlightState(@JsonProperty int state, @JsonProperty String name) implements JsonMessage {

	@SuppressWarnings("unused")
	public FlightState() {
		this(0, null);
	}

	public static void main(String[] args) {
		System.out.println(new FlightState(5, "Recovery").json());
	}
}
