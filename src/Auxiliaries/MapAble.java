package Auxiliaries;

import java.util.HashMap;
import java.util.Map;

/**
 * generates a map contains all the fields needed to the sql request
 */
public interface MapAble {
    /**
     * @param array an array of objects which should be entered into the map as it values
     * @return a map with keys starting from 1 and values
     */
    default Map<Integer, Object> getMap(Object[] array) {
        Map<Integer, Object> map = new HashMap<>();
        for (int i = 0; i < array.length; i++) map.put(i+1, array[i]);
        return map;
    }

    /**
     * @param obj an object should be entered into the map as it value
     * @return a map with a key 1 and a value
     */
    default Map<Integer, Object> getMap(Object obj) {
        Map<Integer, Object> map = new HashMap<>();
        map.put(1, obj);
        return map;
    }

    /**
     * @return an empty map
     */
    default Map<Integer, Object> getMap() {
        Map<Integer, Object> map = new HashMap<>();
        return map;
    }
}
