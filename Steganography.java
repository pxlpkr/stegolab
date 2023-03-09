import java.awt.Color;

public class Steganography
{
    public static void main(String[] args)
    {
        Picture beach = new Picture("beach.jpg");
        beach.explore();
        Picture copy = testClearLow(beach);
        copy.explore();
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
    
}
