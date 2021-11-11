package me.kecker.discodegame.bot.utils;

import lombok.NonNull;

import java.util.*;

public class DcgMapUtils {

    private static final double BUFFER = 4. / 3.;

    public static <K, V> Map<K, V> union(Map<K, V> map1, Map<K, V> map2) throws DuplicateKeyException {
        Map<K, V> result = new HashMap<>((int) ((map1.size() + map2.size()) * BUFFER));
        Set<K> duplicates = new HashSet<>();
        for (var entry : map1.entrySet()) {
            if (map2.containsKey(entry.getKey())) {
                duplicates.add(entry.getKey());
            }
            result.put(entry.getKey(), entry.getValue());
        }
        if (!duplicates.isEmpty()) {
            throw new DuplicateKeyException(duplicates);
        }
        result.putAll(map2);
        return result;
    }

    public static class DuplicateKeyException extends Exception {

        @NonNull
        private final Collection<?> duplicateKeys;

        public DuplicateKeyException(@NonNull Collection<?> duplicateKeys) {
            this.duplicateKeys = duplicateKeys;
        }

        @NonNull
        public Collection<?> getDuplicateKeys() {
            return duplicateKeys;
        }
    }
}
