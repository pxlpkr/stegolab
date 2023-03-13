import java.awt.Color;
import java.awt.Point;
import java.util.ArrayList;

public class Steganography
{
    public static void main(String[] args)
    {
        Picture beach = new Picture("beach.jpg");
        Picture copy = new Picture(beach);
        copy.getPixel(80,20).setRed(6);
        copy.getPixel(140,90).setRed(6);

        Picture zzz = showDifferentArea(beach, findDifferences(beach, copy));
        zzz.explore();

        System.out.println(findDifferences(beach, copy));
        // Picture arch = new Picture("arch.jpg");
        // beach.explore();
        // arch.explore();
        // if (canHide(beach, arch)) {
        //     Picture hhh = hidePicture(beach, arch, 40, 0);
        //     hhh.setTitle("HIDDEN");
        //     hhh.explore();

        //     Picture copy = revealPicture(hhh);
        //     copy.setTitle("revealed");
        //     copy.explore();
        // }
    }

    /**
     * Clear the lower (rightmost) two bits in a pixel.
     */
    public static void clearLow(Pixel p)
    {
        p.setRed(p.getRed()/4*4);
        p.setGreen(p.getGreen()/4*4);
        p.setBlue(p.getBlue()/4*4);
    }

    /**
     * Test for the {@code clearLow} method
     */
    public static Picture testClearLow(Picture pic)
    {
        Picture output = new Picture(pic);
        for (Pixel p : output.getPixels())
        {
            clearLow(p);
        }

        return output;
    }

    /** 
     * Set the lower 2 bits in a pixel to the highest 2 bits in c 
     */
    public static void setLow(Pixel p, Color c) 
    {
        p.setRed(p.getRed() / 4 * 4 + c.getRed() / 64);
        p.setGreen(p.getGreen() / 4 * 4 + c.getGreen() / 64);
        p.setBlue(p.getBlue() / 4 * 4 + c.getBlue() / 64);
    }

    /**
     * Test for the {@code setLow} method
     */
    public static Picture testSetLow(Picture pic, Color c)
    {
        Picture output = new Picture(pic);
        for (Pixel p : output.getPixels())
        {
            setLow(p, c);
        }

        return output;
    }
    
    /** 
    * Sets the highest two bits of each pixel's colors to the lowest two bits of each pixel's colors 
    */ 
    public static Picture revealPicture(Picture hidden) 
    { 
        Picture copy = new Picture(hidden); 
        Pixel[][] pixels = copy.getPixels2D();
        Pixel[][] source = hidden.getPixels2D(); 
        for (int r = 0; r < pixels.length; r++)
        { 
            for (int c = 0; c < pixels[0].length; c++)
            { 	
                Color col = source[r][c].getColor();
                pixels[r][c].setRed(col.getRed() % 4 * 64);
                pixels[r][c].setGreen(col.getGreen() % 4 * 64);
                pixels[r][c].setBlue(col.getBlue() % 4 * 64);
            }
        }
        return copy; 
    }

    /** 
    * Determines whether secret can be hidden in source, which is true if source and secret are the same dimensions. 
    * @param source is not null 
    * @param secret is not null 
    * @return true if secret can be hidden in source, false otherwise. 
    */
    public static boolean canHide(Picture source, Picture secret) {
        return source.getHeight() >= secret.getHeight() && source.getWidth() >= secret.getWidth();
    }

    /** 
    * Some Documentation
    * @author Dylan Ward
    */
    public static Picture hidePicture(Picture source, Picture secret){
        Picture sourcePic = new Picture(source);
        Picture secretPic = new Picture(secret);
        Pixel[][] sourcePixels = sourcePic.getPixels2D();
        Pixel[][] secretPixels = secretPic.getPixels2D();
        for (int r = 0; r < secretPixels.length; r++)
        { 
            for (int c = 0; c < secretPixels[0].length; c++)
            { 	
                int hiddenRed = secretPixels[r][c].getRed() / 64;
                int hiddenGreen = secretPixels[r][c].getGreen() / 64;
                int hiddenBlue = secretPixels[r][c].getBlue() / 64;

                int currentRed = sourcePixels[r][c].getRed() / 4 * 4;
                int currentGreen = sourcePixels[r][c].getGreen() / 4 * 4;
                int currentBlue = sourcePixels[r][c].getBlue() / 4 * 4;

                sourcePixels[r][c].setRed(currentRed + hiddenRed);
                sourcePixels[r][c].setGreen(currentGreen + hiddenGreen);
                sourcePixels[r][c].setBlue(currentBlue + hiddenBlue);
            }
        } 
        return sourcePic;
    }

    /** 
    * Some Documentation
    * @author Dylan Ward
    * Precondition: startRow and startColumn are within source image dimensions
    */
    public static Picture hidePicture(Picture source, Picture secret, int startRow, int startColumn){
        Picture sourcePic = new Picture(source);
        Picture secretPic = new Picture(secret);
        Pixel[][] sourcePixels = sourcePic.getPixels2D();
        Pixel[][] secretPixels = secretPic.getPixels2D();
        for (int r = 0; r < secretPixels.length; r++)
        { 
            for (int c = 0; c < secretPixels[0].length; c++)
            { 	
                int hiddenRed = secretPixels[r][c].getRed() / 64;
                int hiddenGreen = secretPixels[r][c].getGreen() / 64;
                int hiddenBlue = secretPixels[r][c].getBlue() / 64;

                int currentRed = sourcePixels[r + startColumn][c + startRow].getRed() / 4 * 4;
                int currentGreen = sourcePixels[r + startColumn][c + startRow].getGreen() / 4 * 4;
                int currentBlue = sourcePixels[r + startColumn][c + startRow].getBlue() / 4 * 4;

                sourcePixels[r + startColumn][c + startRow].setRed(currentRed + hiddenRed);
                sourcePixels[r + startColumn][c + startRow].setGreen(currentGreen + hiddenGreen);
                sourcePixels[r + startColumn][c + startRow].setBlue(currentBlue + hiddenBlue);
            }
        } 
        return sourcePic;
    }

    //isSame method
    public static boolean isSame(Picture pic1, Picture pic2) {
        if(!(pic1.getHeight() == pic2.getHeight() && pic1.getWidth() == pic2.getWidth()))
        {
            return false;
        }
        Pixel[][] firstPixels = pic1.getPixels2D();
        Pixel[][] secondPixels = pic2.getPixels2D();
        for(int r = 0; r < secondPixels.length; r++){
            for(int c = 0; c < secondPixels[0].length; c++){
                if(!(firstPixels[r][c].getRed() == secondPixels[r][c].getRed() &&
                firstPixels[r][c].getGreen() == secondPixels[r][c].getGreen() &&
                firstPixels[r][c].getBlue() == secondPixels[r][c].getBlue()))
                {
                    return false;
                }
            }
        }
        return true;
    }

    public static ArrayList<Point> findDifferences(Picture pic1, Picture pic2) {
        ArrayList<Point> myArrayListOfPixels = new ArrayList<Point>();
        if(!(pic1.getHeight() == pic2.getHeight() && pic1.getWidth() == pic2.getWidth()))
        {
            return myArrayListOfPixels;
        }
        Pixel[][] firstPixels = pic1.getPixels2D();
        Pixel[][] secondPixels = pic2.getPixels2D();
        for(int r = 0; r < secondPixels.length; r++){
            for(int c = 0; c < secondPixels[0].length; c++){
                if(!(firstPixels[r][c].getRed() == secondPixels[r][c].getRed() &&
                firstPixels[r][c].getGreen() == secondPixels[r][c].getGreen() &&
                firstPixels[r][c].getBlue() == secondPixels[r][c].getBlue()))
                {
                    myArrayListOfPixels.add(new Point(c, r));
                }
            }
        }
        return myArrayListOfPixels;
    }

    public static Picture showDifferentArea(Picture picture, ArrayList<Point> differences) {
        int min_x = picture.getWidth(), max_x = 0, min_y = picture.getHeight(), max_y = 0;
        Picture copy = new Picture(picture);

        for (Point p : differences) {
            if (p.x < min_x)
                min_x = p.x;
            if (p.y < min_y)
                min_y = p.y;
            if (p.x > max_x)
                max_x = p.x;
            if (p.y > max_y)
                max_y = p.y;
        }

        for (int x = min_x; x < max_x; x++) {
            copy.getPixel(x,min_y).setColor(new Color(255, 0, 0));
            copy.getPixel(x,max_y).setColor(new Color(255, 0, 0));
        }
        for (int y = min_y; y < max_y; y++) {
            copy.getPixel(min_x,y).setColor(new Color(255, 0, 0));
            copy.getPixel(max_x,y).setColor(new Color(255, 0, 0));
        }

        return copy;
    }




}

