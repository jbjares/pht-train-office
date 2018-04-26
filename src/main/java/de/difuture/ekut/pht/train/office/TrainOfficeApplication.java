package de.difuture.ekut.pht.train.office;

import de.difuture.ekut.pht.config.Neo4jDenbiConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.neo4j.Neo4jDataAutoConfiguration;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;
import org.springframework.context.annotation.Import;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;


@SpringBootApplication(
    exclude = Neo4jDataAutoConfiguration.class,
    scanBasePackages = {
              "de.difuture.ekut.pht.config",
              "de.difuture.ekut.pht.train.office"})
@EnableBinding(Sink.class)
@EnableNeo4jRepositories
@Import(Neo4jDenbiConfiguration.class)
public class TrainOfficeApplication {

	public static void main(String[] args) {
		SpringApplication.run(TrainOfficeApplication.class, args);
	}
}
