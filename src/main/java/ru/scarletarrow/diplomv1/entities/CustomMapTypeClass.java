package ru.scarletarrow.diplomv1.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomMapTypeClass {
    @Id
    @GeneratedValue
    private Long id;

    private String customMapType;
}
