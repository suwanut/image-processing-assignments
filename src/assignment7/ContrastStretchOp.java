package assignment7;

import java.awt.Color;
import java.awt.image.BufferedImage;

public class ContrastStretchOp extends AbstractBufferedImageOp{

  @Override
  public BufferedImage filter(BufferedImage src, BufferedImage dest) {

    int width  = src.getWidth();
    int height = src.getHeight();

    if(dest == null){
      dest = createCompatibleDestImage(src, null);
    }

    int min_val = Integer.MAX_VALUE;
    int max_val = Integer.MIN_VALUE;

    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        Color c = new Color(src.getRGB(x, y));
        if(c.getBlue() > max_val){
          max_val = c.getBlue();

        }
        if(c.getBlue() < min_val){
          min_val = c.getBlue();
        }
      }
    }

    for(int y = 0; y < height; y++){
      for(int x = 0; x < width; x++){
        Color c      = new Color(src.getRGB(x, y));
        int color    = c.getBlue();
        int newColor = 255*(color - min_val)/(max_val - min_val);
        Color newC   = new Color(newColor, newColor, newColor);
        dest.setRGB(x, y, newC.getRGB());
      }
    }

    return dest;
  }

}
