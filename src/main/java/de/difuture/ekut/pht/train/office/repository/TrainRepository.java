package de.difuture.ekut.pht.train.office.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;


public interface TrainRepository extends JpaRepository<TrainEntity, UUID> {}
