package me.kecker.discodegame.domain.exceptions;

public class UnsupportedLanguageException extends Exception {

    public UnsupportedLanguageException(String language){
        super("Unsupported Language: " + language);
    }

}
