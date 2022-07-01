package ru.scarletarrow.diplomv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import ru.scarletarrow.diplomv1.entities.CustomMap;

import java.util.UUID;

@Repository(value = "MapRepository")
public interface MapRepository extends JpaRepository<CustomMap, UUID> {


}
