import java.awt.Color;

public class PixelPlayground {

    /* Removes the blue from every pixel in a given picture. */
    public static Picture zeroBlue(Picture pic) {
        Picture newPicture = new Picture(pic.getHeight(), pic.getWidth());
        newPicture.copy(pic, 0, 0);
        for (int x = 0; x < newPicture.getWidth(); x++) {
            for (int y = 0; y < newPicture.getHeight(); y++) {
                Pixel parkour = newPicture.getPixel(x, y);
                parkour.setBlue(0);
            }
        }

        return newPicture;
    }

    public static void main(String[] args) {
        Picture beachPic = new Picture("beach.jpg");
        beachPic.explore();
        Picture beachPicNoBlue = zeroBlue(beachPic);
        beachPicNoBlue.explore();
    }
}
