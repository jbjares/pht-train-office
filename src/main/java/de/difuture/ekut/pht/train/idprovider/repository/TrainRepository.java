package de.difuture.ekut.pht.train.idprovider.repository;

import java.util.UUID;

import de.difuture.ekut.pht.train.idprovider.model.Train;
import org.springframework.data.repository.CrudRepository;

public interface TrainRepository extends CrudRepository<Train, UUID> {}
