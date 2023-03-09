import java.awt.Color;

public class PictureTester {
    public static Picture flipDiagonally(Picture original) {
        Picture output = new Picture(original);
        Pixel[][] arr = output.getPixels2D();
        for (int y = 0; y < output.getHeight(); y++) {
            double ratio = 1 - y / output.getHeight();
            for (int x = 0; x < ratio * output.getWidth(); x++) {
                Pixel in = arr[y][x];
                in.setColor(new Color(255, 0, 0));
                // Pixel out = arr[output.getHeight() - y - 1][output.getWidth() - x - 1];
                // out.setColor(in.getColor());
            }
        }

        return output;
    }
    public static void main(String[] args) {
        Picture beachPic = new Picture("beach.jpg");
        beachPic.explore();
        Picture beachPicNoBlue = flipDiagonally(beachPic);
        beachPicNoBlue.explore();
    }
}
