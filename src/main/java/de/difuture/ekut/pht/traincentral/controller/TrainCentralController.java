package de.difuture.ekut.pht.traincentral.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import de.difuture.ekut.pht.traincentral.api.TrainRequest;
import de.difuture.ekut.pht.traincentral.model.Train;
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
	public Train registry(@RequestBody final TrainRequest trainRequest) {
				
		// Store new train and return
		return this.trainRepository.save(new Train(trainRequest.getSparql()));
	}	
}
