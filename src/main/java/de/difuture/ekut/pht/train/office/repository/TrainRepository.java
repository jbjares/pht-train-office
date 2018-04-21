package de.difuture.ekut.pht.train.office.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface TrainRepository extends CrudRepository<TrainEntity, UUID> {}
