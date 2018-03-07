package de.difuture.ekut.pht.traincentral.model;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NonNull;

@Data
@Entity
public final class Train {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	private String sparql;
	
	private TrainState trainState;
	
	@ElementCollection
	private Set<UUID> stations_visited;
	
	public Train() {}
	
	
	public Train(
			@NonNull final String sparql,
			@NonNull final TrainState state) {
		
		this.sparql = sparql;
		this.trainState = state;
		this.stations_visited = new HashSet<>();
	}
	
	public Train withStation(UUID station) {
		
		final Train resultTrain = new Train(this.sparql, this.trainState);
		resultTrain.stations_visited.add(station);
		return resultTrain;
	}
}
