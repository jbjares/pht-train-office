package de.difuture.ekut.pht.train.office.controller;

import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import de.difuture.ekut.pht.lib.core.api.Train;
import de.difuture.ekut.pht.lib.core.messages.TrainUpdate;
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

    @Autowired
    public TrainController(
            @NonNull final TrainRepository trainRepository) {

        this.trainRepository = trainRepository;
    }

    @StreamListener(target=Sink.INPUT)
    public void sink(TrainUpdate trainUpdate) {

        System.out.println("TRAIN_OFFICE_HAS_RECEIVED_TRAIN_UPDATE");

        this.trainRepository.save(

                this.trainRepository
                        .findById(trainUpdate.getTrainID())
                        .map(trainEntity -> {

                            final URI uri = trainUpdate.getTrainRegistryURI();
                            if (uri != null) {

                                trainEntity.setTrainRegistryURI(uri);
                            }
                            return trainEntity;
                        })
                        .orElse(new TrainEntity(trainUpdate))
        );
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
}
