package de.difuture.ekut.pht.traincentral.message;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

public interface TrainUpdateStreams {

	// TODO Move to Centralized Configuration
	String TRAIN_AVAILABLE = "trainavailable";


    @Input(TRAIN_AVAILABLE)
    SubscribableChannel inboundTrainAvailable();
}
