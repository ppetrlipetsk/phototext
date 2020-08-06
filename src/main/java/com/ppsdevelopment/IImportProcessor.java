package com.ppsdevelopment;

import java.util.Map;

public interface IImportProcessor {
    void loadTable();
    Map<Integer, TableCollection> getItems();
}
