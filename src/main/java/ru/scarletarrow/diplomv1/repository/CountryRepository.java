package ru.scarletarrow.diplomv1.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.scarletarrow.diplomv1.entities.Country;

@Repository(value = "CountryRepository")
public interface CountryRepository extends JpaRepository<Country, Long> {
}