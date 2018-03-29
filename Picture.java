
import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.awt.image.BufferedImage;
import java.text.*;
import java.util.*;
import java.util.List; // resolves problem with java.awt.List and java.util.List

/**
 * A class that represents a picture.  This class inherits from
 * SimplePicture and allows the student to add functionality to
 * the Picture class.
 *
 * @author Barbara Ericson ericson@cc.gatech.edu -- original file
 * @author Mayank Singamreddy
 */
public class Picture extends SimplePicture
{
  ///////////////////// constructors //////////////////////////////////

  /**
   * Constructor that takes no arguments
   */
  public Picture ()
  {
    /* not needed but use it to show students the implicit call to super()
     * child constructors always call a parent constructor
     */
    super();
  }

  /**
   * Constructor that takes a file name and creates the picture
   * @param fileName the name of the file to create the picture from
   */
  public Picture(String fileName)
  {
    // let the parent class handle this fileName
    super(fileName);
  }

  /**
   * Constructor that takes the width and height
   * @param height the height of the desired picture
   * @param width the width of the desired picture
   */
  public Picture(int height, int width)
  {
    // let the parent class handle this width and height
    super(width,height);
  }

  /**
   * Constructor that takes a picture and creates a
   * copy of that picture
   * @param copyPicture the picture to copy
   */
  public Picture(Picture copyPicture)
  {
    // let the parent class do the copy
    super(copyPicture);
  }

  /**
   * Constructor that takes a buffered image
   * @param image the buffered image to use
   */
  public Picture(BufferedImage image)
  {
    super(image);
  }

  ////////////////////// methods ///////////////////////////////////////

  /**
   * Method to return a string with information about this picture.
   * @return a string with information about the picture such as fileName,
   * height and width.
   */
  public String toString()
  {
    String output = "Picture, filename " + getFileName() +
      " height " + getHeight()
      + " width " + getWidth();
    return output;
  }

  /*
     * Swaps pixels on the left with pixels on the right
   *  @return  The picture with swapped left and right sides
   */
  public Picture swapLeftRight()
  {
    Picture toSwap = new Picture(this);
    Pixel[][] thePixels = this.getPixels2D();
    Pixel[][] swapPixels = toSwap.getPixels2D();

    for(int r = 0; r < thePixels.length; r++)
      for(int c = 0; c < thePixels[0].length; c++)
      {
        int newColumn = (c + thePixels[0].length/2)%thePixels[0].length;
        swapPixels[r][newColumn].setColor(thePixels[r][c].getColor());
      }
    return toSwap;
  }

    /*
     * Stairstep creates multiple levels within the photo based on
     * user input, as well how far to shift over with each descending
     * level, creating steps, and jumping over to the other side once
     * the pixels have passed off the edge
   *  @param shiftCount  The number of pixels to shift to the right
   *  @param steps  The number of steps
   *  @return  The picture with pixels shifted in stair steps
   */
  public Picture stairStep(int shiftCount, int steps){
		Picture toStair = new Picture(this);
		Pixel[][] pixels = this.getPixels2D();
		Pixel[][] stairedPixels = toStair.getPixels2D();

		int shift = 0;
		int stepSize = pixels.length/steps;
		for(int row = 0; row < pixels.length; row++){
		  for(int col = 0; col < pixels[0].length; col++){
			int newColumn = (col+shift)%pixels[0].length;
			stairedPixels[row][newColumn].setColor
									(pixels[row][col].getColor());
		  }
		  if(row % stepSize == 0){
			   shift += shiftCount;
		  }
		}
		return toStair;
  }

  /* Edits an image to create a Gaussian curve through the middle of the
   * image, accepting the max height of the curve as a parameter, to
   * define the strength of the curve upon the image.
 * @param maxFactor Max height (shift) of curve in pixels
 * @return Liquified picture
 */
 public Picture liquify(int maxHeight)
 {
    Picture liquified = new Picture(this);
    Pixel[][] pixels = this.getPixels2D();
    Pixel[][] liquifiedPixels = liquified.getPixels2D();
    int bellWidth = 50;

    for(int row = 0; row < pixels.length; row++)
      for(int col = 0; col < pixels[0].length; col++)
      {
         double exponent = Math.pow(row - pixels.length / 2.0, 2) /
										(2.0 * Math.pow(bellWidth, 2));
         int shift = (int)(maxHeight * Math.exp(- exponent));
         int newCol = (col+shift)%pixels[0].length;
         liquifiedPixels[row][newCol].setColor
										(pixels[row][col].getColor());
      }
      return liquified;
 }

 /* Method to create an oscillating distortion over the image, repeated
  * multiple times through the photo, accepting the amount of shift
  * per oscillation as the parameter.
 * @param amplitude The maximum shift of pixels
 * @return Wavy picture
 */
 public Picture wavy(int amplitude) {
		Pixel[][] pixels = this.getPixels2D();
		Picture newPic = new Picture(pixels.length, pixels[0].length);
		Pixel[][] resultPixels = newPic.getPixels2D();
		int height = pixels.length;
		int width = pixels[0].length;
		int shiftOver;

		for(int rows = 0; rows < pixels.length;rows++) {
			for(int cols = 0; cols < pixels[0].length; cols++) {

				shiftOver = (int)(amplitude * Math.sin
								(2*Math.PI*(1.0/100)*rows));

				Color oldColor = pixels[rows][cols].getColor();
				if (cols + shiftOver < 0)
					resultPixels[rows][width +
								(cols + shiftOver)].setColor(oldColor);
				else
					resultPixels[rows][(cols +
								shiftOver) % width].setColor(oldColor);
			}
		}
		return newPic;
	 }

   /**
    *  Method to lower the resolution of the picture to a smaller
    *  amount of pixels by taking the average of a group
    *  @param size    size of each new pixel
    */
  public void pixelate(int size){
    Pixel[][] pixels = this.getPixels2D();
    for(int row = 0; row < pixels.length; row+= size)
      for(int col = 0; col < pixels[row].length; col+= size){
          int averageRed = 0;int averageGreen = 0;int averageBlue = 0;
          int averageAlpha = 0;int pixelCount = 0;

        for(int tempRow = row; (tempRow < row + size) &&
								(tempRow < pixels.length); tempRow++)
            for(int tempCol = col; (tempCol < col + size) &&
							(tempCol < pixels[row].length); tempCol++){
                averageRed += pixels[tempRow][tempCol].getRed();
                averageGreen += pixels[tempRow][tempCol].getGreen();
                averageBlue += pixels[tempRow][tempCol].getBlue();
                averageAlpha += pixels[tempRow][tempCol].getAlpha();
                pixelCount++;
            }
        for(int tempRow = row; (tempRow < row + size) &&
							(tempRow < pixels.length); tempRow++)
          for(int tempCol = col; (tempCol < col + size) &&
							(tempCol < pixels[row].length); tempCol++){
           pixels[tempRow][tempCol].setRed(averageRed / pixelCount);
           pixels[tempRow][tempCol].setGreen(averageGreen / pixelCount);
           pixels[tempRow][tempCol].setBlue(averageBlue / pixelCount);
           pixels[tempRow][tempCol].setAlpha(averageAlpha / pixelCount);
          }
      }
  }

  /**
   *  Method to blur the picture and reduce the quality
   *  @param  size  size of blur grid to use
   *  @return blurred picture
   */
  public Picture blur(int size)
  {
    Picture blurred = new Picture(this);
    Pixel[][] pixels = this.getPixels2D();
    Pixel[][] blurredPixels = blurred.getPixels2D();

    for(int row = 0; row < pixels.length; row++)
      for(int col = 0; col < pixels[row].length; col++)
      {
        int averageRed = 0;int averageGreen = 0;int averageBlue = 0;
        int averageAlpha = 0;int pixelCount = 0;

        for(int tempRow = row - size/2; (tempRow <= row + size/2) &&
								(tempRow < pixels.length); tempRow++)
            for(int tempCol = col - size/2; (tempCol <= col + size/2) &&
							(tempCol < pixels[row].length); tempCol++)
            {
              if(tempRow < 0)
					tempRow = 0;
              if(tempCol < 0)
					tempCol = 0;

              averageRed += pixels[tempRow][tempCol].getRed();
              averageGreen += pixels[tempRow][tempCol].getGreen();
              averageBlue += pixels[tempRow][tempCol].getBlue();
              averageAlpha += pixels[tempRow][tempCol].getAlpha();
              pixelCount++;
            }
          blurredPixels[row][col].setRed(averageRed / pixelCount);
          blurredPixels[row][col].setGreen(averageGreen / pixelCount);
          blurredPixels[row][col].setBlue(averageBlue / pixelCount);
          blurredPixels[row][col].setAlpha(averageAlpha / pixelCount);
      }
      return blurred;
  }

  /**
   *  Method to enhance the quality of the picture
   *  @param  size  size of enhance grid to use
   *  @return enhanced picture
   */
  public Picture enhance(int size)
  {
    Picture enhanced = new Picture(this);
    Pixel[][] pixels = this.getPixels2D();
    Pixel[][] enhancedPixels = enhanced.getPixels2D();

    for(int row = 0; row < pixels.length; row++)
      for(int col = 0; col < pixels[row].length; col++)
      {
        int averageRed = 0;int averageGreen = 0;int averageBlue = 0;
        int averageAlpha = 0;int pixelCount = 0;

        for(int tempRow = row - size/2; (tempRow <= row + size/2) &&
						(tempRow < pixels.length); tempRow++)
            for(int tempCol = col - size/2; (tempCol <= col + size/2) &&
							(tempCol < pixels[row].length); tempCol++)
            {
              if(tempRow < 0) tempRow = 0;
              if(tempCol < 0) tempCol = 0;
              averageRed += pixels[tempRow][tempCol].getRed();
              averageGreen += pixels[tempRow][tempCol].getGreen();
              averageBlue += pixels[tempRow][tempCol].getBlue();
              averageAlpha += pixels[tempRow][tempCol].getAlpha();
              pixelCount++;
            }
          enhancedPixels[row][col].setRed((2 * pixels[row]
						   [col].getRed()) - (averageRed / pixelCount));
          enhancedPixels[row][col].setGreen((2 *
			pixels[row][col].getGreen()) - (averageGreen / pixelCount));
          enhancedPixels[row][col].setBlue((2 * pixels[row]
						 [col].getBlue()) - (averageBlue / pixelCount));
          enhancedPixels[row][col].setAlpha((2 * pixels[row]
					   [col].getAlpha()) - (averageAlpha / pixelCount));
      }
      return enhanced;
  }

  /** Method to set the blue to 0 */
  public void zeroBlue(){
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels){
      for (Pixel pixelObj : rowArray){
        pixelObj.setBlue(0);
      }
    }
  }

  /** Method to set the red and green to 0 */
  public void keepOnlyBlue(){
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels){
      for (Pixel pixelObj : rowArray){
        pixelObj.setRed(0);
        pixelObj.setGreen(0);
      }
    }
  }

  /** Method to negate the current pixel values */
  public void negate(){
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels){
      for (Pixel pixelObj : rowArray){
        pixelObj.setRed(255 - pixelObj.getRed());
        pixelObj.setGreen(255 - pixelObj.getGreen());
        pixelObj.setBlue(255 - pixelObj.getBlue());
      }
    }
  }

  /** Method to set the current picture to shades of gray */
  public void grayScale(){
    Pixel[][] pixels = this.getPixels2D();
    for (Pixel[] rowArray : pixels){
      for (Pixel pixelObj : rowArray){
        int avg = (pixelObj.getRed() + pixelObj.getGreen() +
						pixelObj.getBlue()) / 3;
        pixelObj.setRed(avg);
        pixelObj.setGreen(avg);
        pixelObj.setBlue(avg);
      }
    }
  }

  /** Method that mirrors the picture around a
    * vertical mirror in the center of the picture
    * from left to right */
  public void mirrorVertical()
  {
    Pixel[][] pixels = this.getPixels2D();
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int width = pixels[0].length;
    for (int row = 0; row < pixels.length; row++){
      for (int col = 0; col < width / 2; col++){
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][width - 1 - col];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }

  /** Mirror just part of a picture of a temple */
  public void mirrorTemple(){
    int mirrorPoint = 276;
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    int count = 0;
    Pixel[][] pixels = this.getPixels2D();

    // loop through the rows
    for (int row = 27; row < 97; row++){
      // loop from 13 to just before the mirror point
      for (int col = 13; col < mirrorPoint; col++){

        leftPixel = pixels[row][col];
        rightPixel = pixels[row]
                         [mirrorPoint - col + mirrorPoint];
        rightPixel.setColor(leftPixel.getColor());
      }
    }
  }

  /** copy from the passed fromPic to the
    * specified startRow and startCol in the
    * current picture
    * @param fromPic the picture to copy from
    * @param startRow the start row to copy to
    * @param startCol the start col to copy to
    */
  public void copy(Picture fromPic, int startRow, int startCol){
    Pixel fromPixel = null;
    Pixel toPixel = null;
    Pixel[][] toPixels = this.getPixels2D();
    Pixel[][] fromPixels = fromPic.getPixels2D();
    for (int fromRow = 0, toRow = startRow; fromRow < fromPixels.length
						&& toRow < toPixels.length; fromRow++, toRow++){
      for (int fromCol = 0, toCol = startCol;
           fromCol < fromPixels[0].length &&
           toCol < toPixels[0].length;
           fromCol++, toCol++){
        fromPixel = fromPixels[fromRow][fromCol];
        toPixel = toPixels[toRow][toCol];
        toPixel.setColor(fromPixel.getColor());
      }
    }
  }

  /** Method to create a collage of several pictures */
  public void createCollage(){
    Picture flower1 = new Picture("flower1.jpg");
    Picture flower2 = new Picture("flower2.jpg");
    this.copy(flower1,0,0);
    this.copy(flower2,100,0);
    this.copy(flower1,200,0);
    Picture flowerNoBlue = new Picture(flower2);
    flowerNoBlue.zeroBlue();
    this.copy(flowerNoBlue,300,0);
    this.copy(flower1,400,0);
    this.copy(flower2,500,0);
    this.mirrorVertical();
    this.write("collage.jpg");
  }


  /** Method to show large changes in color
    * @param edgeDist the distance for finding edges
    */
  public void edgeDetection(int edgeDist){
    Pixel leftPixel = null;
    Pixel rightPixel = null;
    Pixel[][] pixels = this.getPixels2D();
    Color rightColor = null;
    for (int row = 0; row < pixels.length; row++){
      for (int col = 0; col < pixels[0].length-1; col++){
        leftPixel = pixels[row][col];
        rightPixel = pixels[row][col+1];
        rightColor = rightPixel.getColor();
        if (leftPixel.colorDistance(rightColor) > edgeDist){
		  leftPixel.setColor(Color.BLACK);
        }
        else{
          leftPixel.setColor(Color.WHITE);
		}
      }
    }
  }


  /* Main method for testing - each class in Java can have a main
   * method
   */
  public static void main(String[] args){
    Picture panda = new Picture("Panda.jpg");
    panda.explore();
    Picture waved = panda.wavy(10);
    waved.explore();
    Picture stair = panda.stairStep(10,10);
    stair.explore();
    Picture liquid = panda.liquify(100);
    liquid.explore();
    Picture swapped = panda.swapLeftRight();
    swapped.explore();
    Picture blurred = panda.blur(20);
    blurred.explore();
    Picture enhanced = panda.swapLeftRight();
    enhanced.explore();

  }

}
