package ru.scarletarrow.diplomv1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public final class CustomMap {
    @Id
    private UUID uuid;
    private String mapName;
    private CustomMapType mapType;

    @ElementCollection
    private List<Countries> countries;

    public UUID uuid() {
        return uuid;
    }

    public CustomMapType mapType() {
        return mapType;
    }




}
