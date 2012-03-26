package assignment9;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;

import tao.image.IPUtil;

public class ChainCode {

  BufferedImage image;
  int width;
  int height;

//  private int direction = 1;
  private int direction = 7;
  private boolean flag  = false;
  
  private HashMap<Integer, Integer> map = new HashMap<Integer, Integer>()
      {{
        put(0,3);
        put(7,2);
        put(6,1);
        put(5,0);
        put(4,7);
        put(3,6);
        put(2,5);
        put(1,4);
      }};


  public ChainCode(BufferedImage image){
    this.image  = image;
    this.width  = image.getWidth();
    this.height = image.getHeight();
  }

  public ArrayList<String> getChainCodes(){
    ArrayList<String> list = new ArrayList<String>();

    int x,y;
    x = y = 0;

    outer:
    for(y =0;y < height; y++){
      for(x=0 ;x < width; x++){
        Color c = new Color(image.getRGB(x, y));
        if(c.getBlue() > 0){
          list.add(f(x,y));
          break outer;
        }
      }
    }
    return list;
  }



  private String f(int x, int y) {
    String code = "";

    Dimension firstCell  = new Dimension(x,y);
    Dimension d1         = firstCell;
    Dimension secondCell = getNextCell8(x, y);
    Dimension  d2        = secondCell;
    
    code = code + direction;

    do{
      System.out.print(" ("+x+","+y +"),");
      x = (int) d2.getWidth();
      y = (int) d2.getHeight();
            
      d1 = d2;
      d2 = getNextCell8(x, y);
      code = code + direction;
      
    }while(!d1.equals(firstCell) && !d2.equals(secondCell) && d2!=null);
    
    return code.substring(0,code.length()-1);
  }

  /**
   * 321
   * 4_0
   * 567 
   */
  private Dimension getNextCell8(int x, int y){
//    int startDirection = ((direction & 1) == 0)?((direction+7) % 8):((direction+6) % 8);
    int startDirection = getStartDirection(direction);
    int i = 0;
    Dimension d = null;
    while(i<7){
      System.out.print(","+startDirection);
      d = getCellInDirection(x, y, startDirection);      
      if(d != null){
        System.out.println("Changing dir from "+direction+" to "+startDirection);
        direction = startDirection;
        break;
      }
      startDirection = mod ((startDirection -1), 8); 
      i++;
    }
    System.out.println();
    return d;
  }
  
  private int getStartDirection(int startDirection){
    return map.get(startDirection);
  }
  

  private Dimension getCellInDirection(int x, int y, int dir){
    switch(dir){
      case 0:
        return (inBounds(x+1,y) && isObjectPixel(x+1,y))?new Dimension(x+1, y):null;
      case 1:
        return(inBounds(x+1,y-1) && isObjectPixel(x+1,y-1))?new Dimension(x+1, y-1):null;
      case 2:
        return(inBounds(x,y-1) && isObjectPixel(x,y-1))?new Dimension(x, y-1):null;
      case 3:
        return(inBounds(x-1,y-1) && isObjectPixel(x-1,y-1))?new Dimension(x-1, y-1):null;
      case 4:
        return(inBounds(x-1,y) && isObjectPixel(x-1,y))?new Dimension(x-1, y):null;
      case 5:
        return(inBounds(x-1,y+1) && isObjectPixel(x-1,y+1))?new Dimension(x-1, y+1):null;
      case 6:
        return(inBounds(x,y+1) && isObjectPixel(x,y+1))?new Dimension(x, y+1):null;
      case 7:
        return(inBounds(x+1,y+1) && isObjectPixel(x+1,y+1))?new Dimension(x+1, y+1):null;
      default:
        return null;
    }
  }
  
  
  
  private boolean inBounds(int x, int y){
    return ((x>=0 && x<width) && (y>=0 && y<height));
  }  

  private boolean isObjectPixel(int x, int y){
    return (new Color(image.getRGB(x, y)).getBlue()>0);
  }

  
  
  
  
  
  public static void main(String[] args) throws IOException {

    String name = "bounadry.gif";

    System.out.println(Arrays.toString(args));

    String[] arr = name.split("\\.");
    String format = arr[1];


    BufferedImage src = ImageIO.read(new File(name));
    ColorModel dstCM = src.getColorModel();

    BufferedImage image = new BufferedImage(dstCM, dstCM.createCompatibleWritableRaster(4, 4),
        dstCM.isAlphaPremultiplied(), null);

    for(int y = 0; y < 4; y++){
      for(int x = 0; x < 4; x++){
        int rgb = new Color(0,0,0).getRGB();
        image.setRGB(x, y, rgb);
      }
    }

    /*
     *   0, 200, 200, 0
     * 200,   0, 200, 0
     *   0, 200, 200, 0
     *   0,   0,   0, 0
     * 
     * Chain code for it is:
     */

    int rgb = new Color(255,255,255).getRGB();
    image.setRGB(1, 0, rgb);
    image.setRGB(2, 0, rgb);
    image.setRGB(2, 1, rgb);
    image.setRGB(2, 2, rgb);
    image.setRGB(1, 2, rgb);
    image.setRGB(0, 1, rgb);

    IPUtil.displayMatrix(IPUtil.readImageAsMatrix(image));
    System.out.println("-------");

    ChainCode cc = new ChainCode(image);
    for(String s:cc.getChainCodes()){
      System.out.println(s);
    }
    
//    int n = -11;
//    System.out.println(cc.mod(n,4));
  }
  
  private int mod(int a, int b){
    assert(b>0);    
    int k = (int) Math.ceil((float)Math.abs(a)/b);
    a += (a<0)?k*b:0;
    return a % b;
  }
}
