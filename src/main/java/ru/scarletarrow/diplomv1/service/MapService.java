package ru.scarletarrow.diplomv1.service;

import ru.scarletarrow.diplomv1.entities.CustomMap;

import java.util.List;
import java.util.UUID;

public interface MapService {
    CustomMap saveMap(CustomMap map);
    CustomMap getMap(UUID uuid);
    List<CustomMap> getMaps();
}
