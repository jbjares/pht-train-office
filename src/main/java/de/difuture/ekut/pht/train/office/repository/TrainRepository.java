package de.difuture.ekut.pht.train.office.repository;


import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface TrainRepository extends Neo4jRepository<TrainEntity, Long> {}
