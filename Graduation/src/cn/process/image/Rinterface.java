package cn.process.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Joe on 2016/11/15.
 */
public class Rinterface {
    private JButton open;
    private JButton gray;
    private JButton binary;
    private JButton erosion;
    private JButton expand;
    private JButton getedge;
    private JButton ypro;
    private JButton segmention;
    private JPanel MainPanel;
    private JPanel ButtonPanel;
    private JPanel imageOriginal;
    private JPanel imageProcessPanel;
    private JLabel imageDis;
    private JLabel imagePro;
    private JLabel Single1;
    private JLabel Single2;
    private JLabel Single3;
    private JLabel Single4;
    private JLabel Single5;
    private JLabel Single6;
    private JButton clearPanel;
    private JPanel segDisplay;
    private BufferedImage image = null;
    private ImageProcess imageProcess = null;
    private File file = null;

    public Rinterface() {
        open.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.showOpenDialog(MainPanel);
                file = jFileChooser.getSelectedFile();
                String path = "D:\\JaveDev\\Graduation\\imagehandled\\normalization";
                File fileDir = new File(path);
                String[] fileArray = fileDir.list();
                for (int i = 0 ; i < fileArray.length; i++) {
                    File temp = new File(path + "\\" + fileArray[i]);
                    temp.delete();
                }
                Single1.setIcon(null);
                Single2.setIcon(null);
                Single3.setIcon(null);
                Single4.setIcon(null);
                Single5.setIcon(null);
                Single6.setIcon(null);
                if ( file == null){
                    imageDis.setIcon(null);
                } else {
                    if (file.getName().matches(".+?\\.jpg")) {
                        try {
                            image = ImageIO.read(file);
                            imageProcess = new ImageProcess(file, image);
                            BufferedImage imageGray = imageProcess.Gray(image);
                            BufferedImage imageBinary = imageProcess.Binary(imageGray);
                            BufferedImage imageErosion = imageProcess.Erosion(imageBinary);
                            BufferedImage imageExpand = imageProcess.Expand(imageErosion);
                            BufferedImage imageEdge = imageProcess.Segmentation(imageExpand);
                            int[][] alledge = imageProcess.GetEdge(imageEdge);
                            imageProcess.PrintAfterSeg(imageExpand, alledge);
                            imageProcess.YProjection(imageExpand);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    } else {
                        JOptionPane.showMessageDialog(MainPanel, "请输入以\".jpg\"为拓展名的图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                    }
                    ImageIcon icon = new ImageIcon(image);
                    icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                    imageDis.setIcon(icon);
                    imageDis.setHorizontalAlignment(SwingConstants.CENTER);
                }
            }
        });
        gray.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file == null){
                    imagePro.setIcon(null);
                    JOptionPane.showMessageDialog(MainPanel, "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    String path = imageProcess.path_process + "\\gray\\" + file.getName().replace(".jpg","") + "_gray.bmp";
                    BufferedImage imageGray = null;
                    try {
                        imageGray = ImageIO.read(new File(path));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageGray);
                    imagePro.setIcon(icon);
                }
            }
        });
        binary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file == null){
                    imagePro.setIcon(null);
                    JOptionPane.showMessageDialog(MainPanel, "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    String path = imageProcess.path_process + "\\binary\\" + file.getName().replace(".jpg","") + "_binary.bmp";
                    BufferedImage imageBinary = null;
                    try {
                        imageBinary = ImageIO.read(new File(path));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageBinary);
                    imagePro.setIcon(icon);
                }
            }
        });
        erosion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file == null){
                    imagePro.setIcon(null);
                    JOptionPane.showMessageDialog(MainPanel, "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    String path = imageProcess.path_process + "\\erosion\\" + file.getName().replace(".jpg","") + "_erosion.bmp";
                    BufferedImage imageErosion = null;
                    try {
                        imageErosion = ImageIO.read(new File(path));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageErosion);
                    imagePro.setIcon(icon);
                }
            }
        });
        expand.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file == null) {
                    imagePro.setIcon(null);
                    JOptionPane.showMessageDialog(MainPanel, "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    String path = imageProcess.path_process + "\\expand\\" + file.getName().replace(".jpg", "") + "_expand.bmp";
                    BufferedImage imageExpand = null;
                    try {
                        imageExpand = ImageIO.read(new File(path));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageExpand);
                    imagePro.setIcon(icon);
                }
            }
        });
        getedge.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file == null) {
                    imagePro.setIcon(null);
                    JOptionPane.showMessageDialog(MainPanel, "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    String path = imageProcess.path_process + "\\getedge\\" + file.getName().replace(".jpg", "") + "_edgedete.bmp";
                    BufferedImage imageGetedge = null;
                    try {
                        imageGetedge = ImageIO.read(new File(path));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageGetedge);
                    imagePro.setIcon(icon);
                }
            }
        });
        segmention.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JLabel[] allSeg = {Single1,Single2,Single3,Single4,Single5,Single6};

                if (file == null) {
                    Single1.setIcon(null);
                    Single2.setIcon(null);
                    Single3.setIcon(null);
                    Single4.setIcon(null);
                    Single5.setIcon(null);
                    Single6.setIcon(null);
                    JOptionPane.showMessageDialog(MainPanel, "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                    String path = "D:\\JaveDev\\Graduation\\imagehandled\\normalization";
                    File fileDir = new File(path);
                    String[] fileArray = fileDir.list();
                    for (int i = 0 ; i < fileArray.length; i++) {
                        File temp = new File(path + "\\" + fileArray[i]);
                        temp.delete();
                    }
                } else {
                    String path = imageProcess.path_process + "\\normalization";
                    File fileDir = new File(path);
                    String[] fileArray = fileDir.list();
                    BufferedImage imageSingle = null;
                    for (int i = 0 ; i < fileArray.length; i++){
                        try {
                            imageSingle = ImageIO.read(new File(path + "\\" + fileArray[i]));
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                        ImageIcon icon = new ImageIcon(imageSingle);
                        allSeg[i].setIcon(icon);
                    }
                }
            }
        });
        ypro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (file == null) {
                    imagePro.setIcon(null);
                    JOptionPane.showMessageDialog(MainPanel, "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                } else {
                    String path = imageProcess.path_process + "\\Ypro\\" + file.getName().replace(".jpg", "") + "_yprojection.bmp";
                    BufferedImage imageYpro = null;
                    try {
                        imageYpro = ImageIO.read(new File(path));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageYpro);
                    imagePro.setIcon(icon);
                }
            }
        });
        clearPanel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                imageDis.setIcon(null);
                imagePro.setIcon(null);
                Single1.setIcon(null);
                Single2.setIcon(null);
                Single3.setIcon(null);
                Single4.setIcon(null);
                Single5.setIcon(null);
                Single6.setIcon(null);
            }
        });
        imageOriginal.setVisible(true);
        imageOriginal.setEnabled(true);
        imageOriginal.setSize(300,120);
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("验证码识别");
//        frame.setLayout(null);
//        Rinterface rinterface = new Rinterface();
//        frame.setContentPane(rinterface.MainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//        frame.setVisible(true);
//        frame.setSize(800,700);
//    }

}
