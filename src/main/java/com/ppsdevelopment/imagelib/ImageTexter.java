package com.ppsdevelopment.imagelib;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class ImageTexter {
    private  final String fileName;
    private final String caption;
    private Graphics graphics;
    private Font font;
    private int maxCaptionLength;
    private  final double ratioWidth;
    private  final  double ratioHeight;
    private final int dx;
    private final int dy;
    private final int fontLineHeightRatio;
    private String destinationPath;
    private String sourcePath;

    public ImageTexter(String fileName, String caption, double ratioWidth, double ratioHeight, int dx, int dy, int fontLineHeightRatio, String destinationPath, String sourcePath) {
        this.fileName = fileName;
        this.caption = caption;
        font=new Font("Times New Roman", Font.PLAIN, 150);
        this.ratioWidth=ratioWidth;
        this.ratioHeight=ratioHeight;
        this.dx=dx;
        this.dy=dy;
        this.fontLineHeightRatio = fontLineHeightRatio;
        this.destinationPath=destinationPath;
        this.sourcePath=sourcePath;
    }

    private BufferedImage loadImage() throws IOException {
        final BufferedImage image;
        image = ImageIO.read(new File(sourcePath+fileName));
        return image;
    }

    public void draw(){
        try {
            final BufferedImage image=loadImage();
            graphics = image.getGraphics();
            graphics.setFont(font);

            // Определяем размеры экрана
            int screenWidth=image.getWidth();
            int screenHeight=image.getHeight();

            // Определяем размеры прямоугольной области вывода надписи, в зависимости от ratioWidth и ratioHeight
            maxCaptionLength= (int) Math.round(ratioWidth*screenWidth);
            int maxCaptionHeight=(int)Math.round(ratioHeight*screenHeight);

            //Определяем начальную высоту шрифта
            int maxFontSize=screenHeight/fontLineHeightRatio;
            detectFontSize(maxFontSize);

            //Определяем высоту области вывода
            int height;
            String[] captionLines;
            do {
                captionLines = this.wrapCaption(caption);
                height = getCaptionHeight(captionLines);
                if (height > maxCaptionHeight) decreaseFontForBlock(caption, maxCaptionHeight);
            } while(height>maxCaptionHeight);

            textOut(captionLines,dx,screenHeight-dy-height);
            graphics.dispose();

            ImageIO.write(image, "jpg", new File(destinationPath+fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void decreaseFontForBlock(String caption, int maxCaptionHeight) {
        int fontSize=font.getSize();
        String[] captionLines;
        int height;
        do{
            fontSize = fontSize - 1;
            font = new Font("Times New Roman", Font.PLAIN, fontSize);
            graphics.setFont(font);
            captionLines=this.wrapCaption(caption);
            height=getCaptionHeight(captionLines);
        }while(height>maxCaptionHeight);
    }

    private void increaseFont(int maxFontSize){
        int fontSize=font.getSize();
        int fontSizeOld=font.getSize();
        int h=getLineHeight(caption);
        while(h<=maxFontSize) {
            fontSizeOld=fontSize;
            fontSize = fontSize + 1;
            font = new Font("Times New Roman", Font.PLAIN, fontSize);
            graphics.setFont(font);
            h = getLineHeight(caption);
        }
        font = new Font("Times New Roman", Font.PLAIN, fontSizeOld);
        graphics.setFont(font);
    }

    private void decreaseFont(int maxFontSize){
        int fontSize=font.getSize();
        int h=getLineHeight(caption);

        while(h>maxFontSize) {
            fontSize = fontSize - 1;
            font = new Font("Times New Roman", Font.PLAIN, fontSize);
            graphics.setFont(font);
            h = getLineHeight(caption);
        }
    }

    private void detectFontSize(int maxFontSize) {
        int h=getLineHeight(caption);

        if (h>maxFontSize) decreaseFont(maxFontSize);
        else
            increaseFont(maxFontSize);
    }

    private void textOut(String[] caption,int dx, int dy) {
        int dh=dy;
        for(int i=0;i<caption.length;i++) {
            graphics.drawString(caption[i], dx, dh);
            dh+=getLineHeight(caption[i]);
        }
    }

    private String[] wrapCaption(String caption){
        ArrayList<String> list=new ArrayList<>();
        String[] words=caption.split(" ");
        FontMetrics fm = graphics.getFontMetrics();
        String s="";
        for(int i=0;i<words.length;i++){
            String s1=s+" "+words[i];
            int width = fm.stringWidth(s1);
            if (width>maxCaptionLength){
                list.add(s);
                s=words[i];
            }
            else
                s=s+" "+words[i];
        }
        list.add(s);
        return list.toArray(new String[list.size()]);
    }


    private int getCaptionHeight(String[] caption){
        int h=0;
        for(int i=0;i<caption.length;i++){
            h+=getLineHeight(caption[i]);
        }
        return h;
    }

    private int getLineHeight(String caption){
        Graphics2D g2=(Graphics2D) graphics;
        FontRenderContext fr=g2.getFontRenderContext();
        double w=font.getStringBounds(caption,fr).getHeight();
        return (int)w;
    }

}
