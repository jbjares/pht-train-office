package de.difuture.ekut.pht.train.office.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import de.difuture.ekut.pht.lib.core.model.Train;
import de.difuture.ekut.pht.train.office.repository.TrainEntity;
import de.difuture.ekut.pht.train.office.repository.TrainRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.NonNull;

import javax.validation.Valid;


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

	@RequestMapping(method = RequestMethod.POST)
	public Train postTrain(@RequestBody @Valid Train train) {

        return this.trainRepository.save(new TrainEntity(train)).toTrain();
	}

	@RequestMapping(method = RequestMethod.GET)
	public Iterable<Train> doGetAll() {

	    List<Train> result = new ArrayList<>();
		this.trainRepository.findAll().forEach(x -> result.add(x.toTrain()));
        return result;
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Train> doGetOne(
			@PathVariable("id") UUID trainID) {

		return this.trainRepository
                .findById(trainID)
                .map(x -> ResponseEntity.ok(x.toTrain()))
                .orElse(NOT_FOUND);
	}
}
