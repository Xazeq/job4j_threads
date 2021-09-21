package ru.job4j.io;

import java.io.File;
import java.util.function.Predicate;

public class ParseContentWithoutUnicode implements Parse {
    private final File file;

    public ParseContentWithoutUnicode(File file) {
        this.file = file;
    }

    @Override
    public String getContent(Predicate<Character> filter) {
        return new ParseContent().parse(filter, file);
    }
}
