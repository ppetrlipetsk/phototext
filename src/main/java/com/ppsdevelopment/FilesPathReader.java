package com.ppsdevelopment;

import java.io.File;
import java.util.Objects;
import java.util.Set;
import java.util.TreeSet;

public class FilesPathReader implements IFilesPathReader {
    private final String path;
    private String[] filesCollection;
    private String extension;

    public FilesPathReader(String path, String extension) {

        this.path = path;
        this.extension=extension;
    }

    @Override
    public void fillFilesCollection(){
        final Set<String> items=new TreeSet<>(String::compareTo);

        final File dir = new File(path);

        if(dir.isDirectory())
            for(File item : Objects.requireNonNull(dir.listFiles((dir1, name) -> name.endsWith("."+extension)))) {
                items.add(item.getName());
            }

        this.filesCollection= items.toArray(new String[0]);
    }

    @Override
    public String getPath() {
        return this.path;
    }

    @Override
    public String[] getFilesCollection() {
        return filesCollection;
    }
}
