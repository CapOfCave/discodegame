package me.kecker.discodegame.bot.utils;

import java.util.stream.Collector;
import java.util.stream.Collectors;

public final class DcgStreamUtils {

    public static <T> Collector<T, ?, T> toSingleton() {
        return Collectors.collectingAndThen(
                Collectors.toList(),
                list -> {
                    if (list.size() != 1) {
                        throw new IllegalStateException();
                    }
                    return list.get(0);
                }
        );
    }


    private DcgStreamUtils() {
        // this class should not be instantiated
        throw new IllegalStateException();
    }


}
