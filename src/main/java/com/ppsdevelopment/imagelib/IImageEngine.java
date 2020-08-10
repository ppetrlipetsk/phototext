package com.ppsdevelopment.imagelib;


import com.ppsdevelopment.IFilesPathReader;

public interface IImageEngine {

    void setPhotoFilesCollection(String[] files);

    void process() throws Exception;

    IFilesPathReader getFilesPathReader();

    String getDestinationPath();
}
