package de.difuture.ekut.pht.traincentral;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;

import de.difuture.ekut.pht.traincentral.message.TrainUpdateStreams;

@SpringBootApplication
@EnableBinding(TrainUpdateStreams.class)
public class TraincentralApplication {

	public static void main(String[] args) {
		SpringApplication.run(TraincentralApplication.class, args);
	}
	
	
	/*
	@Bean
    @InboundChannelAdapter(value = Source.OUTPUT)
    public MessageSource<String> timerMessageSource() {
		
        return () -> new GenericMessage<>(new SimpleDateFormat().format(new Date()));
    }
    */
}
