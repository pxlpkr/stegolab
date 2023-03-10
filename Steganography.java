import java.awt.Color;

public class Steganography
{
    public static void main(String[] args)
    {
        Picture beach = new Picture("beach.jpg");
        Picture arch = new Picture("arch.jpg");
        beach.explore();
        arch.explore();
        if (canHide(beach, arch)) {
            Picture copy = revealPicture(hidePicture(beach, arch));
            copy.setTitle("Secret feet picks");
            copy.explore();
        }
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
    public static boolean canHide(Picture source, Picture secret){
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
}

