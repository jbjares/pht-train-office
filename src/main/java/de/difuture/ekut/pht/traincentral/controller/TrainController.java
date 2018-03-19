package de.difuture.ekut.pht.traincentral.controller;

import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.difuture.ekut.pht.traincentral.api.TrainResponse;
import de.difuture.ekut.pht.traincentral.model.Train;
import de.difuture.ekut.pht.traincentral.repository.TrainRepository;
import lombok.NonNull;


@RestController
public class TrainController {

	private static final ResponseEntity<Train> NOT_FOUND = ResponseEntity.notFound().build();
	
	private final TrainRepository trainRepository;

	@Autowired
	public TrainController(
			@NonNull final TrainRepository trainRepository) {

		this.trainRepository = trainRepository;
	}


	@RequestMapping(value = "/train", method = RequestMethod.POST)
	public Train postTrain() {

		// Store new train and return
		return this.trainRepository.save(new Train());
	}

	@RequestMapping(value = "/train", method = RequestMethod.GET)
	public TrainResponse doGetAll() {

		return new TrainResponse(this.trainRepository.findAll());
	}

	@RequestMapping(value = "/train/{id}", method = RequestMethod.GET)
	public ResponseEntity<Train> doGetOne(
			@PathVariable("id") UUID trainID) {

		final Optional<Train> train = this.trainRepository.findById(trainID);
		return train.isPresent() ? ResponseEntity.ok(train.get()) : NOT_FOUND;
	}
}