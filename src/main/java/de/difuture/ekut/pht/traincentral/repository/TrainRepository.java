package de.difuture.ekut.pht.traincentral.repository;

import java.util.UUID;

import org.springframework.data.repository.CrudRepository;

import de.difuture.ekut.pht.traincentral.model.Train;

public interface TrainRepository extends CrudRepository<Train, UUID> {}
