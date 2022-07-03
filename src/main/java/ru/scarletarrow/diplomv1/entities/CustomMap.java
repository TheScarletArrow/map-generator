package ru.scarletarrow.diplomv1.entities;

import lombok.*;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public final class CustomMap {
    @Override
    public String toString() {
        return "CustomMap{" +
                "uuid=" + uuid +
                ", mapName='" + mapName + '\'' +
                ", mapType=" + customMapTypeClass +
                ", countries=" + countries +
                '}';
    }

    @Id
    private UUID uuid;
    private String mapName;
    @OneToOne
    private CustomMapTypeClass customMapTypeClass;

    @ElementCollection
    private List<Countries> countries;
    @OneToOne
    private AppUser owner;






}
