import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class MapSortingCustom {
    public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<java.util.Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(java.util.Map.Entry.comparingByValue());
Collections.reverse(list);        
        Map<K, V> result = new LinkedHashMap();
        for (java.util.Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}