package de.difuture.ekut.pht.train.office.controller;

import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import de.difuture.ekut.pht.lib.core.api.Train;
import de.difuture.ekut.pht.lib.core.messages.TrainUpdate;
import de.difuture.ekut.pht.lib.core.neo4j.entity.TrainEntity;
import de.difuture.ekut.pht.train.office.repository.TrainEntityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.NonNull;


@CrossOrigin
@RestController
@RequestMapping(value = "/train")
public class TrainController {

	private static final ResponseEntity<Train> NOT_FOUND = ResponseEntity.notFound().build();
	
	private final TrainEntityRepository trainRepository;

    @Autowired
    public TrainController(
            @NonNull final TrainEntityRepository trainRepository) {

        this.trainRepository = trainRepository;
    }

    @StreamListener(target=Sink.INPUT)
    public void sink(TrainUpdate trainUpdate) {

        // Only update if the train already exists (otherwise the id is meaningless)
        final URI trainRegistryURI = trainUpdate.getTrainRegistryURI();
        final Long id = trainUpdate.getTrainID();

        if (trainRegistryURI != null && id != null) {

            this.trainRepository.findById(id)
                    .ifPresent(trainEntity -> {

                        trainEntity.setTrainRegistryURI(trainRegistryURI);
                        this.trainRepository.save(trainEntity);
                    });
        }
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

	    return StreamSupport.stream(this.trainRepository.findAll().spliterator(), false)
                .map(TrainEntity::toTrain)
                .collect(Collectors.toList());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Train> getOne(
			@PathVariable("id") Long trainID) {

		return this.trainRepository
                .findById(trainID)
                .map(x -> ResponseEntity.ok(x.toTrain()))
                .orElse(NOT_FOUND);
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<Train> deleteOne(
            @PathVariable("id") Long trainID) {

        return this.trainRepository
                .findById(trainID)
                .map((trainEntity -> {
                    this.trainRepository.delete(trainEntity);
                    return ResponseEntity.ok(trainEntity.toTrain());
                    }))
                .orElse(NOT_FOUND);
    }
}
