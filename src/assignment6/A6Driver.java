package assignment6;

import java.awt.image.BufferedImage;
import java.awt.image.BufferedImageOp;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import assignment5.LowPassFilter;

public class A6Driver {

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {

  String name = "wdg2.gif";
    
  System.out.println(Arrays.toString(args));
  
//  if(args.length < 1){
//    System.out.println("Usage: java -jar Filename.jpg");
//    System.out.println("Exiting ...");
//    System.exit(0);
//  }
//
//  
//  String name = args[0];

  BufferedImage image      = ImageIO.read(new File(name));
  
  BufferedImageOp segmentOp  = new SegmentationOp();
  BufferedImage segmentedImg = segmentOp.filter(image, null);   
  File file1 = new File("segmented-"+name);
  ImageIO.write(segmentedImg, "gif", file1);


  }

}
