package com.mrqinzh.core.file;

public enum FileSource {

    DEFAULT("default"),
    ARTICLE("article"),
    COVER("cover")
    ;

    private String source;

    FileSource(String source) {
        this.source = source;
    }

    public String getSource() {
        return source;
    }

}
