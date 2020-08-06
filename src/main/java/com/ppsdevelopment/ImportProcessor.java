package com.ppsdevelopment;

import org.apache.poi.openxml4j.exceptions.OpenXML4JException;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.*;

public class ImportProcessor implements IImportProcessor {
    private static final int FIELDSCOUNT = 3;
    private final Map<Integer, TableCollection> items;
    private final String fileName;

    public ImportProcessor(String fileName) {
        items=new TreeMap<>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                if (o1>o2) return 1;
                else
                    if (o1<o2)
                    return -1;
                    else
                        return 0;
            }
        });
        this.fileName=fileName;
    }

    @Override
    public void loadTable() {
        DataImportCallBack fcb = new DataImportCallBack();
        ExcelReader ereader = new ExcelReader(fileName, fcb, this.FIELDSCOUNT);
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
            int indx=Integer.valueOf(list.get(0));
            String caption= list.get(2);
            String loc=list.get(1);
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

