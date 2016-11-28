package cn.process.image;

import org.junit.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

import static org.junit.Assert.*;

public class ImageProcessTest {

    private ImageProcess imageProcess = null;
    private ImageProcess imageProcess_test = null;
    private BufferedImage image = null;
    private BufferedImage image_test = null;
    String[] files = null;
    String originPath = "C:\\Users\\Joe\\Desktop\\Image\\all_alphabets_images\\images";
    @Before
    public void setUp() throws Exception {
//        File file = new File(System.getProperty("user.dir") + "\\imagehandled\\expand\\1_expand.bmp");
//        File file_test = new File(System.getProperty("user.dir") + "\\imagehandled\\imagetrans\\1_555.bmp");

        File file = new File(originPath);
        files = file.list();

//        image_test = ImageIO.read(file_test);
//        imageProcess = new ImageProcess(file,image);
//        imageProcess_test = new ImageProcess(file_test,image_test);

    }

    @Test
    public void testGray() throws Exception {
        imageProcess.Gray(image);
    }

    @Test
    public void testBinary() throws Exception {
        BufferedImage image_gray = imageProcess.Gray(image);
        imageProcess.Binary(image_gray);
    }

    @Test
    public void testErosion() throws Exception {
        BufferedImage image_gray = imageProcess.Gray(image);
        BufferedImage image_binary = imageProcess.Binary(image_gray);
        imageProcess.Erosion(image_binary);
    }

    @Test
    public void testExpand() throws Exception {
        BufferedImage image_gray = imageProcess.Gray(image);
        BufferedImage image_binary = imageProcess.Binary(image_gray);
        BufferedImage image_erosion = imageProcess.Erosion(image_binary);
        BufferedImage image_expand1 = imageProcess.Expand(image_erosion);

    }

    @Test
    public void testAvrFiltering() throws Exception {
        BufferedImage image_gray = imageProcess.Gray(image);
        BufferedImage image_binary = imageProcess.Binary(image_gray);
        imageProcess.AvrFiltering(image_binary);
    }

    @Test
    public void testMedianFiltering() throws Exception {
        BufferedImage image_gray = imageProcess.Gray(image);
        BufferedImage image_binary = imageProcess.Binary(image_gray);
        imageProcess.MedianFiltering(image_binary);
    }

    @Test
    public void testYProjection() throws Exception {

        imageProcess_test.YProjection(image_test);
    }

    @Test
    public void testGaussian() throws Exception {
        BufferedImage image_gray = imageProcess.Gray(image);
        BufferedImage image_binary = imageProcess.Binary(image_gray);
        imageProcess.Gaussian(image_binary);
    }

    @Test
    public void testSegmentation() throws Exception {
//        for (String i : files){
//            System.out.println(i);
//        }
//        for (int i = 0 ; i < files.length/3 ; i++) {
//            System.out.println(files[i]);
            File readfile = new File(originPath + "\\" + " (1012).jpg");
            image = ImageIO.read(readfile);
            imageProcess = new ImageProcess(readfile,image);
            BufferedImage image_gray = imageProcess.Gray(image);
            BufferedImage image_binary = imageProcess.Binary(image_gray);
            BufferedImage image_erosion = imageProcess.Erosion(image_binary);
            BufferedImage image_denoise = imageProcess.Expand(image_erosion);
            BufferedImage image_seg = imageProcess.Segmentation(image_denoise);
            imageProcess.ImageTrans(image_seg);
            int[][] alledge = imageProcess.GetSingleChar(image_seg);
            imageProcess.PrintAfterSeg(image_denoise,alledge);
//        }
    }

    @Test
    public void testEightPosition() throws Exception {
//        BufferedImage image_gray = imageProcess.Gray(image);
//        BufferedImage image_binary = imageProcess.Binary(image_gray);
//        BufferedImage image_erosion = imageProcess.Erosion(image_binary);
//        BufferedImage image_denoise  = imageProcess.Expand(image_erosion);
        imageProcess.GetSingleChar(image);
    }

    @Test
    public void testPrintAfterSeg() throws Exception {
        int[][] alledge = imageProcess.GetSingleChar(image_test);
        imageProcess.PrintAfterSeg(image,alledge);
    }
}