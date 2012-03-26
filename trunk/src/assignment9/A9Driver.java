package assignment9;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

import assignment4.SobelOperator;

import tao.image.IPUtil;

public class A9Driver {

  /**
   * @param args
   * @throws IOException 
   */
  public static void main(String[] args) throws IOException {
//          String name = "check.gif";
    String name = "bounadry.gif";

    System.out.println(Arrays.toString(args));

    //    if(args.length < 1){
    //      System.out.println("Usage: java -jar Filename.jpg");
    //      System.out.println("Exiting ...");
    //      System.exit(0);
    //    }


    //    String name = args[0];

    String[] arr = name.split("\\.");
    String format = arr[1];


    BufferedImage image = ImageIO.read(new File(name));
    //    CannyEdgeOp cannyOp = new CannyEdgeOp(10);
    //    BufferedImage boundaryImg = cannyOp.filter(image, null);


    IPUtil.displayMatrix(IPUtil.readImageAsMatrix(image));
//    int[][] imgMatrix = SobelOperator.applySobelOperator(IPUtil.readImageAsMatrix(image));
//    IPUtil.displayMatrix(imgMatrix);
    
    System.out.println("-------");
    ChainCode cc = new ChainCode(image);
    for(String s:cc.getChainCodes()){
      System.out.println(s);
    }

    //    File file = new File("boundary-"+name);
    //    ImageIO.write(boundaryImg, format, file);

  }
}