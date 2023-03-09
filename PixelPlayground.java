import java.awt.Color;

public class PixelPlayground {

    /* Removes the blue from every pixel in a given picture. */
    public static Picture zeroBlue(Picture pic) {
        // makes a copy of the picture to make edits to
        Picture newPicture = new Picture(pic.getHeight(), pic.getWidth());
        newPicture.copy(pic, 0, 0);

        // traverses image pixel by pixel
        for (int x = 0; x < newPicture.getWidth(); x++) {
            for (int y = 0; y < newPicture.getHeight(); y++) {
                Pixel parkour = newPicture.getPixel(x, y);

                //removes blue
                parkour.setBlue(0);
            }
        }

        return newPicture;
    }

    public static Picture keepOnlyBlue(Picture pic) {
        //makes a copy of the picture to make edits to
        Picture newPicture = new Picture(pic.getHeight(), pic.getWidth());
        newPicture.copy(pic, 0, 0);

        // traverses the image pixel by pixel
        for (int x = 0; x < newPicture.getWidth(); x++) {
            for (int y = 0; y < newPicture.getHeight(); y++) {
                Pixel parkour = newPicture.getPixel(x, y);

                // removes all green and red
                parkour.setGreen(0);
                parkour.setRed(0);
            }
        }

        return newPicture;
    }

    public static Picture negateColors(Picture pic) {
        //makes a copy of the picture to make edits to
        Picture newPicture = new Picture(pic.getHeight(), pic.getWidth());
        newPicture.copy(pic, 0, 0);

        // traverses the image pixel by pixel
        for (int x = 0; x < newPicture.getWidth(); x++) {
            for (int y = 0; y < newPicture.getHeight(); y++) {
                Pixel parkour = newPicture.getPixel(x, y);

                // sets each RGB value to their opposite
                parkour.setBlue(255-parkour.getBlue());
                parkour.setRed(255-parkour.getRed());
                parkour.setGreen(255-parkour.getGreen());
            }
        }

        return newPicture;
    }

    public static void average(Pixel parkour) {
        int avg = (parkour.getBlue() + parkour.getGreen() + parkour.getRed())/3;
        parkour.setBlue(avg);
        parkour.setRed(avg);
        parkour.setGreen(avg);
    }

    public static Picture grayscale(Picture pic) {
        // makes a copy of the picture to make edits to
        Picture newPicture = new Picture(pic.getHeight(), pic.getWidth());
        newPicture.copy(pic, 0, 0);

        // traverses the image pixel by pixel
        for (int x = 0; x < newPicture.getWidth(); x++) {
            for (int y = 0; y < newPicture.getHeight(); y++) {
                Pixel parkour = newPicture.getPixel(x, y);

                // gets average of the RGB values at that pixel and sets all values to the average
                average(parkour);
            }
        }

        return newPicture;
    }

    public static Picture fixUnderwater(Picture pic) {
        // makes a copy of the picture to make edits to
        Picture newPicture = new Picture(pic.getHeight(), pic.getWidth());
        newPicture.copy(pic, 0, 0);

        // traverses the image pixel by pixel
        for (int x = 0; x < newPicture.getWidth(); x++) {
            for (int y = 0; y < newPicture.getHeight(); y++) {
                Pixel parkour = newPicture.getPixel(x, y);

                // saving the color values at that pixel
                int red = parkour.getRed();
                int blue = parkour.getBlue();
                int green = parkour.getGreen();

                // checks what pixels are mostly fish and changing the color there
                if (!(red > 12 && blue > 160 && green < 170)) {
                    average(parkour);
                }
                
                
            }
        }
        return newPicture;
        }
    public static void main(String[] args) {
        Picture beachPic = new Picture("water.jpg");
        beachPic.explore();
        Picture beachPicNoBlue = fixUnderwater(beachPic);
        beachPicNoBlue.explore();
    }
}
