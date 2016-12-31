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
        BufferedImage image1 = ImageIO.read(new File("D:\\JaveDev\\Graduation\\imagehandled\\gray\\206_14_14_15_36_captcha_120000_cf8d9692-19b3-422c-900e-9a64f5da33ee_gray.bmp"));
        ImageProcess imageProcess1 = new ImageProcess(new File("D:\\JaveDev\\Graduation\\imagehandled\\gray\\206_14_14_15_36_captcha_120000_cf8d9692-19b3-422c-900e-9a64f5da33ee_gray.bmp"),image1);
        imageProcess1.Binary(image1);
        File filete = new File("C:\\Users\\Joe\\Desktop\\images\\长条数字加减_8邻域去噪\\206_14_14_15_36_captcha_120000_cf8d9692-19b3-422c-900e-9a64f5da33ee.jpg");
        File filematch = new File("D:\\JaveDev\\Graduation\\imagehandled\\single_eightdenoise_normal\\206_14_14_15_36_captcha_120000_cf8d9692-19b3-422c-900e-9a64f5da33ee_3normal.bmp");
        System.out.println(filematch.getName().substring(0, filete.getName().replace(".jpg", "").length()).equals(filete.getName().replace(".jpg", "")));
        System.out.println(filematch.getName().substring(0, filete.getName().replace(".jpg", "").length()));
        System.out.println(filete.getName().replace(".jpg", ""));
    }

    @Test
    public void testErosion() throws Exception {
        BufferedImage image_gray = imageProcess.Gray(image);
        BufferedImage image_binary = imageProcess.Binary(image_gray);
        imageProcess.Erosion(image_binary);
    }

    @Test
    public void testExpand() throws Exception {
//        BufferedImage image_gray = imageProcess.Gray(image);
//        BufferedImage image_binary = imageProcess.Binary(image_gray);
//        BufferedImage image_erosion = imageProcess.Erosion(image_binary);
//        BufferedImage image_expand1 = imageProcess.Expand(image_erosion);
        File filetest = new File("D:\\JaveDev\\Graduation\\imagehandled\\binary\\206_14_14_15_39_captcha_120000_e77c9105-9e04-4c5e-9c3d-9d4ecd127e0f_binary.bmp");
        BufferedImage imagetest = ImageIO.read(filetest);
        ImageProcess imageProcesstest = new ImageProcess(filetest,imagetest);
        BufferedImage imageExpand = imageProcesstest.Expand(imagetest);
        imageProcesstest.Erosion(imageExpand);

    }

    @Test
    public void testAvrFiltering() throws Exception {
        BufferedImage image_gray = imageProcess.Gray(image);
        BufferedImage image_binary = imageProcess.Binary(image_gray);
        imageProcess.AvrFiltering(image_binary);
    }

    @Test
    public void testMedianFiltering() throws Exception {
//        BufferedImage image_gray = imageProcess.Gray(image);
//        BufferedImage image_binary = imageProcess.Binary(image_gray);
//        imageProcess.MedianFiltering(image_binary);
        File filetest = new File("D:\\JaveDev\\Graduation\\imagehandled\\binary\\206_14_14_15_39_captcha_120000_e77c9105-9e04-4c5e-9c3d-9d4ecd127e0f_binary.bmp");
        BufferedImage imagetest = ImageIO.read(filetest);
        ImageProcess imageProcesstest = new ImageProcess(filetest,imagetest);
        imageProcesstest.MedianFiltering(imagetest);
    }

    @Test
    public void testYProjection() throws Exception {

        imageProcess_test.YProjection(image_test);
    }

    @Test
    public void testGaussian() throws Exception {
//        int[] filename = new int[files.length];
//        for (int i = 0 ; i < filename.length; i++){
//            filename[i] = Integer.parseInt(files[i].substring(2,files[i].length() - 5));
//        }
//        Arrays.sort(filename);
//        for (int i = 0 ; i < filename.length ; i++) {
//            File readfile = new File(originPath + "\\ (" + filename[i] + ").jpg");
//            image = ImageIO.read(readfile);
//            imageProcess = new ImageProcess(readfile,image);
//            System.out.println(readfile.getName());
//            BufferedImage image_gray = imageProcess.Gray(image);
//            BufferedImage image_binary = imageProcess.Binary(image_gray);
//            BufferedImage image_gauss = imageProcess.Gaussian(image_binary);
//            BufferedImage image_denoise = imageProcess.Erosion(image_gauss);
//            BufferedImage image_seg = imageProcess.Segmentation(image_denoise,"edge_ero");
//            imageProcess.ImageTrans(image_seg);
//            try {
//                int[][] alledge = imageProcess.GetSingleChar(image_seg);
//                imageProcess.PrintAfterSeg(image_denoise, alledge,"\\single");
//            } catch (Exception e) {
//                continue ;
//            }
//        }
        File filetest = new File("D:\\JaveDev\\Graduation\\imagehandled\\binary\\206_14_14_15_39_captcha_120000_e77c9105-9e04-4c5e-9c3d-9d4ecd127e0f_binary.bmp");
        BufferedImage imagetest = ImageIO.read(filetest);
        ImageProcess imageProcesstest = new ImageProcess(filetest,imagetest);
        imageProcesstest.Gaussian(imagetest);
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
            imageProcess.ImageTransBlackWhite(image_seg);
            imageProcess.setCharNum(6);
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
            imageProcess.ImageTransBlackWhite(image_seg);
            imageProcess.setCharNum(6);
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
        imageProcess.setCharNum(6);
        imageProcess.GetSingleChar(image);
    }

    @Test
    public void testPrintAfterSeg() throws Exception {
        String testPath = "D:\\JaveDev\\Graduation\\imagehandled\\edge_eight\\ (101)_edgedete.bmp";
        BufferedImage imageTest = ImageIO.read(new File(testPath));
        imageProcess = new ImageProcess(new File(testPath),imageTest);
        imageProcess.setCharNum(6);
        int[][] alledge = imageProcess.GetSingleChar(imageTest);
        for (int i = 0 ; i < alledge.length; i++){

            System.out.println("area[" + i + " = " + ((alledge[i][1]-alledge[i][0])*(alledge[i][3]-alledge[i][2])));

        }
//        String imageDe = "D:\\JaveDev\\Graduation\\imagehandled\\avg\\ (44)_avgfilter.bmp";
//        BufferedImage imageOri = ImageIO.read(new File(imageDe));
//        imageProcess.PrintAfterSeg(imageOri,alledge,"\\single");
    }

    @Test
    public void testMakeDir() throws Exception {
        ImageProcess imageProcess1 = new ImageProcess();
        String path = imageProcess1.path_process + "\\binary\\" + "a_binary.bmp";
        System.out.println((new File(path)).exists());
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
        File filetpl = new File("D:\\JaveDev\\Graduation\\resources\\Template\\G_2.bmp");
//        System.out.println(imageProcessTest.MatchTemp(filetest));
//        System.out.println(imageProcessTest.MatchTempTest(filetest,filetpl));
        System.out.println(filetpl.getName().replaceAll(".bmp","").split("_")[0]);
        String test = "";
        System.out.println(test.replaceFirst("\\?", ""));
        System.out.println(test.indexOf("?"));
        String[] results = test.replaceAll("={1,2}","").split("\\+");
        System.out.println(results[0]);
        System.out.println(results[1]);
        System.out.println(imageProcessTest.PreMatch(test));
    }

    @Test
    public void testEightDenoise() throws Exception {
        File filetest = new File("D:\\JaveDev\\Graduation\\imagehandled\\binary\\206_14_18_04_captcha_120000_b694d17f-bb49-4b15-b7f9-9ca5740b7f7b_binary.bmp");
        BufferedImage imagetest = ImageIO.read(filetest);
        ImageProcess imageProcesstest = new ImageProcess(filetest,imagetest);
        BufferedImage imageDenoise = imageProcesstest.EightDenoise(imagetest, 2);
        BufferedImage imageDenoise1 = imageProcesstest.EightDenoise(imageDenoise, 2);
        BufferedImage imageDenoise2 = imageProcesstest.EightDenoise(imageDenoise1, 2);
        BufferedImage imageDenoise3 = imageProcesstest.EightDenoise(imageDenoise2,2);
//        BufferedImage imgeEdge = imageProcesstest.Segmentation(imageDenoise,"\\EightDe");
//        imageProcesstest.charNum = 6;
//        System.out.println(imageProcesstest.charNum);
//        try {
//            int[][] alledge = imageProcesstest.GetSingleChar(imgeEdge,6);
//            imageProcesstest.PrintAfterSeg(imageDenoise,alledge,"\\EightDeSingle");
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }

    @Test
    public void testGetByProjection() throws Exception {
//        File filetest = new File("D:\\JaveDev\\Graduation\\imagehandled\\eightdenoise\\ (1002)_eightdenoise.bmp");
//        BufferedImage image1 = ImageIO.read(filetest);
//        ImageProcess imageProcessTest = new ImageProcess(filetest,image1);
//        imageProcessTest.setCharNum(5);
//        int[] horizon = imageProcessTest.YProjection(image1);
//        int[] vertical = imageProcessTest.XProjection(image1);
//        int[][] alledge = imageProcessTest.GetByProjection(horizon,vertical);
//        imageProcessTest.PrintAfterSeg(image1,alledge,"test");
        File filetestNormal = new File("D:\\JaveDev\\Graduation\\imagehandledtest\\ (1001)_eightdenoise.bmp_3.bmp");
        BufferedImage imageTestNormal = ImageIO.read(filetestNormal);
        ImageProcess imageProcess1 = new ImageProcess(filetestNormal,imageTestNormal);
        imageProcess1.imgScale(imageTestNormal,imageTestNormal.getWidth(),imageTestNormal.getHeight(),16,16,0,"testNormal");
    }
}