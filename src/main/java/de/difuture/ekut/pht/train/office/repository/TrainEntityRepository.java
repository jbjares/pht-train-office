package de.difuture.ekut.pht.train.office.repository;


import de.difuture.ekut.pht.lib.core.neo4j.entity.Train;
import org.springframework.data.neo4j.repository.Neo4jRepository;


public interface TrainEntityRepository extends Neo4jRepository<Train, Long> {}
