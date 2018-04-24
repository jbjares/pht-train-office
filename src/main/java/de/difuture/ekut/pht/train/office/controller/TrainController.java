package de.difuture.ekut.pht.train.office.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import de.difuture.ekut.pht.lib.core.messages.TrainUpdate;
import de.difuture.ekut.pht.lib.core.model.Train;
import de.difuture.ekut.pht.train.office.repository.TrainEntity;
import de.difuture.ekut.pht.train.office.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.NonNull;


@RestController
@RequestMapping(value = "/train")
public class TrainController {

	private static final ResponseEntity<Train> NOT_FOUND = ResponseEntity.notFound().build();
	
	private final TrainRepository trainRepository;


    @StreamListener(target=Sink.INPUT)
    public void sink(TrainUpdate trainUpdate) {

        final URI registryURI = trainUpdate.getTrainRegistryURI();
        // Update the trainRegistryURI of the train
        final TrainEntity trainEntity = this.trainRepository
                .findById(trainUpdate.getTrainID())
                .orElse(new TrainEntity(trainUpdate.getTrainID(), registryURI));
        trainEntity.setTrainRegistryURI(registryURI);
        this.trainRepository.saveAndFlush(trainEntity);
    }

	@Autowired
	public TrainController(
			@NonNull final TrainRepository trainRepository) {

		this.trainRepository = trainRepository;
	}

	/**
	 * Creates new Train by assigning UUID.
     *
	 */
	@RequestMapping(method = RequestMethod.POST)
	public Train createTrain() {

        return this.trainRepository.save(new TrainEntity()).toTrain();
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Train> getAll() {

	    final List<Train> result = new ArrayList<>();
		this.trainRepository.findAll().forEach(x -> result.add(x.toTrain()));
        return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Train> getOne(
			@PathVariable("id") UUID trainID) {

		return this.trainRepository
                .findById(trainID)
                .map(x -> ResponseEntity.ok(x.toTrain()))
                .orElse(NOT_FOUND);
	}
}
