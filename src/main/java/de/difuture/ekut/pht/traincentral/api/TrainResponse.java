package de.difuture.ekut.pht.traincentral.api;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import de.difuture.ekut.pht.traincentral.model.Train;
import lombok.NonNull;

@Validated
public final class TrainResponse {
	
	@JsonProperty("trains")
	private final Iterable<Train> trains;
	
	public TrainResponse(@NonNull final Iterable<Train> trains) {
		
		this.trains = trains;
	}	
}
