import java.awt.Color;

public class PixelPlayground {

    /* Removes the blue from every pixel in a given picture. */
    public static void zeroBlue(Picture pic) {

    }

    public static void main(String[] args) {
        Picture beachPic = new Picture("beach.jpg");
        beachPic.explore();
        Picture beachPicNoBlue = zeroBlue(beachPic);
        beachPicNoBlue.explore();
    }
}
