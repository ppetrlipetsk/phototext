package com.ppsdevelopment;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;

public class ImportProcessor implements IImportProcessor {
    private static final int FIELDSCOUNT = 3;
    private final Map<Integer, TableCollection> items;
    private String filePath;

    public ImportProcessor() {
        items=new TreeMap<>(Integer::compareTo);
    }

//    public ImportProcessor(String fileName) {
//        this();
//        this.filePath =fileName;
//    }


    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

//    public void setXLSXFilePath(String fileName) {
//        this.filePath = fileName;
//    }

    @Override
    public void loadTable() {
        DataImportCallBack fcb = new DataImportCallBack();
        ExcelReader ereader = new ExcelReader(filePath, fcb, FIELDSCOUNT);
        try {
            ereader.read();
        } catch (IOException | SAXException | OpenXML4JException e) {
            e.printStackTrace();
        }
        ereader.close();
    }

    @Override
    public Map<Integer, TableCollection> getItems() {
        return items;
    }

    private void lineImporter(LinkedList<String> list, long currentRow) {
        if (currentRow>0){
            int indx=Integer.parseInt(list.get(0));
            String caption= list.get(1);
            String loc=null;//list.get(2);
            TableCollection tc=new TableCollection(caption,indx,loc);
            items.put(indx,tc);
        }
    }


    private class DataImportCallBack implements IParserCallBack {
        private long currentRow;

        @Override
        public void call(LinkedList<String> list) {
            ++currentRow;

            if (currentRow>0)
            System.out.println("Import row №"+currentRow);
            lineImporter(list,currentRow);
        }

    private DataImportCallBack() {
        this.currentRow=-1;
    }
}

}

