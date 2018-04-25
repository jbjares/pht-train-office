package de.difuture.ekut.pht.train.office;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


@SpringBootApplication
@EnableBinding(Sink.class)
@EnableNeo4jRepositories
public class TrainOfficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainOfficeApplication.class, args);
	}
}
