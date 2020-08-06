package com.ppsdevelopment.imagelib;

import com.ppsdevelopment.TableCollection;

import java.util.LinkedHashMap;
import java.util.Map;

public interface IImageEngine {

    void setPhotoFilesCollection(String[] files);

    void setInfoTable(Map<Integer, TableCollection> items);
}
