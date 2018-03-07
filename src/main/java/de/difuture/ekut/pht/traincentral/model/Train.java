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
	
	// Private copy constructor
	private Train(final Train other) {

		this.id = other.id;
		this.sparql = other.sparql;
		this.stations_visited = new HashSet<>(other.stations_visited);
		this.trainState = other.trainState;
	}
	
	
	public Train withStation(UUID station) {
		
		final Train resultTrain = new Train(this);
		resultTrain.stations_visited.add(station);
		return resultTrain;
	}
	
	public Train withState(TrainState state) {
		
		final Train resultTrain = new Train(this);
		resultTrain.trainState = state;
		return resultTrain;
	}
}
