package de.difuture.ekut.pht.traincentral.api;

import java.util.UUID;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

@Validated
public final class TrainStationRequest {

	@JsonProperty("train")
	private UUID trainId;

	@JsonProperty("station")
	private UUID stationId;

	public UUID getTrainId() {

		return this.trainId;
	}

	public UUID getStationId() {

		return this.stationId;
	}
}
