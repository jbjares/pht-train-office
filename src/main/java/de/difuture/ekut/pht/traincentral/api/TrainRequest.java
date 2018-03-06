package de.difuture.ekut.pht.traincentral.api;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

@Validated
public final class TrainRequest {
	
	@JsonProperty("sparql")
	private String sparql;
	
	public String getSparql() {
		
		return this.sparql;
	}
}
