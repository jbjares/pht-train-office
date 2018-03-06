package de.difuture.ekut.pht.traincentral.model;

import java.util.UUID;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NonNull;

@Data
@Entity
public final class Train {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
	
	private String sparql;
	
	public Train() {}
	
	public Train(
			@NonNull final String sparql) {
		
		this.sparql = sparql;
	}
}
