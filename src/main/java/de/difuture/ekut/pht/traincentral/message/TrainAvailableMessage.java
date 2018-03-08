package de.difuture.ekut.pht.traincentral.message;

import java.net.URI;
import java.util.UUID;

import lombok.Data;

@Data
public final class TrainAvailableMessage {

	// ID of the available train
	private UUID trainID;
	
	// Registry host
	private URI host;	
}
