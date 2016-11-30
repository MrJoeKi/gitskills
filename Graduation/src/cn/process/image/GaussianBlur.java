package cn.process.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Joe on 2016/11/3.
 */
public class GaussianBlur {

    int width;
    int height;
    BufferedImage image = null;

    public GaussianBlur(int width,int height,BufferedImage image){
        this.width = width;
        this.height = height;
        this.image = image;
    }

    public BufferedImage Gaussian() throws IOException {

        BufferedImage image_new = new BufferedImage(width,height,BufferedImage.TYPE_BYTE_BINARY);
        int[][] matrix = new int[3][3];
        int[] values = new int[9];

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                readPixel(image, i, j, values);
                fillMatrix(matrix, values);
                image_new.setRGB(i, j, avgMatrix(matrix));
            }
        }
        return image_new;
    }

    private static void readPixel(BufferedImage img, int x, int y, int[] pixels) {
        int xStart = x - 1;
        int yStart = y - 1;
        int current = 0;

        for (int i = xStart; i < 3 + xStart; i++) {
            for (int j = yStart; j < 3 + yStart; j++) {
                int tx = i;
                if (tx < 0) {
                    tx = -tx;
                } else if (tx >= img.getWidth()) {
                    tx = x;
                }
                int ty = j;
                if (ty < 0) {
                    ty = -ty;
                } else if (ty >= img.getHeight()) {
                    ty = y;
                }
                pixels[current++] = img.getRGB(tx, ty) & 0xFF;
            }
        }

    }

    private static void fillMatrix(int[][] matrix, int[] values) {
        int filled = 0;
        for (int i = 0; i < matrix.length; i++) {
            int[] x = matrix[i];
            for (int j = 0; j < x.length; j++) {
                x[j] = values[filled++];
            }
        }
    }

    private static int avgMatrix(int[][] matrix) {
        int b = 0;

        for (int i = 0; i < matrix.length; i++) {
            int[] x = matrix[i];

            for (int j = 0; j < x.length; j++) {
                if (j == 1) {
                    continue;
                }
                Color c = new Color(x[j]);
                b += c.getBlue();
            }
        }
        return new Color(b/8, b/8, b/8).getRGB();
    }
}
