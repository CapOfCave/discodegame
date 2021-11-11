package me.kecker.discodegame.core.domain.exceptions;

public class UnsupportedLanguageException extends Exception {

    public UnsupportedLanguageException(String language){
        super("Unsupported Language: " + language);
    }

}
