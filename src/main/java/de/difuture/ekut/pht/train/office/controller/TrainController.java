package de.difuture.ekut.pht.train.office.controller;

import java.net.URI;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import de.difuture.ekut.pht.lib.core.api.APITrain;
import de.difuture.ekut.pht.lib.core.messages.TrainUpdate;
import de.difuture.ekut.pht.lib.core.neo4j.entity.Train;
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

	private static final ResponseEntity<APITrain> NOT_FOUND = ResponseEntity.notFound().build();
	
	private final TrainEntityRepository trainRepository;

    @Autowired
    public TrainController(
            @NonNull final TrainEntityRepository trainRepository) {

        this.trainRepository = trainRepository;
    }

    @StreamListener(target=Sink.INPUT)
    public void sink(TrainUpdate trainUpdate) {

        // Only update if the train already exists (otherwise the trainDestinationID is meaningless)
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
	 * Creates new APITrain by assigning UUID.
     *
	 */
	@RequestMapping(method = RequestMethod.POST)
	public APITrain createTrain() {

        return this.trainRepository.save(new Train()).toTrain();
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<APITrain> getAll() {

	    return StreamSupport.stream(this.trainRepository.findAll().spliterator(), false)
                .map(Train::toTrain)
                .collect(Collectors.toList());
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<APITrain> getOne(
			@PathVariable("id") Long trainID) {

		return this.trainRepository
                .findById(trainID)
                .map(x -> ResponseEntity.ok(x.toTrain()))
                .orElse(NOT_FOUND);
	}

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public ResponseEntity<APITrain> deleteOne(
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
