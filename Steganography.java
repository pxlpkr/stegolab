import java.awt.Color;

public class Steganography
{
    public static void main(String[] args)
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        Picture copy = testSetLow(beach, new Color(155, 0, 255));
        copy.explore();
        Picture copy2 = revealPicture(copy);
        copy2.setTitle("REVEALED");
        copy2.explore();
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
                pixels[r][c].setRed(col.getRed() % 4 * 64 + col.getRed() / 4);
                pixels[r][c].setGreen(col.getGreen() % 4 * 64 + col.getGreen() / 4);
                pixels[r][c].setBlue(col.getBlue() % 4 * 64 + col.getBlue() / 4);
            }
        }
        return copy; 
    }

}
