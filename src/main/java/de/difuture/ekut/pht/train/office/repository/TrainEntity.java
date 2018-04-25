package de.difuture.ekut.pht.train.office.repository;


import de.difuture.ekut.pht.lib.core.api.Train;
import de.difuture.ekut.pht.lib.core.messages.TrainUpdate;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Id;
import org.neo4j.ogm.annotation.NodeEntity;

import javax.validation.constraints.NotNull;
import java.net.URI;

@NodeEntity
@Data
@NoArgsConstructor
public class TrainEntity {

    @Id
    @GeneratedValue
    private Long trainID;
    private URI trainRegistryURI;


    public TrainEntity(@NotNull TrainUpdate trainUpdate) {

        this.trainID = trainUpdate.getTrainID();
        this.trainRegistryURI = trainUpdate.getTrainRegistryURI();
    }

    public Train toTrain() {

        return new Train(this.trainID, this.trainRegistryURI);
    }
}
