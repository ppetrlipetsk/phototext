package com.ppsdevelopment;

import java.io.File;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class FilesPathReader implements IFilesPathReader {
    private final String path;
    private String[] filesCollection;

    public FilesPathReader(String path) {
        this.path = path;
    }

    @Override
    public void fillFilesCollection(){
        final Set<String> items=new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        final File dir = new File(path);
        if(dir.isDirectory())
            for(File item : dir.listFiles()) {
                items.add(item.getName());
            }

        this.filesCollection= items.toArray(new String[items.size()]);
    }

    @Override
    public String[] getFilesCollection() {
        return filesCollection;
    }
}
