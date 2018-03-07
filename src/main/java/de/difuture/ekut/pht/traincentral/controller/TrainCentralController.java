package de.difuture.ekut.pht.traincentral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.difuture.ekut.pht.traincentral.api.TrainRequest;
import de.difuture.ekut.pht.traincentral.api.TrainResponse;
import de.difuture.ekut.pht.traincentral.api.TrainStateRequest;
import de.difuture.ekut.pht.traincentral.api.TrainStationRequest;
import de.difuture.ekut.pht.traincentral.model.Train;
import de.difuture.ekut.pht.traincentral.model.TrainState;
import de.difuture.ekut.pht.traincentral.repository.TrainRepository;
import lombok.NonNull;


@RestController
public class TrainCentralController {

	private final TrainRepository trainRepository;
	
	@Autowired
	public TrainCentralController(
			@NonNull final TrainRepository trainRepository) {
		
		this.trainRepository = trainRepository;
	}
	
	
	@RequestMapping(value = "/train", method = RequestMethod.POST)
	public Train postTrain(@RequestBody final TrainRequest trainRequest) {
				
		// Store new train and return
		return this.trainRepository.save(new Train(trainRequest.getSparql(), TrainState.WAIT_FOR_UPLOAD));
	}
	
	@RequestMapping(value = "/train", method = RequestMethod.GET)
	public TrainResponse getTrain() {
				
		return new TrainResponse(this.trainRepository.findAll());
	}
	
	
	@RequestMapping(value = "/train/upload", method = RequestMethod.PUT)
	public Train setUploadState(@RequestBody TrainStateRequest trainStateRequest) {
		
		// Handle missing train
		final Train train = this.trainRepository.findById(trainStateRequest.getId()).get();
		
		// TODO Handle missing train and wrong state
		
		// TODO Might be useful to implement Spring State Machine to keep track of the states that
		// the train goes through
		return this.trainRepository.save(new Train(train.getSparql(), TrainState.UPLOADED));
	}
	
	
	@RequestMapping(value = "/train/station", method = RequestMethod.PUT)
	public Train addStation(@RequestBody TrainStationRequest trainStationRequest) {
		
		// Handle missing train
		final Train train = this.trainRepository.findById(trainStationRequest.getTrainId()).get();
		
		// TODO Handle missing train and wrong state
		
		// TODO Might be useful to implement Spring State Machine to keep track of the states that
		// the train goes through
		return this.trainRepository.save(train.withStation(trainStationRequest.getStationId()));
	}
}
