package org.javaee.imageLoader.model.util;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

import static org.javaee.imageLoader.model.util.NamesHandler.*;

public class ImageResizer {
	
    private static int SMALL = 100;
    
    private static int MEDIUM = 300;
 
    public static void resizeImage(String filePath) throws Exception{
        BufferedImage toResizeImage = loadImage(filePath);
        
        BufferedImage resizedImage = resize(toResizeImage, SMALL);
        
        String partialFilePath = removeFileNameFormat(filePath);
        String fileFormat = getImageFormat(filePath);
        String finalImageName = partialFilePath + SMALL + "." + fileFormat;
        
        saveImage(resizedImage, finalImageName, fileFormat);
        
        resizedImage = resize(toResizeImage, MEDIUM);
        
        partialFilePath = removeFileNameFormat(filePath);
        fileFormat = getImageFormat(filePath);
        finalImageName = partialFilePath + MEDIUM + "." + fileFormat;
        
        saveImage(resizedImage, finalImageName, fileFormat);
    }
     
    public static BufferedImage loadImage(String pathName) throws Exception{
        BufferedImage sourceImage = ImageIO.read(new File(pathName));

        return sourceImage;
    }
 
    public static void saveImage(BufferedImage destImage, String pathName, String format) throws IOException{
        File file =new File(pathName);

        ImageIO.write(destImage, format, file);   
    }
     
    public static BufferedImage resize(BufferedImage sourceImage, int size) {

        BufferedImage destImage = new BufferedImage(size, size, sourceImage.getType());
        
        Graphics2D g = destImage.createGraphics();
        g.drawImage(sourceImage, 0, 0, size, size, null);
        g.dispose();
        
        return destImage;
    }
}