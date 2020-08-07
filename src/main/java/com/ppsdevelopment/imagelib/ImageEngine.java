package com.ppsdevelopment.imagelib;

import com.ppsdevelopment.TableCollection;

import java.util.Map;

public class ImageEngine implements IImageEngine{
    private String[] files;
    private Map<Integer, TableCollection> items;
    private   double withRatio;
    private   double heightRatio;
    private   int dx;
    private   int dy;
    private   int fontLineHeightRatio;
    private String sourcePath;
    private String destinationPath;

    public void setSourcePath(String sourcePath) {
        this.sourcePath = sourcePath;
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

    public void setFontLineHeightRatio(int fontLineHeightRatio) {
        this.fontLineHeightRatio = fontLineHeightRatio;
    }

    public ImageEngine() {
    }
/*
    public ImageEngine(double withRatio, double heightRatio, int dx, int dy, int fontLineHeightRatio) {
        this.withRatio = withRatio;
        this.heightRatio = heightRatio;
        this.dx = dx;
        this.dy = dy;
        this.fontLineHeightRatio = fontLineHeightRatio;
    }*/

    @Override
    public void setPhotoFilesCollection(String[] files) {
        this.files=files;
    }

    @Override
    public void setInfoTable(Map<Integer, TableCollection> items) {
        this.items=items;
    }

    public void process(){
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
                    this.destinationPath,this.sourcePath)
            ).draw();
            System.out.println("Обработана запись id="+key+" файл № "+(i+1)+" имя файла "+fileName);
        }
    }

    private void indexItems() {
        TableCollection tcPred=null;
        TableCollection tableCollection=null;
        for (Map.Entry entry: items.entrySet()) {
            int key= (int) entry.getKey();
            tableCollection= (TableCollection) entry.getValue();
            if (tcPred==null){
                tcPred=tableCollection;
            }
            else
            {
                tcPred.setCount(tableCollection.getIndex()-tcPred.getIndex());
                tcPred=tableCollection;
            }
        }
        tableCollection.setCount(this.files.length-tableCollection.getIndex());
    }

}
