package com.ppsdevelopment.imagelib;

import com.ppsdevelopment.TableCollection;

import java.util.Map;

public class ImageEngine implements IImageEngine{
    private String[] files;
    private Map<Integer, TableCollection> items;
    private  final double withRatio;
    private  final double heightRatio;
    private  final int dx;
    private  final  int dy;
    private  final int fontLineHeightRatio;

    public ImageEngine(double withRatio, double heightRatio, int dx, int dy, int fontLineHeightRatio) {
        this.withRatio = withRatio;
        this.heightRatio = heightRatio;
        this.dx = dx;
        this.dy = dy;
        this.fontLineHeightRatio = fontLineHeightRatio;
    }

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
        for(int i=0;i<tableCollection.getCount()-1;i++){
            int fileIndex=key+i;
            String fileName=files[fileIndex];
            ImageTexter texter=new ImageTexter(fileName,caption, withRatio,heightRatio,dx,dy, fontLineHeightRatio);
            texter.draw();
        }
    }

    private void indexItems() {
        int indx=-1;
        for (Map.Entry entry: items.entrySet()) {
            int key= (int) entry.getKey();
            TableCollection tableCollection= (TableCollection) entry.getValue();
            if (indx==-1){
                indx=key;
            }
            else{
                int count=key-indx;
                tableCollection.setCount(count);
            }
        }
    }

}
