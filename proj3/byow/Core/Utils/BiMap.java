package byow.Core.Utils;

import java.util.HashMap;
import java.util.Map;

/**
 * A one-to-one mapping table, where each K and V cannot be the same, can quickly achieve mutual lookup between K and V
 */
public class BiMap<K ,V> {
    private final Map<K, V> keyToValue;
    private final Map<V, K> valueToKey;

    public BiMap() {
        keyToValue = new HashMap<>();
        valueToKey = new HashMap<>();
    }

    public void put(K key, V value) {
        if (keyToValue.containsKey(key) || valueToKey.containsKey(value)) {
            throw new IllegalArgumentException("Key or value already exists, key: " + key + " value: " + value);
        }
        keyToValue.put(key, value);
        valueToKey.put(value, key);
    }

    public V getValue(K key) {
        return keyToValue.get(key);
    }

    public K getKey(V value) {
        return valueToKey.get(value);
    }

    public void removeByKey(K key) {
        V value = keyToValue.remove(key);
        if (value != null) {
            valueToKey.remove(value);
        }
    }

    public void removeByValue(V value) {
        K key = valueToKey.remove(value);
        if (key != null) {
            keyToValue.remove(key);
        }
    }
}
