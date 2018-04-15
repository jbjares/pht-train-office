package de.difuture.ekut.pht.train.idprovider.model;

import java.util.UUID;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;


/**
 *  The traincentral currently only assigns IDs to trains.
 *
 *  @author Lukas Zimmermann
 */
@Data
@Entity
public final class Train {

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private UUID id;
}
