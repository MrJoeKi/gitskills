package cn.process.image;

import org.junit.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        int[] filename = new int[files.length];
        for (int i = 0 ; i < filename.length; i++){
            filename[i] = Integer.parseInt(files[i].substring(2,files[i].length() - 5));
        }
        Arrays.sort(filename);
        for (int i = 0 ; i < filename.length ; i++) {
            File readfile = new File(originPath + "\\ (" + filename[i] + ").jpg");
            image = ImageIO.read(readfile);
            imageProcess = new ImageProcess(readfile,image);
            System.out.println(readfile.getName());
            BufferedImage image_gray = imageProcess.Gray(image);
            BufferedImage image_binary = imageProcess.Binary(image_gray);
            BufferedImage image_gauss = imageProcess.Gaussian(image_binary);
            BufferedImage image_denoise = imageProcess.Erosion(image_gauss);
            BufferedImage image_seg = imageProcess.Segmentation(image_denoise,"edge_ero");
            imageProcess.ImageTrans(image_seg);
            try {
                int[][] alledge = imageProcess.GetSingleChar(image_seg);
                imageProcess.PrintAfterSeg(image_denoise, alledge,"\\single");
            } catch (Exception e) {
                continue ;
            }
        }
    }

    @Test
    public void testSegmentation() throws Exception {
        for (int i=0;i < files.length;i++){
            System.out.println(files[i]);
        }
        int[] filename = new int[files.length];
        for (int i = 0 ; i < filename.length; i++){
            filename[i] = Integer.parseInt(files[i].substring(2,files[i].length() - 5));
        }
        Arrays.sort(filename);
        for (int i = 0 ; i < filename.length ; i++) {
            File readfile = new File(originPath + "\\ (" + filename[i] + ").jpg");
            System.out.println(readfile.getName());
            image = ImageIO.read(readfile);
            imageProcess = new ImageProcess(readfile,image);
            BufferedImage image_gray = imageProcess.Gray(image);
            BufferedImage image_binary = imageProcess.Binary(image_gray);
            BufferedImage image_erosion = imageProcess.Erosion(image_binary);
            BufferedImage image_denoise = imageProcess.Expand(image_erosion);
//            BufferedImage image_denoise = imageProcess.MedianFiltering(image_binary);
            BufferedImage image_seg = imageProcess.Segmentation(image_denoise,"\\edge_ero");
            imageProcess.ImageTrans(image_seg);
            try {
                int[][] alledge = imageProcess.GetSingleChar(image_seg);
                imageProcess.PrintAfterSeg(image_denoise,alledge,"\\single_ero");
            } catch (Exception e){
                continue ;
            }
        }

        for (int i = 0 ; i < filename.length ; i++) {
            File readfile = new File(originPath + "\\ (" + filename[i] + ").jpg");
            System.out.println(readfile.getName());
            image = ImageIO.read(readfile);
            imageProcess = new ImageProcess(readfile,image);
            BufferedImage image_gray = imageProcess.Gray(image);
            BufferedImage image_binary = imageProcess.Binary(image_gray);
//            BufferedImage image_erosion = imageProcess.Erosion(image_binary);
//            BufferedImage image_denoise = imageProcess.Expand(image_erosion);
            BufferedImage image_denoise = imageProcess.AvrFiltering(image_binary);
            BufferedImage image_seg = imageProcess.Segmentation(image_denoise,"\\edge_avg");
            imageProcess.ImageTrans(image_seg);
            try {
                int[][] alledge = imageProcess.GetSingleChar(image_seg);
                imageProcess.PrintAfterSeg(image_denoise,alledge,"\\single_avg");
            } catch (Exception e){
                continue ;
            }
        }
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
        String testPath = "D:\\JaveDev\\Graduation\\imagehandled\\edge_avg\\ (44)_edgedete.bmp";
        BufferedImage imageTest = ImageIO.read(new File(testPath));
        imageProcess = new ImageProcess(new File(testPath),imageTest);
        int[][] alledge = imageProcess.GetSingleChar(imageTest);
        String imageDe = "D:\\JaveDev\\Graduation\\imagehandled\\avg\\ (44)_avgfilter.bmp";
        BufferedImage imageOri = ImageIO.read(new File(imageDe));
        imageProcess.PrintAfterSeg(imageOri,alledge,"\\single");
    }

    @Test
    public void testMakeDir() throws Exception {
        File file_test = new File("D:\\a\\b\\b\\c");
        ImageProcess.MakeDir(file_test);
//        System.out.println(file_test.getName());
    }

    @Test
    public void testPrintPix() throws Exception {
        String pathAvg = "D:\\JaveDev\\Graduation\\imagehandled\\single_avg_normal\\";
        String pathEro = "D:\\JaveDev\\Graduation\\imagehandled\\single_ero_normal\\";

        File file = new File(pathAvg + " (57)_3normal.bmp");
        BufferedImage image = ImageIO.read(file);
        imageProcess = new ImageProcess(file,image);
        imageProcess.PrintPix(image);
    }

    @Test
    public void testMatchTemp() throws Exception {
        ImageProcess imageProcessTest = new ImageProcess();
        File filetest = new File("D:\\JaveDev\\Graduation\\imagehandled\\single_avg_normal\\ (1006)_5normal.bmp");
        File filetpl = new File("D:\\JaveDev\\Graduation\\resources\\Template\\G (2).bmp");
//        System.out.println(imageProcessTest.MatchTemp(filetest));
        System.out.println(imageProcessTest.MatchTempTest(filetest,filetpl));
    }
}