package cn.process.image;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by Joe on 2016/10/25.
 */
public class ImageProcess {

    int width;
    int height;
    File file = null;
    String path_process = "D:\\JaveDev\\Graduation\\imagehandled";
    static Color background_color = Color.WHITE;
    static Color forge_color = Color.BLACK;
    int normalizeSize = 16;
    int charNum = 6;

    public ImageProcess(){

    }

    public ImageProcess(File file, BufferedImage image) throws IOException {

        this.file = file;
        width = image.getWidth();
        height = image.getHeight();
    }

    //    灰度化操作
    public BufferedImage Gray(BufferedImage image) throws IOException {
        int rgb;
        BufferedImage image_gray = new BufferedImage(width, height, image.getType());

        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rgb = image.getRGB(i, j);
                int red = (rgb >> 16) & 0xFF;
                int green = (rgb >> 8) & 0xFF;
                int blue = (rgb >> 0) & 0xFF;
                int gray = (int) (red * 0.299 + green * 0.587 + blue * 0.114);
                Color color_new = new Color(gray, gray, gray);
                image_gray.setRGB(i, j, color_new.getRGB());
            }
        }
        String path = path_process + "\\gray";
        SaveFile(image_gray, path, file.getName().replace(".jpg", "") + "_gray.bmp");
        return image_gray;
    }

    //    二值化操作
    public BufferedImage Binary(BufferedImage image) throws IOException {
        int threshold = Ostu(image);
        BufferedImage image_binary = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        int rgb;
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                rgb = image.getRGB(i, j) & 0xFF;
                if (rgb > threshold)
                    image_binary.setRGB(i, j, background_color.getRGB());
                else
                    image_binary.setRGB(i, j, forge_color.getRGB());
            }
        }
        String path = path_process + "\\binary";
        SaveFile(image_binary, path, file.getName().replace(".jpg", "") + "_binary.bmp");
        return image_binary;
    }

    //    形态学膨胀操作
    public BufferedImage Expand(BufferedImage image) throws IOException {

        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];
        BufferedImage image_expand = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        int index = 0, index1 = 0, newRow = 0, newCol = 0;
        int tb1 = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                inPixels[i * width + j] = image.getRGB(j, i);
        }
        for (int row = 0; row < height; row++) {
            int tb = 0;
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                tb = inPixels[index] & 0xff;
                boolean dilation = false;
                for (int offsetY = -1; offsetY <= 1; offsetY++) {
                    for (int offsetX = -1; offsetX <= 1; offsetX++) {
                        if (offsetY == 0 && offsetX == 0) {
                            continue;
                        }
                        newRow = row + offsetY;
                        newCol = col + offsetX;
                        if (newRow < 0 || newRow >= height) {
                            newRow = 0;
                        }
                        if (newCol < 0 || newCol >= width) {
                            newCol = 0;
                        }
                        index1 = newRow * width + newCol;
                        tb1 = inPixels[index1] & 0xff;
                        if (tb1 == forge_color.getRed()) {
                            dilation = true;
                            break;
                        }
                    }
                    if (dilation) {
                        break;
                    }
                }
                if (dilation) {
                    tb = 0;
                } else {
                    tb = 255;
                }
                outPixels[index] = tb;
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = new Color(outPixels[i * width + j], outPixels[i * width + j], outPixels[i * width + j]);
                image_expand.setRGB(j, i, color.getRGB());
            }
        }
//        File newFile = new File(System.getProperty("user.dir") + "/expand.bmp");
//        ImageIO.write(image_expand, "bmp", newFile);
        String path = path_process + "\\expand";
        SaveFile(image_expand, path, file.getName().replace(".jpg", "") + "_expand.bmp");
        return image_expand;
    }

    //    形态学腐蚀
    public BufferedImage Erosion(BufferedImage image) throws IOException {
        BufferedImage image_erosion = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];
        int index = 0, index1 = 0, newRow = 0, newCol = 0;
        int red1 = 0, green1 = 0, blue1 = 0;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                inPixels[i * width + j] = image.getRGB(j, i);
        }
        for (int row = 0; row < height; row++) {
            int blue = 0;
            for (int col = 0; col < width; col++) {
                index = row * width + col;
                blue = inPixels[index] & 0xff;
                boolean erosion = false;
                for (int offsetY = -1; offsetY <= 1; offsetY++) {
                    for (int offsetX = -1; offsetX <= 1; offsetX++) {
                        if (offsetY == 0 && offsetX == 0) {
                            continue;
                        }
                        newRow = row + offsetY;
                        newCol = col + offsetX;
                        if (newRow < 0 || newRow >= height) {
                            newRow = 0;
                        }
                        if (newCol < 0 || newCol >= width) {
                            newCol = 0;
                        }
                        index1 = newRow * width + newCol;
                        red1 = (inPixels[index1] >> 16) & 0xff;
                        green1 = (inPixels[index1] >> 8) & 0xff;
                        blue1 = inPixels[index1] & 0xff;
                        if (red1 == background_color.getRed() && green1 == blue1) {
                            erosion = true;
                            break;
                        }
                    }
                    if (erosion) {
                        break;
                    }
                }

                if (erosion) {
                    blue = 255;
                } else {
                    blue = 0;
                }
                outPixels[index] = blue;
            }
        }

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = new Color(outPixels[i * width + j], outPixels[i * width + j], outPixels[i * width + j]);
                image_erosion.setRGB(j, i, color.getRGB());
            }
        }
//        image_erosion.setRGB(0,0,width,height,outPixels,0,1);
        String path = path_process + "\\erosion";
        SaveFile(image_erosion, path, file.getName().replace(".jpg", "") + "_erosion.bmp");
        return image_erosion;
    }

    //    均值滤波
    public BufferedImage AvrFiltering(BufferedImage image) throws IOException {
        BufferedImage image_denoise = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                inPixels[i * width + j] = image.getRGB(j, i);
        }
        ColorModel cm = ColorModel.getRGBdefault();
        int r = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x != 0 && x != width - 1 && y != 0 && y != height - 1) {
                    //g = (f(x-1,y-1) + f(x,y-1)+ f(x+1,y-1)
                    //  + f(x-1,y) + f(x,y) + f(x+1,y)
                    //  + f(x-1,y+1) + f(x,y+1) + f(x+1,y+1))/9
                    r = (cm.getRed(inPixels[x - 1 + (y - 1) * width]) + cm.getRed(inPixels[x + (y - 1) * width]) + cm.getRed(inPixels[x + 1 + (y - 1) * width])
                            + cm.getRed(inPixels[x - 1 + (y) * width]) + cm.getRed(inPixels[x + (y) * width]) + cm.getRed(inPixels[x + 1 + (y) * width])
                            + cm.getRed(inPixels[x - 1 + (y + 1) * width]) + cm.getRed(inPixels[x + (y + 1) * width]) + cm.getRed(inPixels[x + 1 + (y + 1) * width])) / 9;
//                    r = (cm.getRed(inPixels[x + (y - 1) * width])
//                            + cm.getRed(inPixels[x - 1 + (y) * width]) + cm.getRed(inPixels[x + (y) * width]) + cm.getRed(inPixels[x + 1 + (y) * width])
//                            + +cm.getRed(inPixels[x + (y + 1) * width])) / 5;
                    outPixels[y * width + x] = 255 << 24 | r << 16 | r << 8 | r;

                } else {
                    outPixels[y * width + x] = inPixels[y * width + x];
                }
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = new Color(outPixels[i * width + j]);
                image_denoise.setRGB(j, i, color.getRGB());
            }
        }
//        image_erosion.setRGB(0,0,width,height,outPixels,0,1);
        String path = path_process + "\\avg";
        SaveFile(image_denoise, path, file.getName().replace(".jpg", "") + "_avgfilter.bmp");
        return image_denoise;
    }

    //    中值滤波
    public BufferedImage MedianFiltering(BufferedImage image) throws IOException {
        BufferedImage image_denoise = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        int[] inPixels = new int[width * height];
        int[] outPixels = new int[width * height];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++)
                inPixels[i * width + j] = image.getRGB(j, i);
        }
        int[] temp = new int[9];
        ColorModel cm = ColorModel.getRGBdefault();
        int r = 0;
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (x != 0 && x != width - 1 && y != 0 && y != height - 1) {
                    //g = median[(x-1,y-1) + f(x,y-1)+ f(x+1,y-1)
                    //  + f(x-1,y) + f(x,y) + f(x+1,y)
                    //  + f(x-1,y+1) + f(x,y+1) + f(x+1,y+1)]
                    temp[0] = cm.getRed(inPixels[x - 1 + (y - 1) * width]);
                    temp[1] = cm.getRed(inPixels[x + (y - 1) * width]);
                    temp[2] = cm.getRed(inPixels[x + 1 + (y - 1) * width]);
                    temp[3] = cm.getRed(inPixels[x - 1 + (y) * width]);
                    temp[4] = cm.getRed(inPixels[x + (y) * width]);
                    temp[5] = cm.getRed(inPixels[x + 1 + (y) * width]);
                    temp[6] = cm.getRed(inPixels[x - 1 + (y + 1) * width]);
                    temp[7] = cm.getRed(inPixels[x + (y + 1) * width]);
                    temp[8] = cm.getRed(inPixels[x + 1 + (y + 1) * width]);
                    Arrays.sort(temp);
                    r = temp[4];
                    outPixels[y * width + x] = 255 << 24 | r << 16 | r << 8 | r;
                } else {
                    outPixels[y * width + x] = inPixels[y * width + x];
                }
            }
        }
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                Color color = new Color(outPixels[i * width + j]);
                image_denoise.setRGB(j, i, color.getRGB());
            }
        }
//        image_erosion.setRGB(0,0,width,height,outPixels,0,1);
        String path = path_process + "\\median";
        SaveFile(image_denoise, path, file.getName().replace(".jpg", "") + "_medfilter.bmp");
        return image_denoise;
    }

    //    高斯滤波，高斯模糊（有问题！！！）
    public BufferedImage Gaussian(BufferedImage image) throws IOException {

        BufferedImage image_gaussian = null;
        GaussianBlur gaussianBlur = new GaussianBlur(width, height, image);
        image_gaussian = gaussianBlur.Gaussian();
        String path = path_process + "\\gaussian";
        SaveFile(image_gaussian, path, file.getName().replace(".jpg", "") + "_gaussian.bmp");
        return image_gaussian;
    }

    //    垂直-Y投影直方图
    public void YProjection(BufferedImage image) throws IOException {
        int[] horizon = new int[width];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((image.getRGB(i, j) & 0xFF) < background_color.getRed()) {
//                测试规整编码
//                if ((image.getRGB(i,j)&0xFF) >= background_color.getRed()){
                    horizon[i]++;
                }
            }
        }
        BufferedImage image_new = getHist(horizon);
        String path = path_process + "\\Ypro";
        SaveFile(image_new, path, file.getName().replace(".jpg", "") + "_yprojection.bmp");
    }

    //    水平-X投影直方图
    public void XProjection(BufferedImage image) {

    }

    public BufferedImage getHist(int[] intensity) {

        BufferedImage pic = new BufferedImage(width, height, BufferedImage.TYPE_3BYTE_BGR);
        Graphics2D g2d = pic.createGraphics();
        g2d.setPaint(Color.WHITE);
        g2d.fillRect(0, 0, width, height);
//        g2d.setPaint(Color.BLACK);
//        g2d.drawLine(5, 250, 150, 250);
//        g2d.drawLine(5, 250, 5, 5);

        int max = 0;  //找到直方图中最大的值
        for (int i = 0; i < intensity.length; i++) {
            if (intensity[i] > max) {
                max = intensity[i];
            }
        }

        float rate = 50.0f / ((float) max);
        for (int i = 0; i < intensity.length; i++) {
            int frequency = (int) (intensity[i] * rate);
            g2d.setPaint(Color.BLUE);
            g2d.drawLine(i, height, i, height - frequency);
        }

        g2d.setPaint(Color.RED);
        g2d.drawString("", 100, 270);
        return pic;
    }

    //边缘检测
    public BufferedImage Segmentation(BufferedImage image, String pathRoot) throws IOException {
//        int[] horizon = new int[width];
//        for (int i = 0; i < width; i++) {
//            int numpixel = 0;
//            for (int j = 0; j < height; j++) {
//                if ((image.getRGB(i, j) & 0xFF) < background_color.getRed()) {
//                    ++horizon[i];
//                }
//            }
//        }

//        System.out.println("horizon-width : " + horizon.length);
//        System.out.println("width : " + width);
//        System.out.println("height : " + height);
//        int[] position_horizon = new int[12];
//        int[] position_pixnumber = new int[6];
//        int index = 0;
//        for (int i = 1; i < horizon.length - 1; i++) {
//            if (horizon[i] > 4 && horizon[i - 1] < 5) {
//                position_horizon[index] = i;
//                index++;
//            } else if (horizon[i] > 4 && horizon[i + 1] < 5) {
//                position_horizon[index] = i;
//                index++;
//            }
//        }
//        for (int i = 0; i < position_horizon.length; i++) {
//            System.out.print(position_horizon[i] + " ");
//        }
//        System.out.println();

        BufferedImage newPic = new BufferedImage(width, height,
                BufferedImage.TYPE_BYTE_BINARY);

        float[] elements = {0.0f, -1.0f, 0.0f, -1.0f, 4.0f, -1.0f, 0.0f,
                -1.0f, 0.0f};
        Kernel kernel = new Kernel(3, 3, elements);
        ConvolveOp cop = new ConvolveOp(kernel, ConvolveOp.EDGE_ZERO_FILL, null);
        cop.filter(image, newPic);
        BufferedImage imageSeg = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((newPic.getRGB(i, j) & 0xFF) == background_color.getRed()) {
                    Color color = new Color(forge_color.getBlue(), forge_color.getBlue(), forge_color.getBlue());
                    imageSeg.setRGB(i, j, color.getRGB());
                } else {
                    Color color = new Color(background_color.getBlue(), background_color.getBlue(), background_color.getBlue());
                    imageSeg.setRGB(i, j, color.getRGB());
                }
            }
        }
        String path = path_process + pathRoot;
        SaveFile(imageSeg, path, file.getName().replace(".jpg", "") + "_edgedete.bmp");
        return imageSeg;
    }

    public void ImageTrans(BufferedImage image) throws IOException {
        BufferedImage image_new = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if ((image.getRGB(i, j) & 0xFF) == background_color.getRed()) {
                    Color color = new Color(forge_color.getBlue(), forge_color.getBlue(), forge_color.getBlue());
                    image_new.setRGB(i, j, color.getRGB());
                } else {
                    Color color = new Color(background_color.getBlue(), background_color.getBlue(), background_color.getBlue());
                    image_new.setRGB(i, j, color.getRGB());
                }
            }
        }
        String path = path_process + "\\imagetrans";
        SaveFile(image_new, path, file.getName().replace(".jpg", "") + "_colortrans.bmp");
    }

    public int[][] GetSingleChar(BufferedImage image) throws IOException {
//        System.out.println(width);
//        System.out.println(height);
        int[][] flag = new int[height][width];
        int[][] flag_old = new int[height][width];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if ((image.getRGB(j, i) & 0xFF) == forge_color.getRed()) {
                    flag[i][j] = 1;
                    flag_old[i][j] = 1;
                }
            }
        }
//        for (int j = 0; j < height; j++) {
//            for (int i = 0; i < width; i++) {
//                if (flag[j][i] == 1)
//                    System.out.print("*");
//                else
//                    System.out.print(" ");
//            }
//            System.out.println();
//        }

        int[] edge = {0, 0, 0, 0};
        int[][] alledge = new int[6][4];
        int index = 0;

//        i为长（x）轴，j为高（y）轴
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (flag[j][i] == 1) {
                    edge[0] = j;
                    edge[1] = j + 1;
                    edge[2] = i;
                    edge[3] = i + 1;
                    flag[j][i] = 0;
                    EightPosition(flag, i, j, edge);
                    System.out.println("i = " + i);
                    System.out.println("j = " + j);
                    if (((edge[1] - edge[0]) * (edge[3] - edge[2])) > 150) {
                        alledge[index][0] = edge[0];
                        alledge[index][1] = edge[1] + 1;
                        alledge[index][2] = edge[2];
                        alledge[index][3] = edge[3] + 1;
                        System.out.print("alledge[" + index + "][0]:" + alledge[index][0]);
                        System.out.print("alledge[" + index + "][1]:" + alledge[index][1]);
                        System.out.print("alledge[" + index + "][2]:" + alledge[index][2]);
                        System.out.print("alledge[" + index + "][3]:" + alledge[index][3]);
                        System.out.println();
                        index++;
                        i = 0;
                        j = 0;
                        for (int k = 0; k < height; k++) {
                            for (int m = 0; m < width; m++) {
                                if (flag[k][m] == 1) {
                                    System.out.print("*");
                                } else {
                                    System.out.print(" ");
                                }
                            }
                            System.out.println();
                        }
                    }
                }
            }
        }

//        for (int i = 0; i < height; i++) {
//            for (int j = 0; j < width; j++) {
//                if ((image.getRGB(j, i) & 0xFF) == forge_color.getRed()) {
//                    flag[i][j] = 1;
//                }
//            }
//        }

        for (int k = 0; k < 6; k++) {
            for (int j = alledge[k][0]; j < alledge[k][1]; j++) {
                for (int i = alledge[k][2]; i < alledge[k][3]; i++) {
                    if (flag_old[j][i] == 1)
                        System.out.print("*");
                    else
                        System.out.print(" ");
                }
                System.out.println();
            }
        }

        for (int k = 0; k < 6; k++) {
            System.out.println("Area[" + k + "]:" + ((alledge[k][1] - alledge[k][0]) * (alledge[k][3] - alledge[k][2])));
        }

        return alledge;
//        for (int j = 0; j < height; j++) {
//            for (int i = 0; i < width; i++) {
//                if (flag[j][i] == 1)
//                    System.out.print("*");
//                else
//                    System.out.print(" ");
//            }
//            System.out.println();
//        }

    }

    public void PrintAfterSeg(BufferedImage image, int[][] alledge, String pathRoot) throws IOException {
        BufferedImage image_afterseg = null;
        int index = 0;
        String path = null;
        for (int k = 0; k < alledge.length; k++) {
            if (((alledge[k][1] - alledge[k][0]) * (alledge[k][3] - alledge[k][2])) == 0) {
                continue;
            } else {
                int area = ((alledge[index][1] - alledge[index][0]) * (alledge[index][3] - alledge[index][2]));
                if (area > 700 && (alledge[index][3] - alledge[index][2]) > 30) {
                    int width = (alledge[k][3] - alledge[k][2] - 2) / 2;
                    int height = alledge[k][1] - alledge[k][0] - 2;

////                    去除上部空白行
//                    int countZero = 0;
//                    int blankUp = 0;
//                    for (int i = alledge[k][0] + 1; i < alledge[k][1] - 1; i++) {
//                        if (countZero == width + 1 || countZero == width) {
//                            blankUp++;
//                            countZero = 0;
//                        } else if (i != alledge[k][0] + 1) {
//                            break;
//                        }
//                        for (int j = alledge[k][2]; j < (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2); j++) {
//                            if ((image.getRGB(j, i) & 0xFF) == background_color.getGreen()) {
//                                countZero++;
//                            }
//                        }
//                    }
//
////                    去除下部空白行
//                    int blankDown = 0;
//                    countZero = 0;
//                    for (int i = alledge[k][1] - 1; i > alledge[k][0] + 1; i--) {
//                        if (countZero == width + 1 || countZero == width) {
//                            blankDown++;
//                            countZero = 0;
//                        } else if (i != alledge[k][1] - 1) {
//                            break;
//                        }
//                        for (int j = alledge[k][2]; j < (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2); j++) {
//                            if ((image.getRGB(j, i) & 0xFF) == background_color.getGreen()) {
//                                countZero++;
//                            }
//                        }
//                    }
//
////                    去除左边空白行
//                    int blankLeft = 0;
//                    countZero = 0;
//                    for (int i = alledge[k][2]; i < (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2); i++) {
//                        if (countZero == width + 1 || countZero == width) {
//                            blankLeft++;
//                            countZero = 0;
//                        } else if (i != alledge[k][1] - 1) {
//                            break;
//                        }
//                        for (int j = alledge[k][0] + 1; j < alledge[k][1] - 1; j++) {
//                            if ((image.getRGB(i, j) & 0xFF) == background_color.getGreen()) {
//                                countZero++;
//                            }
//                        }
//                    }
//
////                    去除右边空白行
//                    int blankRight = 0;
//                    countZero = 0;
//                    for (int i = (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2); i > alledge[k][2]; i--) {
//                        if (countZero == width + 1 || countZero == width) {
//                            blankRight++;
//                            countZero = 0;
//                        } else if (i != alledge[k][1] - 1) {
//                            break;
//                        }
//                        for (int j = alledge[k][0] + 1; j < alledge[k][1] - 1; j++) {
//                            if ((image.getRGB(i, j) & 0xFF) == background_color.getGreen()) {
//                                countZero++;
//                            }
//                        }
//                    }
                    int[] deleteBlank = DeleteBlank(image,alledge[k][2],(alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2),alledge[k][0] + 1,alledge[k][1] - 1);
                    int blankUp = deleteBlank[0];
                    int blankDown =deleteBlank[1];
                    int blankLeft = deleteBlank[2];
                    int blankRight = deleteBlank[3];
                    image_afterseg = new BufferedImage(width - blankLeft - blankRight, height - blankUp - blankDown, BufferedImage.TYPE_BYTE_BINARY);
//                    System.out.println("alledge[" + k + "][0] :" + alledge[k][0]);
//                    System.out.println("alledge[" + k + "][1] :" + alledge[k][1]);
//                    System.out.println("alledge[" + k + "][2] :" + alledge[k][2]);
//                    System.out.println("alledge[" + k + "][3] :" + alledge[k][3]);
//                    System.out.println("height start:" + (alledge[k][0] + 1 + blankUp) + "end:" + (alledge[k][1] - 1 - blankDown));
                    for (int j = alledge[k][0] + 1 + blankUp, m = 0; j < alledge[k][1] - 1 - blankDown; j++, m++) {
                        boolean flag = false;
                        for (int i = alledge[k][2] + blankLeft, n = 0; i < (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2) - blankRight; i++, n++) {
                            if (i != alledge[0][2] && !flag) {
                                i++;
                                flag = true;
                            }
                            int rgb = image.getRGB(i, j);
                            System.out.println("n = " + n + ",m = " + m + "i = " + i + ",j = " + j);
                            image_afterseg.setRGB(n, m, rgb);
                        }
                    }
                    path = path_process + pathRoot;

                    SaveFile(image_afterseg, path, file.getName().replace(".jpg", "") + "_" + index + ".bmp");
                    imgScale(image_afterseg, image_afterseg.getWidth(), image_afterseg.getHeight(), normalizeSize, normalizeSize, index, pathRoot + "_normal");
                    ++index;

                    width = (alledge[k][3] - alledge[k][2] - 2) / 2;
                    height = alledge[k][1] - alledge[k][0] - 2;

////                    去除上部空白行
//                    countZero = 0;
//                    blankUp = 0;
//                    for (int i = alledge[k][0] + 1; i < alledge[k][1] - 1; i++) {
//                        if (countZero == width + 2 || countZero == width + 1) {
//                            blankUp++;
//                            countZero = 0;
//                        } else if (i != alledge[k][0] + 1) {
//                            break;
//                        }
//                        for (int j = (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2) - 1; j < alledge[k][3] - 1; j++) {
//                            if ((image.getRGB(j, i) & 0xFF) == background_color.getGreen()) {
//                                countZero++;
//                            }
//                        }
//                    }
//
////                    去除下部空白行
//                    blankDown = 0;
//                    countZero = 0;
//                    for (int i = alledge[k][1] - 1; i > alledge[k][0] + 1; i--) {
//                        if (countZero == width + 2 || countZero == width + 1) {
//                            blankDown++;
//                            countZero = 0;
//                        } else if (i != alledge[k][1] - 1) {
//                            break;
//                        }
//                        for (int j = (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2) - 1; j < alledge[k][3] - 1; j++) {
//                            if ((image.getRGB(j, i) & 0xFF) == background_color.getGreen()) {
//                                countZero++;
//                            }
//                        }
//                    }
//
////                    去除左边空白行
//                    int blankLeft = 0;
//                    countZero = 0;
//                    for (int i = (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2) - 1; i < alledge[k][3] - 1; i++) {
//                        if (countZero == width + 1 || countZero == width) {
//                            blankLeft++;
//                            countZero = 0;
//                        } else if (i != alledge[k][1] - 1) {
//                            break;
//                        }
//                        for (int j = alledge[k][0] + 1; j < alledge[k][1] - 1; j++) {
//                            if ((image.getRGB(i, j) & 0xFF) == background_color.getGreen()) {
//                                countZero++;
//                            }
//                        }
//                    }
//
////                    去除右边空白行
//                    int blankRight = 0;
//                    countZero = 0;
//                    for (int i = alledge[k][3] - 1; i > (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2) - 1; i--) {
//                        if (countZero == width + 1 || countZero == width) {
//                            blankRight++;
//                            countZero = 0;
//                        } else if (i != alledge[k][1] - 1) {
//                            break;
//                        }
//                        for (int j = alledge[k][0] + 1; j < alledge[k][1] - 1; j++) {
//                            if ((image.getRGB(i, j) & 0xFF) == background_color.getGreen()) {
//                                countZero++;
//                            }
//                        }
//                    }

                    deleteBlank = DeleteBlank(image,(alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2) - 1,alledge[k][3] - 1,alledge[k][0] + 1,alledge[k][1] - 1);
                    blankUp = deleteBlank[0];
                    blankDown =deleteBlank[1];
                    blankLeft = deleteBlank[2];
                    blankRight = deleteBlank[3];

                    image_afterseg = new BufferedImage(width - blankLeft - blankRight, height - blankUp - blankDown, BufferedImage.TYPE_BYTE_BINARY);
                    for (int j = alledge[k][0] + 1 + blankUp, m = 0; j < alledge[k][1] - 1 - blankDown; j++, m++) {
                        boolean flag = false;
                        for (int i = (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2) - 1 + blankLeft, n = 0; i < alledge[k][3] - 1 - blankRight; i++, n++) {
                            if (i != alledge[0][2] && !flag) {
                                i++;
                                flag = true;
                            }
                            int rgb = image.getRGB(i, j);
                            if (n < width && m < height - blankUp - blankDown) {
                                image_afterseg.setRGB(n, m, rgb);
                            } else {
                                continue;
                            }
                        }
                    }
                    path = path_process + pathRoot;
                    SaveFile(image_afterseg, path, file.getName().replace(".jpg", "") + "_" + index + ".bmp");
                    imgScale(image_afterseg, image_afterseg.getWidth(), image_afterseg.getHeight(), normalizeSize, normalizeSize, index, pathRoot + "_normal");
                    index++;

                } else {
                    if (k == 0 && alledge[k][2] == 1) {
////                        去除上部空白行
//                        int countZero = 0;
//                        int blankUp = 0;
//                        for (int i = alledge[k][0] + 1; i < alledge[k][1] - 1; i++) {
//                            if (countZero == width + 2 || countZero == width + 1) {
//                                blankUp++;
//                                countZero = 0;
//                            } else if (i != alledge[k][0] + 1) {
//                                break;
//                            }
//                            for (int j = (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2) - 1; j < alledge[k][3] - 1; j++) {
//                                if ((image.getRGB(j, i) & 0xFF) == background_color.getGreen()) {
//                                    countZero++;
//                                }
//                            }
//                        }
//
////                        去除下部空白行
//                        int blankDown = 0;
//                        countZero = 0;
//                        for (int i = alledge[k][1] - 1; i > alledge[k][0] + 1; i--) {
//                            if (countZero == width + 2 || countZero == width + 1) {
//                                blankDown++;
//                                countZero = 0;
//                            } else if (i != alledge[k][1] - 1) {
//                                break;
//                            }
//                            for (int j = (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2) - 1; j < alledge[k][3] - 1; j++) {
//                                if ((image.getRGB(j, i) & 0xFF) == background_color.getGreen()) {
//                                    countZero++;
//                                }
//                            }
//                        }
//
////                        去除左边空白行
//                        int blankLeft = 0;
//                        countZero = 0;
//                        for (int i = (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2) - 1; i < alledge[k][3] - 1; i++) {
//                            if (countZero == width + 1 || countZero == width) {
//                                blankLeft++;
//                                countZero = 0;
//                            } else if (i != alledge[k][1] - 1) {
//                                break;
//                            }
//                            for (int j = alledge[k][0] + 1; j < alledge[k][1] - 1; j++) {
//                                if ((image.getRGB(i, j) & 0xFF) == background_color.getGreen()) {
//                                    countZero++;
//                                }
//                            }
//                        }
//
////                      去除右边空白行
//                        int blankRight = 0;
//                        countZero = 0;
//                        for (int i = alledge[k][3] - 1; i > (alledge[k][2] + (alledge[k][3] - alledge[k][2]) / 2) - 1; i--) {
//                            if (countZero == width + 1 || countZero == width) {
//                                blankRight++;
//                                countZero = 0;
//                            } else if (i != alledge[k][1] - 1) {
//                                break;
//                            }
//                            for (int j = alledge[k][0] + 1; j < alledge[k][1] - 1; j++) {
//                                if ((image.getRGB(i, j) & 0xFF) == background_color.getGreen()) {
//                                    countZero++;
//                                }
//                            }
//                        }
                        int[] deleteBlank = DeleteBlank(image,alledge[k][2] - 1,alledge[k][3] - 1,alledge[k][0] + 1,alledge[k][1] - 1);
                        int blankUp = deleteBlank[0];
                        int blankDown =deleteBlank[1];
                        int blankLeft = deleteBlank[2];
                        int blankRight = deleteBlank[3];
                        image_afterseg = new BufferedImage(alledge[k][3] - alledge[k][2] - blankDown - blankUp, alledge[k][1] - alledge[k][0] - 2 - blankLeft - blankRight, BufferedImage.TYPE_BYTE_BINARY);
//                        System.out.println("alledge[" + k + "][0] : " + alledge[k][0]);
//                        System.out.println("alledge[" + k + "][1] : " + alledge[k][1]);
//                        System.out.println("alledge[" + k + "][2] : " + alledge[k][2]);
//                        System.out.println("alledge[" + k + "][3] : " + alledge[k][3]);

                        for (int j = alledge[k][0] + 1 + blankUp, m = 0; j < alledge[k][1] - 1 - blankDown; j++, m++) {
                            boolean flag = false;
                            for (int i = alledge[k][2] - 1 + blankLeft, n = 0; i < alledge[k][3] - 1 - blankRight; i++, n++) {

                                int rgb = image.getRGB(i, j);
                                image_afterseg.setRGB(n, m, rgb);
                            }
                        }
                        path = path_process + pathRoot;
                        SaveFile(image_afterseg, path, file.getName().replace(".jpg", "") + "_" + index + ".bmp");
                        imgScale(image_afterseg, image_afterseg.getWidth(), image_afterseg.getHeight(), normalizeSize, normalizeSize, index, pathRoot + "_normal");
                        index++;
                    } else {
                        int[] deleteBlank = DeleteBlank(image,alledge[k][2],alledge[k][3] - 1,alledge[k][0] + 1,alledge[k][1] - 1);
                        int blankUp = deleteBlank[0];
                        int blankDown =deleteBlank[1];
                        int blankLeft = deleteBlank[2];
                        int blankRight = deleteBlank[3];
                        image_afterseg = new BufferedImage(alledge[k][3] - alledge[k][2] - 2 - blankUp - blankDown, alledge[k][1] - alledge[k][0] - 2 - blankLeft - blankRight, BufferedImage.TYPE_BYTE_BINARY);
//                        System.out.println("alledge[" + k + "][0] : " + alledge[k][0]);
//                        System.out.println("alledge[" + k + "][1] : " + alledge[k][1]);
//                        System.out.println("alledge[" + k + "][2] : " + alledge[k][2]);
//                        System.out.println("alledge[" + k + "][3] : " + alledge[k][3]);

                        for (int j = alledge[k][0] + 1 + blankUp, m = 0; j < alledge[k][1] - 1 - blankDown; j++, m++) {
                            boolean flag = false;
                            for (int i = alledge[k][2] + blankLeft, n = 0; i < alledge[k][3] - 1 - blankRight; i++, n++) {
                                if (i != alledge[0][2] && !flag) {
                                    i++;
                                    flag = true;
                                }
                                int rgb = image.getRGB(i, j);
                                image_afterseg.setRGB(n, m, rgb);
                            }
                        }
                        path = path_process + pathRoot;
                        SaveFile(image_afterseg, path, file.getName().replace(".jpg", "") + "_" + index + ".bmp");
                        imgScale(image_afterseg, image_afterseg.getWidth(), image_afterseg.getHeight(), normalizeSize, normalizeSize, index, pathRoot + "_normal");
                        index++;
                    }
                }
            }
        }
    }

    //    双线性插值
    public void imgScale(BufferedImage image, int srcW, int srcH, int destW, int destH, int index, String path) throws IOException {

        BufferedImage imageDest = new BufferedImage(destW, destH, BufferedImage.TYPE_BYTE_BINARY);
        float rowRatio = ((float) srcH) / ((float) destH);
        float colRatio = ((float) srcW) / ((float) destW);

        for (int col = 0; col < destW; col++) {

            double srcCol = ((float) col) * colRatio;
            double k = Math.floor(srcCol);
            double u = srcCol - k;
            for (int row = 0; row < destH; row++) {

                double srcRow = ((float) row) * rowRatio;
                double j = Math.floor(srcRow);
                double t = srcRow - j;

                double coffiecent1 = (1.0d - t) * (1.0d - u);
                double coffiecent2 = (t) * (1.0d - u);
                double coffiecent3 = t * u;
                double coffiecent4 = (1.0d - t) * u;

                int rgb = (int) (coffiecent1 * (image.getRGB(getClip((int) k, srcW - 1, 0), getClip((int) j, srcH - 1, 0)) & 0xFF) +
                        coffiecent2 * (image.getRGB(getClip((int) k, srcW - 1, 0), getClip((int) j + 1, srcH - 1, 0)) & 0xFF) +
                        coffiecent3 * (image.getRGB(getClip((int) k + 1, srcW - 1, 0), getClip((int) j + 1, srcH - 1, 0)) & 0xFF) +
                        coffiecent4 * (image.getRGB(getClip((int) k + 1, srcW - 1, 0), getClip((int) j, srcH - 1, 0)) & 0xFF));
                Color color = new Color(rgb, rgb, rgb);
                imageDest.setRGB(col, row, color.getRGB());
            }
        }
        SaveFile(imageDest, path_process + path, file.getName().replace(".jpg", "") + "_" + index + "normal.bmp");
    }

    public int getClip(int x, int max, int min) {
        return x > max ? max : x < min ? min : x;
    }

    public void EightPosition(int[][] flag, int x_p, int y_p, int[] edge) {

        //左
        if ((x_p - 1) >= 0 && flag[y_p][x_p - 1] == 1) {
            flag[y_p][x_p - 1] = 0;
            if (x_p - 1 < edge[2])
                edge[2] = x_p - 1;
            EightPosition(flag, x_p - 1, y_p, edge);
        }
        //右
        if ((x_p + 1) < width && flag[y_p][x_p + 1] == 1) {
            flag[y_p][x_p + 1] = 0;
            if (x_p + 1 > edge[3])
                edge[3] = x_p + 1;
            EightPosition(flag, x_p + 1, y_p, edge);
        }
        //上
        if ((y_p - 1) >= 0 && flag[y_p - 1][x_p] == 1) {
            flag[y_p - 1][x_p] = 0;
            if (y_p - 1 < edge[0])
                edge[0] = y_p - 1;
            EightPosition(flag, x_p, y_p - 1, edge);
        }
        //下
        if ((y_p + 1) < height && flag[y_p + 1][x_p] == 1) {
            flag[y_p + 1][x_p] = 0;
            if (y_p + 1 > edge[1])
                edge[1] = y_p + 1;
            EightPosition(flag, x_p, y_p + 1, edge);
        }
        //右上
        if ((x_p + 1) < width && (y_p - 1) >= 0 && flag[y_p - 1][x_p + 1] == 1) {
            flag[y_p - 1][x_p + 1] = 0;
            if (x_p + 1 > edge[3])
                edge[3] = x_p + 1;
            if (y_p - 1 < edge[0])
                edge[0] = y_p - 1;
            EightPosition(flag, x_p + 1, y_p - 1, edge);
        }
        //左上
        if ((x_p - 1) >= 0 && (y_p - 1) >= 0 && flag[y_p - 1][x_p - 1] == 1) {
            flag[y_p - 1][x_p - 1] = 0;
            if (y_p - 1 < edge[0])
                edge[0] = y_p - 1;
            if (x_p - 1 < edge[2])
                edge[2] = x_p - 1;
            EightPosition(flag, x_p - 1, y_p - 1, edge);
        }
        //左下
        if ((x_p - 1) >= 0 && (y_p + 1) < height && flag[y_p + 1][x_p - 1] == 1) {
            flag[y_p + 1][x_p - 1] = 0;
            if (x_p - 1 < edge[2])
                edge[2] = x_p - 1;
            if (y_p + 1 > edge[1])
                edge[1] = y_p + 1;
            EightPosition(flag, x_p - 1, y_p + 1, edge);
        }
        //右下
        if ((x_p + 1) < width && (y_p + 1) < height && flag[y_p + 1][x_p + 1] == 1) {
            flag[y_p + 1][x_p + 1] = 0;
            if (x_p + 1 > edge[3])
                edge[3] = x_p + 1;
            if (y_p + 1 > edge[1])
                edge[1] = y_p + 1;
            EightPosition(flag, x_p + 1, y_p + 1, edge);
        }
    }

    //    Ostu算法
    public int Ostu(BufferedImage image) {
        int[] histData = new int[256];
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                int rgb = image.getRGB(x, y) & 0xFF;
                histData[rgb]++;
            }
        }
        int total = width * height;
        float sum = 0;
        for (int t = 0; t < 256; t++)
            sum += t * histData[t];
        float sumB = 0;
        int wB = 0;
        int wF = 0;
        float varMax = 0;
        int threshold = 0;
        for (int t = 0; t < 256; t++) {
            wB += histData[t];
            if (wB == 0)
                continue;
            wF = total - wB;
            if (wF == 0)
                break;
            sumB += (float) (t * histData[t]);
            float mB = sumB / wB;
            float mF = (sum - sumB) / wF;
            float varBetween = (float) wB * (float) wF * (mB - mF) * (mB - mF);
            if (varBetween > varMax) {
                varMax = varBetween;
                threshold = t;
            }
        }
        return threshold;
    }

    //    保存文件
    public void SaveFile(BufferedImage image, String path, String filename) throws IOException {
        File fileDir = new File(path);
        MakeDir(fileDir);
        File newFile = new File(path + "\\" + filename);
        ImageIO.write(image, "bmp", newFile);
    }

    public static void MakeDir(File file) {
        if (file.getParentFile().exists()) {
            file.mkdir();
        } else {
            MakeDir(file.getParentFile());
            file.mkdir();
        }
    }

    public void PrintPix(BufferedImage image) {
        for (int i = 0; i < image.getHeight(); i++) {
            for (int j = 0; j < image.getWidth(); j++) {
                if ((image.getRGB(j, i) & 0xff) == forge_color.getBlue()) {
                    System.out.print("1,");
                } else {
                    System.out.print("0,");
                }
            }
        }
    }

    public int[] DeleteBlank(BufferedImage image,int startX,int endX,int startY,int endY){

//                    去除上部空白行
        int countZero = 0;
        int blankUp = 0;
        for (int i = startY; i < endY; i++) {
            if (countZero == width + 2 || countZero == width + 1) {
                blankUp++;
                countZero = 0;
            } else if (i!= startY) {
                break;
            }
            for (int j = startX; j < endX; j++) {
                if ((image.getRGB(j, i) & 0xFF) == background_color.getGreen()) {
                    countZero++;
                }
            }
        }

//                    去除下部空白行
        int blankDown = 0;
        countZero = 0;
        for (int i = endY; i > startY; i--) {
            if (countZero == width + 2 || countZero == width + 1) {
                blankDown++;
                countZero = 0;
            } else if (i!= endY) {
                break;
            }
            for (int j = startX; j < endX; j++) {
                if ((image.getRGB(j, i) & 0xFF) == background_color.getGreen()) {
                    countZero++;
                }
            }
        }

//                    去除左边空白行
        int blankLeft = 0;
        countZero = 0;
        for (int i = startX; i < endX; i++) {
            if (countZero == width + 1 || countZero == width) {
                blankLeft++;
                countZero = 0;
            } else if (i!= startX) {
                break;
            }
            for (int j = startY; j < endY; j++) {
                if ((image.getRGB(i, j) & 0xFF) == background_color.getGreen()) {
                    countZero++;
                }
            }
        }

//                    去除右边空白行
        int blankRight = 0;
        countZero = 0;
        for (int i = endX; i > startX; i--) {
            if (countZero == width + 1 || countZero == width) {
                blankRight++;
                countZero = 0;
            } else if (i!= endX) {
                break;
            }
            for (int j = startY; j < endY; j++) {
                if ((image.getRGB(i, j) & 0xFF) == background_color.getGreen()) {
                    countZero++;
                }
            }
        }

        int[] delete = {blankUp,blankDown,blankLeft,blankRight};
        return delete;
    }

    public String MatchTemp(File file){
        File[] filesTemp = new File("resources/Template").listFiles();
        ArrayList<Integer> codeTpl = null;
        ArrayList<Integer> code = null;
        BufferedImage imageTpl = null;
        BufferedImage image = null;
        double maxRate = 0.0;
        double rateTmp = 0.0;
        String recoResult = null;
        for (int i = 0; i < filesTemp.length; i++){
//            if (filesTemp[i].getName().substring(0, 1).equals("8") || filesTemp[i].getName().substring(0, 1).equals("B") || filesTemp[i].getName().substring(0, 1).equals("3")) {
                try {
                    imageTpl = ImageIO.read(filesTemp[i]);
                    image = ImageIO.read(file);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                codeTpl = ReadPix(imageTpl);
                code = ReadPix(image);
                rateTmp = GetRate(code, codeTpl);
                if (rateTmp > maxRate) {
                    maxRate = rateTmp;
                    recoResult = filesTemp[i].getName().substring(0, 1);
                }
                System.out.println("template file name : " + filesTemp[i].getName().substring(0, 1));
//            }
        }
        return recoResult;
    }

    public ArrayList<Integer> ReadPix(BufferedImage image){
        ArrayList<Integer> code = new ArrayList<Integer>();
        for (int i = 0; i < image.getHeight(); i++){
            for (int j = 0; j < image.getWidth(); j++){
                if ((image.getRGB(j,i) & 0xff ) == forge_color.getBlue()){
                    code.add(1);
                } else {
                    code.add(0);
                }
            }
        }
        return code;
    }

    public double GetRate(ArrayList<Integer> code, ArrayList<Integer> codeTpl){

        int countEqual = 0;
        int countOneTpl = 0;
        int countOne = 0;
        for (int i = 0; i < code.size(); i++){
            if (code.get(i) == 1){
                countOne++;
            }
        }
        for (int i = 0; i < codeTpl.size(); i++){
            if (codeTpl.get(i) == 1){
                countOneTpl++;
                if (code.get(i) == codeTpl.get(i)){
                    countEqual++;
                }
            }
        }
        System.out.println("countOne:" + countOne);
        System.out.println("countOneTpl:" + countOneTpl);
        System.out.println("countEqual:" + countEqual);
        System.out.println("rate:" + (double)countEqual/countOneTpl);
        if ( countOne - countOneTpl > 20 ) {
            if ((double) countEqual / countOneTpl < 0.85) {
                return 0;
            }
            return 0;
        }
        return (double)countEqual/countOneTpl;
    }

    public String MatchTempTest(File file,File filetml){
        ArrayList<Integer> codeTpl = null;
        ArrayList<Integer> code = null;
        BufferedImage imageTpl = null;
        BufferedImage image = null;
        double maxRate = 0.0;
        double rateTmp = 0.0;
        String recoResult = null;
        try {
            imageTpl = ImageIO.read(filetml);
            image = ImageIO.read(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        codeTpl = ReadPix(imageTpl);
        code = ReadPix(image);
        rateTmp = GetRate(code, codeTpl);
        if (rateTmp > maxRate) {
            maxRate = rateTmp;
            recoResult = filetml.getName().substring(0, 1);
        }
        System.out.println("template file name : " + filetml.getName().substring(0, 1));
        return recoResult;
    }

}