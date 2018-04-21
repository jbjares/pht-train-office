package de.difuture.ekut.pht.train.office.repository;


import de.difuture.ekut.pht.lib.core.model.Train;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.net.URI;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TrainEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID trainID;
    private URI trainRegistryURI;

    public TrainEntity(final Train train) {

        this.trainRegistryURI = train.getTrainRegistryURI();
    }

    public Train toTrain() {

        return new Train(this.trainID, this.trainRegistryURI);
    }
}
