package me.kecker.discodegame.utils;

import java.util.function.Function;

public class DcgStringUtils {
    public static final Function<String,String> addQuotes = s -> "\"" + s + "\"";
}
