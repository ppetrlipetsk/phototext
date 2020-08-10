package com.ppsdevelopment.imagelib;

import com.ppsdevelopment.IFilesPathReader;
import com.ppsdevelopment.IImportProcessor;
import com.ppsdevelopment.TableCollection;

import java.util.Map;

public class ImageEngine implements IImageEngine{
    private String[] files;
    private Map<Integer, TableCollection> items;
    private   double withRatio;
    private   double heightRatio;
    private   int dx;
    private   int dy;
    private   double fontLineHeightRatio;
//    private String sourcePath;
    private String destinationPath;
 //   private String xlsxPath;
    private IFilesPathReader filesPathReader;
    private IImportProcessor importProcessor;

    /*public void setXlsxPath(String xlsxPath) {
        this.xlsxPath = xlsxPath;
    }
*/
    public void setImportProcessor(IImportProcessor importProcessor) {
        this.importProcessor = importProcessor;
    }

    public void setFilesPathReader(IFilesPathReader filesPathReader) {
        this.filesPathReader = filesPathReader;
    }

    //public void setSourcePath(String sourcePath) {
//        this.sourcePath = sourcePath;
//    }


    public IFilesPathReader getFilesPathReader() {
        return filesPathReader;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }

    public void setWithRatio(double withRatio) {
        this.withRatio = withRatio;
    }

    public void setHeightRatio(double heightRatio) {
        this.heightRatio = heightRatio;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }

    public void setFontLineHeightRatio(double fontLineHeightRatio) {
        this.fontLineHeightRatio = fontLineHeightRatio;
    }

    public ImageEngine() {
    }

//    @Override
//    public String getSourcePath() {
//        return sourcePath;
//    }

    @Override
    public String getDestinationPath() {
        return destinationPath;
    }

    @Override
    public void setPhotoFilesCollection(String[] files) {
        this.files=files;
    }

//    @Override
//    public void setInfoTable(Map<Integer, TableCollection> items) {
//        this.items=items;
//    }

//    @Override
//    public void setInfoTable(Map<Integer, TableCollection> items) {
//        this.items=items;
//    }

    public void process() throws Exception {
        filesPathReader.fillFilesCollection();
        this.setPhotoFilesCollection(filesPathReader.getFilesCollection());
        //importProcessor.setXLSXFilePath(this.xlsxPath);
        importProcessor.loadTable();
        items=importProcessor.getItems();

        indexItems();
        for (Map.Entry entry: items.entrySet()) {
            int key= (int) entry.getKey();
            TableCollection tableCollection= (TableCollection) entry.getValue();
            processRow(key,tableCollection);
        }
    }

    private void processRow(int key, TableCollection tableCollection) {
        String caption=tableCollection.getCaption();
        for(int i=0;i<tableCollection.getCount();i++){
            int fileIndex=key+i;
            String fileName=files[fileIndex];
            if (!fileName.equals("Thumbs.db"))
            (new ImageTexter(
                    fileName,
                    caption,
                    withRatio,
                    heightRatio,
                    dx,
                    dy,
                    fontLineHeightRatio,
                    this.destinationPath,this.filesPathReader.getPath())
            ).draw();
            System.out.println("Обработана запись id="+key+" файл № "+(i+1)+" имя файла "+fileName);
        }
    }

    private void indexItems() throws Exception {
        TableCollection tcPred = null;
        TableCollection tableCollection;
        for (Map.Entry entry : items.entrySet()) {
            tableCollection = (TableCollection) entry.getValue();
            if (tcPred == null) {
                tcPred = tableCollection;
            } else {
                tcPred.setCount(tableCollection.getIndex() - tcPred.getIndex());
                tcPred = tableCollection;
            }

            if (tableCollection != null) {
                tableCollection.setCount(this.files.length - tableCollection.getIndex());
            } else
                throw new Exception("Ошибка данных в таблице XLSX");
        }
    }

}
