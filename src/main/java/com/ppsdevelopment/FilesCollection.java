package com.ppsdevelopment;

import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

public class FilesCollection implements IFilesCollection {
    private Set<String> items;

    public FilesCollection() {
        items =new TreeSet<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    @Override
    public void addItem(String item) {
        items.add(item);
    }

    @Override
    public String[] getItems() {
        return (String[]) items.toArray();
    }

    @Override
    public void sort() {

    }
}
