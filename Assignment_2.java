
package Uppgift_2;
import java.awt.Color;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

/**
 * Uppgift2
 * MMMMMMMMMMMMMMM
 * @author emmashakespeare
 *
 */
public class Assignment_2 {

   private BufferedImage flip;
   private BufferedImage img;
   private int[][] matrixSobelX;
   private int[][] matrixSobelY;
   private int  width;
   private int height;
   private boolean isOriginUpperLeft = true;


   public BufferedImage getFlippedImage(){
      return this.flip;
   }

   public Assignment_2(BufferedImage img){
	  createXYMatrix();
	  this.img = img;
      width  = img.getWidth();
      height = img.getHeight();
      this.flip = new BufferedImage(width, height, /*BufferedImage.TYPE_INT_RGB);*/ BufferedImage.TYPE_BYTE_GRAY);
      
      //konturdetektering
      for (int y=1; y<width-1; y++){
          for (int x=1; x<height-1; x++) {
        	  
        	  //Create a 3X3 kernel of pixels surrounding pixel
        	  int[][] gray = new int[3][3];
        	  for(int i = 0; i < 3;i++){
        		  for(int j=0; j<3;j++){
//        			  int c = (int) lum(get(y-1+i, x-1+j));
//        			  int value = get(y-1+i, x-1+j).getBlue();
//        			  int value = get(y-1+i, x-1+j).getRed();
//        			  int value = get(y-1+i, x-1+j).getGreen();


        			  Color c = get(y-1+i, x-1+j);
//        			  System.out.println(c);

        			  int value = (int) lum(c);


//        			  System.out.println(value);

        			  gray[i][j] = value;
        		  }
        	  }
        	  
        	  //apply filter, faltning
        	  int gray1 = 0, gray2 = 0;
        	  for(int i = 0; i < 3; i++){
        		  for(int j = 0; j < 3; j++){
        			  gray1 += gray[i][j] * matrixSobelX[i][j];
        			  gray2 += gray[i][j] * matrixSobelY[i][j];
        		  }
        	  }
        	  
        	  //Identifiera de konturer som är meningsfulla och sålla de som representerar brus,
        	  //Metod: tröskelvärden -tr
        	  int magnitude = truncate(Math.abs(gray1)+ Math.abs(gray2));
//        	  int magnitude = truncate((int) Math.sqrt(gray1*gray1+ gray2*gray2));
//        	  int magnitude = 255 - truncate((int) Math.sqrt((gray1*gray1) + (gray2*gray2)));

        	  Color grayscale = new Color(magnitude, magnitude, magnitude);
        	  set(y,x, grayscale);

          }
      }

      
   }
   /**
    * Makes the value to white or black to show edges
    * @param a - col. value
    * @return - truncated color value
    */
   public int truncate(int a){
	   if(a<0){
		   return 0;
	   }else if(a>255){
		   return 255;
	   }else{
		   return a;
	   }
   }
   
// return the monochrome luminance of given color
   public static double lum(Color color) {
       int r = color.getRed();
       int g = color.getGreen();
       int b = color.getBlue();
       return .299*r + .587*g + .114*b;
   }
   
   public Color get(int col, int row) {
       if (col < 0 || col >= width)  throw new IndexOutOfBoundsException("col must be between 0 and " + (width-1));
       if (row < 0 || row >= height) throw new IndexOutOfBoundsException("row must be between 0 and " + (height-1));
       return new Color(img.getRGB(col, row));
   }
   
   /**
    * Sets the color of pixel (<tt>col</tt>, <tt>row</tt>) to given color.
    *
    * @param col the column index
    * @param row the row index
    * @param color the color
    * @throws IndexOutOfBoundsException unless both 0 &le; <tt>col</tt> &lt; <tt>width</tt>
    *         and 0 &le; <tt>row</tt> &lt; <tt>height</tt>
    * @throws NullPointerException if <tt>color</tt> is <tt>null</tt>
    */
   public void set(int col, int row, Color color) {
       if (col < 0 || col >= width)  throw new IndexOutOfBoundsException("col must be between 0 and " + (width-1));
       if (row < 0 || row >= height) throw new IndexOutOfBoundsException("row must be between 0 and " + (height-1));
       if (color == null) throw new NullPointerException("can't set Color to null");
//       if (isOriginUpperLeft) flip.setRGB(col, row, color.getRGB());
//       else                   flip.setRGB(col, height - row - 1, color.getRGB());
       flip.setRGB(col, row, color.getRGB());
   }
   
   public void createXYMatrix(){
	   this.matrixSobelX = new int[][]{
	            {-1,     0,  1},
	            {-2,     0,  2},
	            {-1,     0,  1}
	    };
	    this.matrixSobelY = new int[][]{
	            {1,    2,     1},
	            {0,    0,     0},
	            {-1,   -2,   -1}
	    };
   }
   
   public static void main(String[] args) {
//       String file  = "src/Uppgift_2/tree_bw.jpg";
       String file  = "src/Uppgift_2/water_lilies.jpg";
//       String file  = "src/Uppgift_2/green_boat.jpg";


       try {
           BufferedImage img  = ImageIO.read(new File(file));
         
           Assignment_2 flipper = new Assignment_2(img);
           ImageIO.write(flipper.getFlippedImage(), "PNG", new File(file + "RESULTMath.sqrt.png"));
       } catch (IOException e) {
           System.out.println("Failed processing!\n"+e.toString());
           e.printStackTrace();
       }

   }
   
   /**
    * Sets the origin to be the upper left pixel. This is the default.
    */
   public void setOriginUpperLeft() {
       isOriginUpperLeft = true;
   }

  /**
    * Sets the origin to be the lower left pixel.
    */
   public void setOriginLowerLeft() {
       isOriginUpperLeft = false;
   }
}


