package ru.scarletarrow.diplomv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.scarletarrow.diplomv1.entities.LocationClass;
@Repository
public interface LocationRepository extends JpaRepository<LocationClass, Long> {

}
