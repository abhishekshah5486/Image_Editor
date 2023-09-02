import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;

import javax.imageio.ImageIO;


public class ImageEditor1 {

    
    public static BufferedImage convertToGrayScale( BufferedImage inputImage ) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage grayImage = new BufferedImage (width, height, BufferedImage.TYPE_BYTE_GRAY);
        // The code snippet is iterating over each pixel in the input image and setting the
        // corresponding pixel in the grayImage to the same RGB value. This effectively creates a
        // grayscale version of the input image.
        for ( int j=0; j<height; j++ ) {
            for ( int k=0; k<width; k++ ) {
                grayImage.setRGB(k,j,inputImage.getRGB(k,j));
            }
        }
        
        return grayImage;
    }

    
    public static BufferedImage rotateImageACW(BufferedImage inputImage) {

        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);

        for ( int j=0; j<width; j++ ) {
            for ( int k=0; k<height; k++ ) {
                outputImage.setRGB(k,j,inputImage.getRGB(j,k));
            }
        }
        return outputImage ;
    }
    
    public static BufferedImage rotateImageCW( BufferedImage inputImage ) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);
        for ( int j=0; j<width; j++ ) {
            for ( int k=0; k<height; k++) {
                outputImage.setRGB(k,j,inputImage.getRGB(j,k));
            }
        }
        int newHeight = outputImage.getHeight();
        int newWidth = outputImage.getWidth();

        for ( int j=0; j<(newWidth/2); j++ ) {
            for ( int k=0; k<newHeight; k++ ) {
                int oldPixelVal = outputImage.getRGB(j,k);
                int newPixelVal = outputImage.getRGB((newWidth-j-1),k);
                outputImage.setRGB(j,k,newPixelVal);
                outputImage.setRGB((newWidth-1-j),k,oldPixelVal);
            }
        }
        return outputImage;
    }

    public static BufferedImage changeBrightness(BufferedImage inputImage, int percentage, int option ) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(height, width, BufferedImage.TYPE_INT_RGB);

        for ( int j=0; j<height; j++ ) {
            for ( int k=0; k<width; k++ ) {

                Color pixel = new Color(inputImage.getRGB(k,j));
                int red = pixel.getRed();
                int green = pixel.getGreen();
                int blue = pixel.getBlue();

                if ( option==1 ) {
                    int newRed = red + (percentage*red/100);
                    if ( newRed > 255 ) {
                        newRed = 255;
                    }
                    int newGreen = green + (percentage*green/100);
                    if ( newGreen > 255 ) {
                        newGreen = 255;
                    }
                    int newBlue = blue + (percentage*blue/100);
                    if ( newBlue > 255 ) {
                        newBlue = 255;
                    }
                    Color newPixel = new Color(newRed,newGreen,newBlue);
                    outputImage.setRGB(k,j,newPixel.getRGB());


                }

                else if ( option==2 ) {

                    int newRed = red - (percentage*red/100);
                    if ( newRed < 0  ) {
                        newRed = 0;
                    }
                    int newGreen = green - (percentage*green/100);
                    if ( newGreen < 0 ) {
                        newGreen = 0;
                    }
                    int newBlue = blue - (percentage*blue/100);
                    if ( newBlue < 0 ) {
                        newBlue = 0;
                    }
                    Color newPixel = new Color(newRed,newGreen,newBlue);
                    outputImage.setRGB(k,j,newPixel.getRGB());

                }
            }
        }
        return outputImage;
    }

    public static BufferedImage invertImageVertically(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for ( int j=0; j<(height/2); j++ ) {
            for ( int k=0; k<width; k++ ) {
                int oldPixelVal = inputImage.getRGB(k,j);
                int newPixelVal = inputImage.getRGB(k,(height-1-j));
                outputImage.setRGB(k,j,newPixelVal);
                outputImage.setRGB(k,(height-1-j),oldPixelVal);
            }
        }
        return outputImage;
    }

    public static BufferedImage invertImageHorizontally(BufferedImage inputImage) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();

        BufferedImage outputImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        for ( int j=0; j<(width/2); j++ ) {
            for ( int k=0; k<height; k++ ) {
                int oldPixelVal = inputImage.getRGB(j,k);
                int newPixelVal = inputImage.getRGB((width-1-j),k);
                outputImage.setRGB(j,k,newPixelVal);
                outputImage.setRGB((width-1-j),k,oldPixelVal);
            }
        }
        return outputImage;
    }

    public static BufferedImage convertToBlurr(BufferedImage inputImage, int sizeMatrix ) {
        int height = inputImage.getHeight();
        int width = inputImage.getWidth();
        BufferedImage outputImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        
        for ( int j=0; j<(width/sizeMatrix); j++ ) {
            for ( int k=0; k<(height/sizeMatrix); k++ ) {
                
                int red = 0;
                int green = 0;
                int blue = 0;
                for ( int x=(sizeMatrix*j); x<sizeMatrix*(j+1); x++ ) {
                    for ( int y=(sizeMatrix*k); y<sizeMatrix*(k+1); y++ ) {
                        Color pixels = new Color(inputImage.getRGB(x,y));
                        red = red + pixels.getRed();
                        blue = blue + pixels.getBlue();
                        green = green + pixels.getGreen();
                        
                    }
                }
                int avgRed = red/(sizeMatrix*sizeMatrix);
                int avgGreen = green/(sizeMatrix*sizeMatrix);
                int avgBlue = blue/(sizeMatrix*sizeMatrix);

                for ( int x=(sizeMatrix*j); x<sizeMatrix*(j+1); x++ ) {
                    for ( int y=(sizeMatrix*k); y<sizeMatrix*(k+1); y++ ) {
                        Color newPixels = new Color (avgRed, avgGreen, avgBlue);
                        outputImage.setRGB(x,y,newPixels.getRGB());
                    }
                }
            }
        }
        return outputImage;
    }
      public static void main(String args[]) {
        Scanner myScanner = new Scanner (System.in);
        
        System.out.println("Enter the directory of the image.");
        String imageLocation = myScanner.next();
        File inputFile = new File(imageLocation);

       

        System.out.println();
        System.out.println( "Enter 1 ---> Convert an Image to GrayScale");
        System.out.println( "Enter 2 ---> Rotating Image by 90 degrees anticlockwise");
        System.out.println( "Enter 3 ---> Rotating Image by 90 degrees clockwise");
        System.out.println( "Enter 4 ---> Changing the Brightness of Image (Increase/Decrease)");
        System.out.println( "Enter 5 ---> Vertically Inverting the Image");
        System.out.println( "Enter 6 ---> Horizontally Inverting the Image//Mirror Image");
        System.out.println( "Enter 7 ---> Blurring the Image/Pixelated Blur/Guassian Blur");
        System.out.println();
        
        System.out.println("Enter the operation to perform.");
        int choice = myScanner.nextInt();

        
    

        try {
            BufferedImage inputImage = ImageIO.read(inputFile);
            System.out.println("Enter the name of outputImage you wish to set!");
            String name0fFile = myScanner.next(); //Eg. "image.jpg"
            File outputFile = new File(name0fFile);

            switch (choice) {
                case 1: 
                        ImageIO.write(convertToGrayScale(inputImage),"jpg",outputFile);
                        System.out.println("Image converted to grayscale.");
                        break;
                case 2: 
                        ImageIO.write(rotateImageACW(inputImage), "jpg", outputFile);
                        System.out.println("Image rotated anticlockwise by 90m degrees.");
                        break;

                case 3:
                        ImageIO.write(rotateImageCW(inputImage), "jpg" ,outputFile);
                        System.out.println("Image rotated clockwise by 90 degrees.");
                        break;
                
                case 4: System.out.println("Enter 1 ---> Increase Brightness");
                        System.out.println("Enter 2 ---> Decrease Brightness");
                        int option = myScanner.nextInt();
                        System.out.println("Enter the percentage by which you want to increase or decrease the brightness of image.");
                        int percentage = myScanner.nextInt();
                        ImageIO.write(changeBrightness(inputImage,percentage,option),"jpg",outputFile);
                        if ( option == 1 ) {
                            System.out.println("Image brightness increased by" + percentage + "%");
                        }
                        else {
                            System.out.println("Image brightness decreased by" + percentage + "%");
                        }
                        break;

                case 5: ImageIO.write(invertImageVertically(inputImage),"jpg",outputFile);
                        System.out.println("Image inverted vertically.");
                        break;
        
                
                case 6: ImageIO.write(invertImageHorizontally(inputImage),"jpg",outputFile);
                        System.out.println("Image inverted horizontally.");
                        break;

                case 7: System.out.println("Enter the amount of blur.");
                        int sizeMatrix = myScanner.nextInt();
                        ImageIO.write(convertToBlurr(inputImage,sizeMatrix),"jpg",outputFile);
                        System.out.println("Image has been blurred.");
                        break;

                default: System.out.println("INVALID CHOICE !");
                         System.out.println("Enter a valid choice to proceed.");
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
        
    }
}
