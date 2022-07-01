package ru.scarletarrow.diplomv1.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import ru.scarletarrow.diplomv1.entities.CustomMap;
import ru.scarletarrow.diplomv1.repository.MapRepository;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.UUID;

@Service(value = "MapServiceImpl")
public class MapServiceImpl implements MapService {

    @Autowired
    @Qualifier("MapRepository")
    MapRepository mapRepository;

    @Override
    public CustomMap saveMap(CustomMap map) {
        return mapRepository.save(map);
    }

    @Override
    public CustomMap getMap(UUID uuid) throws EntityNotFoundException {

        return mapRepository.getById(uuid);

    }

    @Override
    public List<CustomMap> getMaps() {
        return mapRepository.findAll();
    }

}
