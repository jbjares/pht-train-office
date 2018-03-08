package de.difuture.ekut.pht.traincentral.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.difuture.ekut.pht.traincentral.api.TrainRequest;
import de.difuture.ekut.pht.traincentral.api.TrainResponse;
import de.difuture.ekut.pht.traincentral.api.TrainStationRequest;
import de.difuture.ekut.pht.traincentral.message.TrainAvailableMessage;
import de.difuture.ekut.pht.traincentral.message.TrainUpdateStreams;
import de.difuture.ekut.pht.traincentral.model.Train;
import de.difuture.ekut.pht.traincentral.model.TrainState;
import de.difuture.ekut.pht.traincentral.repository.TrainRepository;
import lombok.NonNull;


@RestController
public class TrainController {

	private final TrainRepository trainRepository;

	@Autowired
	public TrainController(
			@NonNull final TrainRepository trainRepository) {

		this.trainRepository = trainRepository;
	}

	@StreamListener(TrainUpdateStreams.TRAIN_AVAILABLE)
	public void handleTrainUpdate(@Payload TrainAvailableMessage message) {

		// Once the train becomes available, set the state of the train in the
		// traincentral to available		
		final Optional<Train> train = this.trainRepository.findById(message.getTrainID());

		if (train.isPresent()) {

			this.trainRepository.save(train.get().withState(TrainState.AVAILABLE));
		}		
	}	

	@RequestMapping(value = "/train", method = RequestMethod.POST)
	public Train postTrain(@RequestBody final TrainRequest trainRequest) {

		// Store new train and return
		return this.trainRepository.save(new Train(trainRequest.getSparql(), TrainState.WAIT_FOR_UPLOAD));
	}

	@RequestMapping(value = "/train", method = RequestMethod.GET)
	public TrainResponse doGet() {

		return new TrainResponse(this.trainRepository.findAll());
	}


	@RequestMapping(value = "/train/station", method = RequestMethod.POST)
	public Train addStation(@RequestBody TrainStationRequest trainStationRequest) {

		// Handle missing train
		final Train train = this.trainRepository.findById(trainStationRequest.getTrainId()).get();

		// TODO Handle missing train and wrong state

		// TODO Might be useful to implement Spring State Machine to keep track of the states that
		// the train goes through
		return this.trainRepository.save(train.withStation(trainStationRequest.getStationId()));
	}
}
