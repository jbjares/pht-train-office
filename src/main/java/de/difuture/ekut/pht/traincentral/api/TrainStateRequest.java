package de.difuture.ekut.pht.traincentral.api;

import java.util.UUID;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

@Validated
public final class TrainStateRequest {
	
	@JsonProperty("train")
	private UUID trainId;
	
	public UUID getId() {
		
		return this.trainId;
	}
}
