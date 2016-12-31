package cn.process.image;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by Joe on 2016/11/18.
 */
public class RecoInterface extends JFrame {


    public JLabel imageDis = new JLabel();
    public JLabel imagePro = new JLabel();
    public JLabel Single1 = new JLabel();
    public JLabel Single2 = new JLabel();
    public JLabel Single3 = new JLabel();
    public JLabel Single4 = new JLabel();
    public JLabel Single5 = new JLabel();
    public JLabel Single6 = new JLabel();
    public JLabel filePath = new JLabel();
    public JTextField textReco = new JTextField();
    public JTextField charNumInput = new JTextField();
    public JTextField binaryThreInput = new JTextField();
    public BufferedImage imageOri = null;
    public BufferedImage image = null;
    public BufferedImage imageDenoise = null;
    public ImageProcess imageProcess = null;
    public File file = null;
    public File[] files = null;
    public int indexFiles = 0;
    public int charNum = 0;
    public int MaxNearPoints = 2;
    public int binaryThre = 0;


    private JButton OpenButton() {
        JButton open = new JButton();
        open.setText("���ļ�");
        open.setSize(80, 40);
        open.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       JFileChooser jFileChooser = new JFileChooser();
                                       jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
//                                       ɾ��֮ǰ���д�������ļ���
//                                       ImageProcess imageDelete = new ImageProcess();
//                                       imageDelete.DeleteFiles(new File(imageDelete.path_process));

                                       jFileChooser.showOpenDialog(getContentPane());
                                       file = jFileChooser.getSelectedFile();

                                       Single1.setIcon(null);
                                       Single2.setIcon(null);
                                       Single3.setIcon(null);
                                       Single4.setIcon(null);
                                       Single5.setIcon(null);
                                       Single6.setIcon(null);
                                       filePath.setText(null);

                                       charNumInput.setText("0");
                                       binaryThreInput.setText("0");
                                       textReco.setText("");
                                       charNum = 0;
                                       binaryThre = 0;
                                       imageDenoise = null;
                                       indexFiles = 0;

                                       if (file == null) {
                                           imageDis.setIcon(null);
                                           imagePro.setIcon(null);
                                           JOptionPane.showMessageDialog(getContentPane(), "���ȴ��ļ���", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                       } else {
                                           if (file.getName().matches(".+?\\.jpg")) {
                                               try {
                                                   files = null;
                                                   image = ImageIO.read(file);
                                                   imageOri = image;
                                                   imageProcess = new ImageProcess(file, image);
                                                   BufferedImage imageGray = imageProcess.Gray(image);
                                                   image = imageGray;
                                                   BufferedImage imageBinary = imageProcess.Binary(imageGray);
                                                   BufferedImage imageErosion = imageProcess.Erosion(imageBinary);
                                                   BufferedImage imageExpand = imageProcess.Expand(imageErosion);
                                                   BufferedImage imageEdge = imageProcess.Segmentation(imageExpand, "\\edge_ero");
//                                                   int[][] alledge = imageProcess.GetSingleChar(imageEdge, imageProcess.charNum);
//                                                   imageProcess.PrintAfterSeg(imageExpand, alledge, "\\single_ero");
//                                                   imageProcess.YProjection(imageExpand);

                                                   BufferedImage imageAvgFilter = imageProcess.AvrFiltering(imageBinary);
                                                   BufferedImage imageEdgeAvg = imageProcess.Segmentation(imageAvgFilter, "\\edge_avg");
//                                                   int[][] alledgeAvg = imageProcess.GetSingleChar(imageEdgeAvg, imageProcess.charNum);
//                                                   imageProcess.PrintAfterSeg(imageAvgFilter, alledgeAvg, "\\single_avg");
//                                                   imageProcess.YProjection(imageAvgFilter);
//
                                                   BufferedImage imageEightDenoise = imageProcess.EightDenoise(imageBinary, MaxNearPoints);
                                                   BufferedImage imageEightEdge = imageProcess.Segmentation(imageEightDenoise, "\\edge_eight");
//                                                   int[][] alledgeEight = imageProcess.GetSingleChar(imageEightEdge, imageProcess.charNum);
//                                                   imageProcess.PrintAfterSeg(imageEightDenoise, alledgeEight, "\\single_eight");
//                                                   imageProcess.YProjection(imageEightDenoise);

                                                   BufferedImage imageMed = imageProcess.MedianFiltering(imageBinary);
                                                   BufferedImage imageMedEdge = imageProcess.Segmentation(imageMed, "\\edge_med");

                                                   ImageIcon icon_pro = new ImageIcon(imageGray);
                                                   icon_pro.setImage(icon_pro.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                                   imagePro.setIcon(icon_pro);
                                               } catch (IOException e1) {
                                                   e1.printStackTrace();
                                               }
                                           } else {
                                               JOptionPane.showMessageDialog(getContentPane(), "��������\".jpg\"��չ����ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                           }
                                           ImageIcon icon = new ImageIcon(imageOri);
                                           icon.setImage(icon.getImage().getScaledInstance(imageOri.getWidth(), imageOri.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                           imageDis.setIcon(icon);
                                           filePath.setText(file.getAbsolutePath());
                                       }
                                   }
                               }

        );
        return open;
    }

    private JButton OpenFilesButton() {
        JButton openFiles = new JButton();
        openFiles.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            JFileChooser jFileChooser = new JFileChooser();
                                            jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//                                            ɾ��֮ǰ���д�������ļ���
//                                            ImageProcess imageDelete = new ImageProcess();
//                                            imageDelete.DeleteFiles(new File(imageDelete.path_process));

                                            jFileChooser.showOpenDialog(getContentPane());
                                            File directory = jFileChooser.getSelectedFile();

                                            charNumInput.setText("0");
                                            binaryThreInput.setText("0");
                                            charNum = 0;
                                            binaryThre = 0;
                                            textReco.setText("");
                                            imageDenoise = null;

                                            String direPath = null;
                                            try {
                                                direPath = directory.getAbsolutePath();
                                                files = new File(direPath).listFiles();
                                            } catch (Exception e1) {
                                                if (e1.toString().equals("java.lang.NullPointerException")){
                                                    JOptionPane.showMessageDialog(getContentPane(), "���ȴ��ļ��У�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                                }
                                            }

                                            file = null;
                                            indexFiles = 0;

                                            Single1.setIcon(null);
                                            Single2.setIcon(null);
                                            Single3.setIcon(null);
                                            Single4.setIcon(null);
                                            Single5.setIcon(null);
                                            Single6.setIcon(null);
                                            filePath.setText(null);
                                            if (files == null) {
                                                imageDis.setIcon(null);
                                                imagePro.setIcon(null);
                                            } else {
                                                if (files[indexFiles].getName().matches(".+?\\.jpg")) {
                                                    try {
                                                        image = ImageIO.read(files[indexFiles]);
                                                        imageOri = image;
                                                        imageProcess = new ImageProcess(files[indexFiles], image);
                                                        BufferedImage imageGray = imageProcess.Gray(image);
                                                        image = imageGray;
                                                        BufferedImage imageBinary = imageProcess.Binary(imageGray);
                                                        BufferedImage imageErosion = imageProcess.Erosion(imageBinary);
                                                        BufferedImage imageExpand = imageProcess.Expand(imageErosion);
                                                        imageProcess.Segmentation(imageExpand, "\\edge_ero");
//                                                        int[][] alledge = imageProcess.GetSingleChar(imageEdge, imageProcess.charNum);
//                                                        imageProcess.PrintAfterSeg(imageExpand, alledge, "\\single_ero");
//                                                        imageProcess.YProjection(imageExpand);

                                                        BufferedImage imageAvgFilter = imageProcess.AvrFiltering(imageBinary);
                                                        imageProcess.Segmentation(imageAvgFilter, "\\edge_avg");
//                                                        int[][] alledgeAvg = imageProcess.GetSingleChar(imageEdgeAvg, imageProcess.charNum);
//                                                        imageProcess.PrintAfterSeg(imageAvgFilter, alledgeAvg, "\\single_avg");
//                                                        imageProcess.YProjection(imageAvgFilter);

                                                        BufferedImage imageEightDenoise = imageProcess.EightDenoise(imageBinary, MaxNearPoints);
                                                        imageProcess.Segmentation(imageEightDenoise, "\\edge_eight");
//                                                        int[][] alledgeEight = imageProcess.GetSingleChar(imageEightEdge, imageProcess.charNum);
//                                                        imageProcess.PrintAfterSeg(imageEightDenoise, alledgeEight, "\\single_eight");
//                                                        imageProcess.YProjection(imageEightDenoise);

                                                        BufferedImage imageMed = imageProcess.MedianFiltering(imageBinary);
                                                        imageProcess.Segmentation(imageMed, "\\edge_med");

                                                        ImageIcon icon_pro = new ImageIcon(imageGray);
                                                        icon_pro.setImage(icon_pro.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                                        imagePro.setIcon(icon_pro);
                                                    } catch (IOException e1) {
                                                        e1.printStackTrace();
                                                    }
                                                } else {
                                                    JOptionPane.showMessageDialog(getContentPane(), "��������\".jpg\"��չ����ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                                }
                                                ImageIcon icon = new ImageIcon(imageOri);
                                                icon.setImage(icon.getImage().getScaledInstance(imageOri.getWidth(), imageOri.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                                imageDis.setIcon(icon);
                                                filePath.setText(files[indexFiles].getAbsolutePath());
                                            }
                                        }
                                    }

        );
        return openFiles;
    }

    private JButton NextFilesButton() {
        JButton openFiles = new JButton();
        openFiles.setText("��һ��");
        openFiles.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (files == null) {
                                                JOptionPane.showMessageDialog(getContentPane(), "���ȴ��ļ���", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                            } else {
                                                if (indexFiles >= files.length - 1) {
                                                    JOptionPane.showMessageDialog(getContentPane(), "�Ѿ������һ���ļ���", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                                } else {
                                                    imagePro.setIcon(null);
                                                    textReco.setText("");
                                                    indexFiles++;

                                                    Single1.setIcon(null);
                                                    Single2.setIcon(null);
                                                    Single3.setIcon(null);
                                                    Single4.setIcon(null);
                                                    Single5.setIcon(null);
                                                    Single6.setIcon(null);
                                                    filePath.setText(null);
                                                    imageDenoise = null;

                                                    if (files[indexFiles] == null) {
                                                        imageDis.setIcon(null);
                                                        imagePro.setIcon(null);
                                                    } else {
                                                        if (files[indexFiles].getName().matches(".+?\\.jpg")) {
                                                            try {
                                                                image = ImageIO.read(files[indexFiles]);
                                                                imageOri = image;
                                                                imageProcess = new ImageProcess(files[indexFiles], image);
                                                                BufferedImage imageGray = imageProcess.Gray(image);
                                                                image = imageGray;
                                                                BufferedImage imageBinary = null;
                                                                if (binaryThre != 0){
                                                                    imageBinary = imageProcess.BinaryWithParam(imageGray,binaryThre);
                                                                } else {
                                                                    imageBinary = imageProcess.Binary(imageGray);
                                                                }
                                                                BufferedImage imageErosion = imageProcess.Erosion(imageBinary);
                                                                BufferedImage imageExpand = imageProcess.Expand(imageErosion);
                                                                imageProcess.Segmentation(imageExpand, "\\edge_ero");
//                                                                int[][] alledge = imageProcess.GetSingleChar(imageEdge, imageProcess.charNum);
//                                                                imageProcess.PrintAfterSeg(imageExpand, alledge, "\\single_ero");
//                                                                imageProcess.YProjection(imageExpand);

                                                                BufferedImage imageAvgFilter = imageProcess.AvrFiltering(imageBinary);
                                                                imageProcess.Segmentation(imageAvgFilter, "\\edge_avg");
//                                                                int[][] alledgeAvg = imageProcess.GetSingleChar(imageEdgeAvg, imageProcess.charNum);
//                                                                imageProcess.PrintAfterSeg(imageAvgFilter, alledgeAvg, "\\single_avg");
//                                                                imageProcess.YProjection(imageAvgFilter);

                                                                BufferedImage imageEightDenoise = imageProcess.EightDenoise(imageBinary, MaxNearPoints);
                                                                imageProcess.Segmentation(imageEightDenoise, "\\edge_eight");
//                                                                int[][] alledgeEight = imageProcess.GetSingleChar(imageEightEdge, imageProcess.charNum);
//                                                                imageProcess.PrintAfterSeg(imageEightDenoise, alledgeEight, "\\single_eight");
//                                                                imageProcess.YProjection(imageEightDenoise);

                                                                BufferedImage imageMed = imageProcess.MedianFiltering(imageBinary);
                                                                imageProcess.Segmentation(imageMed, "\\edge_med");

                                                                ImageIcon icon_pro = new ImageIcon(imageGray);
                                                                icon_pro.setImage(icon_pro.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                                                imagePro.setIcon(icon_pro);
                                                            } catch (IOException e1) {
                                                                e1.printStackTrace();
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(getContentPane(), "��������\".jpg\"��չ����ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                                        }
                                                        ImageIcon icon = new ImageIcon(imageOri);
                                                        icon.setImage(icon.getImage().getScaledInstance(imageOri.getWidth(), imageOri.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                                        imageDis.setIcon(icon);
                                                        filePath.setText(files[indexFiles].getAbsolutePath());
                                                    }
                                                }
                                            }
                                        }
                                    }

        );
        return openFiles;
    }

    private JButton LastFilesButton() {
        JButton openFiles = new JButton();
        openFiles.setText("��һ��");
        openFiles.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (files == null) {
                                                JOptionPane.showMessageDialog(getContentPane(), "���ȴ��ļ���", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                            } else {
                                                if (indexFiles < 1) {
                                                    JOptionPane.showMessageDialog(getContentPane(), "�Ѿ��ǵ�һ���ļ���", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                                } else {
                                                    imagePro.setIcon(null);
                                                    textReco.setText("");
                                                    indexFiles--;

                                                    Single1.setIcon(null);
                                                    Single2.setIcon(null);
                                                    Single3.setIcon(null);
                                                    Single4.setIcon(null);
                                                    Single5.setIcon(null);
                                                    Single6.setIcon(null);
                                                    filePath.setText(null);
                                                    imageDenoise = null;

                                                    if (files[indexFiles] == null) {
                                                        imageDis.setIcon(null);
                                                        imagePro.setIcon(null);
                                                    } else {
                                                        if (files[indexFiles].getName().matches(".+?\\.jpg")) {
                                                            try {
                                                                image = ImageIO.read(files[indexFiles]);
                                                                imageOri = image;
                                                                imageProcess = new ImageProcess(files[indexFiles], image);
                                                                BufferedImage imageGray = imageProcess.Gray(image);
                                                                image = imageGray;
                                                                BufferedImage imageBinary = null;
                                                                if (binaryThre != 0){
                                                                    imageBinary = imageProcess.BinaryWithParam(imageGray,binaryThre);
                                                                } else {
                                                                    imageBinary = imageProcess.Binary(imageGray);
                                                                }
                                                                BufferedImage imageErosion = imageProcess.Erosion(imageBinary);
                                                                BufferedImage imageExpand = imageProcess.Expand(imageErosion);
                                                                imageProcess.Segmentation(imageExpand, "\\edge_ero");
//                                                                int[][] alledge = imageProcess.GetSingleChar(imageEdge, imageProcess.charNum);
//                                                                imageProcess.PrintAfterSeg(imageExpand, alledge, "\\single_ero");
//                                                                imageProcess.YProjection(imageExpand);
//
                                                                BufferedImage imageAvgFilter = imageProcess.AvrFiltering(imageBinary);
                                                                imageProcess.Segmentation(imageAvgFilter, "\\edge_avg");
//                                                                int[][] alledgeAvg = imageProcess.GetSingleChar(imageEdgeAvg, imageProcess.charNum);
//                                                                imageProcess.PrintAfterSeg(imageAvgFilter, alledgeAvg, "\\single_avg");
//                                                                imageProcess.YProjection(imageAvgFilter);

                                                                BufferedImage imageEightDenoise = imageProcess.EightDenoise(imageBinary, MaxNearPoints);
                                                                imageProcess.Segmentation(imageEightDenoise, "\\edge_eight");
//                                                                int[][] alledgeEight = imageProcess.GetSingleChar(imageEightEdge, imageProcess.charNum);
//                                                                imageProcess.PrintAfterSeg(imageEightDenoise, alledgeEight, "\\single_eight");
//                                                                imageProcess.YProjection(imageEightDenoise);

                                                                BufferedImage imageMed = imageProcess.MedianFiltering(imageBinary);
                                                                imageProcess.Segmentation(imageMed, "\\edge_med");

                                                                ImageIcon icon_pro = new ImageIcon(imageGray);
                                                                icon_pro.setImage(icon_pro.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                                                imagePro.setIcon(icon_pro);
                                                            } catch (IOException e1) {
                                                                e1.printStackTrace();
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(getContentPane(), "��������\".jpg\"��չ����ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                                        }
                                                        ImageIcon icon = new ImageIcon(imageOri);
                                                        icon.setImage(icon.getImage().getScaledInstance(imageOri.getWidth(), imageOri.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                                        imageDis.setIcon(icon);
                                                        filePath.setText(files[indexFiles].getAbsolutePath());
                                                    }
                                                }
                                            }
                                        }
                                    }

        );
        return openFiles;
    }

    private JButton GrayButton() {
        JButton gray = new JButton();
        gray.setText("�ҶȻ�");
        gray.setSize(80, 40);
        gray.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       if (file == null && files == null) {
                                           imagePro.setIcon(null);
                                           JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                       } else {
                                           if (files != null) {
                                               String path = imageProcess.path_process + "\\gray\\" + files[indexFiles].getName().replace(".jpg", "") + "_gray.bmp";
                                               BufferedImage imageGray = null;
                                               try {
                                                   imageGray = ImageIO.read(new File(path));
                                               } catch (IOException e1) {
                                                   e1.printStackTrace();
                                               }
                                               ImageIcon icon = new ImageIcon(imageGray);
                                               imagePro.setIcon(icon);
                                           } else {
                                               String path = imageProcess.path_process + "\\gray\\" + file.getName().replace(".jpg", "") + "_gray.bmp";
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
                                   }
                               }

        );
        return gray;
    }

    private JButton BinaryButton() {
        JButton binary = new JButton();
        binary.setText("��ֵ��");
        binary.setSize(80, 40);
        binary.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent e) {
                                         if (file == null && files == null) {
                                             imagePro.setIcon(null);
                                             JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                         } else {
                                             if (files != null) {
                                                 if (binaryThre != 0) {
                                                     String path = imageProcess.path_process + "\\gray\\" + files[indexFiles].getName().replace(".jpg", "") + "_gray.bmp";
                                                     BufferedImage imageGray = null;
                                                     BufferedImage imageBinary = null;
                                                     try {
                                                         imageGray = ImageIO.read(new File(path));
                                                         imageBinary = imageProcess.BinaryWithParam(imageGray, binaryThre);

                                                         BufferedImage imageErosion = imageProcess.Erosion(imageBinary);
                                                         BufferedImage imageExpand = imageProcess.Expand(imageErosion);
                                                         imageProcess.Segmentation(imageExpand, "\\edge_ero");

                                                         BufferedImage imageAvgFilter = imageProcess.AvrFiltering(imageBinary);
                                                         imageProcess.Segmentation(imageAvgFilter, "\\edge_avg");

                                                         BufferedImage imageEightDenoise = imageProcess.EightDenoise(imageBinary, MaxNearPoints);
                                                         imageProcess.Segmentation(imageEightDenoise, "\\edge_eight");

                                                         BufferedImage imageMed = imageProcess.MedianFiltering(imageBinary);
                                                         imageProcess.Segmentation(imageMed, "\\edge_med");
                                                     } catch (IOException e1) {
                                                         e1.printStackTrace();
                                                     }
                                                     ImageIcon icon = new ImageIcon(imageBinary);
                                                     imagePro.setIcon(icon);
                                                 } else {
                                                     String path = imageProcess.path_process + "\\binary\\" + files[indexFiles].getName().replace(".jpg", "") + "_binary.bmp";
                                                     BufferedImage imageBinary = null;
                                                     try {
                                                         imageBinary = ImageIO.read(new File(path));
                                                     } catch (IOException e1) {
                                                         e1.printStackTrace();
                                                     }
                                                     ImageIcon icon = new ImageIcon(imageBinary);
                                                     imagePro.setIcon(icon);
                                                 }
                                             } else {
                                                 if (binaryThre != 0) {
                                                     String path = imageProcess.path_process + "\\gray\\" + file.getName().replace(".jpg", "") + "_gray.bmp";
                                                     BufferedImage imageGray = null;
                                                     BufferedImage imageBinary = null;
                                                     try {
                                                         imageGray = ImageIO.read(new File(path));
                                                         imageBinary = imageProcess.BinaryWithParam(imageGray, binaryThre);

                                                         BufferedImage imageErosion = imageProcess.Erosion(imageBinary);
                                                         BufferedImage imageExpand = imageProcess.Expand(imageErosion);
                                                         imageProcess.Segmentation(imageExpand, "\\edge_ero");

                                                         BufferedImage imageAvgFilter = imageProcess.AvrFiltering(imageBinary);
                                                         imageProcess.Segmentation(imageAvgFilter, "\\edge_avg");

                                                         BufferedImage imageEightDenoise = imageProcess.EightDenoise(imageBinary, MaxNearPoints);
                                                         imageProcess.Segmentation(imageEightDenoise, "\\edge_eight");

                                                         BufferedImage imageMed = imageProcess.MedianFiltering(imageBinary);
                                                         imageProcess.Segmentation(imageMed, "\\edge_med");
                                                     } catch (IOException e1) {
                                                         e1.printStackTrace();
                                                     }
                                                     ImageIcon icon = new ImageIcon(imageBinary);
                                                     imagePro.setIcon(icon);
                                                 } else {
                                                     String path = imageProcess.path_process + "\\binary\\" + file.getName().replace(".jpg", "") + "_binary.bmp";
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
                                         }
                                     }
                                 }

        );
        return binary;
    }

    private JButton ErosionButton() {
        JButton erosion = new JButton();
        erosion.setText("��ʴ");
        erosion.setSize(80, 40);
        erosion.addActionListener(new ActionListener() {
                                      @Override
                                      public void actionPerformed(ActionEvent e) {
                                          if (file == null && files == null) {
                                              imagePro.setIcon(null);
                                              JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                          } else {
                                              if (files != null) {
                                                  String path = imageProcess.path_process + "\\erosion\\" + files[indexFiles].getName().replace(".jpg", "") + "_erosion.bmp";
                                                  BufferedImage imageErosion = null;
                                                  try {
                                                      imageErosion = ImageIO.read(new File(path));
                                                  } catch (IOException e1) {
                                                      e1.printStackTrace();
                                                  }
                                                  ImageIcon icon = new ImageIcon(imageErosion);
                                                  imagePro.setIcon(icon);
                                              } else {
                                                  String path = imageProcess.path_process + "\\erosion\\" + file.getName().replace(".jpg", "") + "_erosion.bmp";
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
                                      }
                                  }
        );
        return erosion;
    }

    private JButton ExpandButton() {
        JButton expand = new JButton();
        expand.setText("����");
        expand.setSize(80, 40);
        expand.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent e) {
                                         if (file == null && files == null) {
                                             imagePro.setIcon(null);
                                             JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                         } else {
                                             if (files != null) {
                                                 String path = imageProcess.path_process + "\\expand\\" + files[indexFiles].getName().replace(".jpg", "") + "_expand.bmp";
                                                 BufferedImage imageExpand = null;
                                                 try {
                                                     imageExpand = ImageIO.read(new File(path));
                                                 } catch (IOException e1) {
                                                     e1.printStackTrace();
                                                 }
                                                 ImageIcon icon = new ImageIcon(imageExpand);
                                                 imagePro.setIcon(icon);
                                                 imageDenoise = imageExpand;
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
                                                 imageDenoise = imageExpand;
                                             }
                                         }
                                     }
                                 }
        );
        return expand;
    }

//    private JButton GetEdgeButton() {
//        JButton getedge = new JButton();
//        getedge.setText("��Ե���");
//        getedge.setSize(80, 40);
//        getedge.addActionListener(new ActionListener() {
//                                      @Override
//                                      public void actionPerformed(ActionEvent e) {
//                                          if (file == null && files == null) {
//                                              imagePro.setIcon(null);
//                                              JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
//                                          } else {
//                                              if (files != null) {
//                                                  String path = imageProcess.path_process + "\\getedge\\" + files[indexFiles].getName().replace(".jpg", "") + "_edgedete.bmp";
//                                                  BufferedImage imageGetedge = null;
//                                                  if ((new File(path)).exists() ){
//                                                      try {
//                                                          imageGetedge = ImageIO.read(new File(path));
//                                                      } catch (IOException e1) {
//                                                          e1.printStackTrace();
//                                                      }
//                                                  } else {
//                                                      imageGetedge = imageProcess.Segmentation(image);
//                                                      image = imageGetedge;
//                                                  }
//                                                  ImageIcon icon = new ImageIcon(imageGetedge,);
//                                                  imagePro.setIcon(icon);
//                                              } else {
//                                                  String path = imageProcess.path_process + "\\getedge\\" + file.getName().replace(".jpg", "") + "_edgedete.bmp";
//                                                  BufferedImage imageGetedge = null;
//                                                  if ((new File(path)).exists() ){
//                                                      try {
//                                                          imageGetedge = ImageIO.read(new File(path));
//                                                      } catch (IOException e1) {
//                                                          e1.printStackTrace();
//                                                      }
//                                                  } else {
//                                                      imageGetedge = imageProcess.Segmentation(image);
//                                                      image = imageGetedge;
//                                                  }
//                                                  ImageIcon icon = new ImageIcon(imageGetedge);
//                                                  imagePro.setIcon(icon);
//                                              }
//                                          }
//                                      }
//                                  }
//
//        );
//        return getedge;
//    }

//    private JButton SegmentionButton() {
//        JButton segmention = new JButton();
//        segmention.setText("�ָ�");
//        segmention.setSize(80, 40);
//        segmention.addActionListener(new ActionListener() {
//                                         @Override
//                                         public void actionPerformed(ActionEvent e) {
//                                             JLabel[] allSeg = {Single1, Single2, Single3, Single4, Single5, Single6};
//                                             if (file == null && files == null) {
//                                                 Single1.setIcon(null);
//                                                 Single2.setIcon(null);
//                                                 Single3.setIcon(null);
//                                                 Single4.setIcon(null);
//                                                 Single5.setIcon(null);
//                                                 Single6.setIcon(null);
//                                                 JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
////                                                 String path = "D:\\JaveDev\\Graduation\\imagehandled\\single_ero_normal";
////                                                 File fileDir = new File(path);
////                                                 String[] fileArray = fileDir.list();
////                                                 for (int i = 0; i < fileArray.length; i++) {
////                                                     File temp = new File(path + "\\" + fileArray[i]);
////                                                     temp.delete();
////                                                 }
//                                             } else {
//                                                 String path = imageProcess.path_process + "\\single_ero_normal";
//                                                 File fileDir = new File(path);
//                                                 String[] fileArray = fileDir.list();
//                                                 BufferedImage imageSingle = null;
//                                                 for (int i = 0, j = 0; i < fileArray.length; i++) {
//                                                     if (fileArray[i].substring(0,file.getName().replace(".jpg", "").length()).equals(file.getName().replace(".jpg", "")) ||
//                                                             fileArray[i].substring(0,files[indexFiles].getName().replace(".jpg","").length()).equals(files[indexFiles].getName().replace(".jpg",""))) {
//                                                         if ((new File(path)).exists() ){
//                                                             try {
//                                                                 imageSingle = ImageIO.read(new File(path));
//                                                             } catch (IOException e1) {
//                                                                 e1.printStackTrace();
//                                                             }
//                                                         } else {
//                                                             imageSingle = imageProcess.Erosion(image);
//                                                             image = imageSingle;
//                                                         }
//                                                         ImageIcon icon = new ImageIcon(imageSingle);
//                                                         allSeg[j].setIcon(icon);
//                                                         allSeg[j].setHorizontalAlignment(JLabel.CENTER);
//                                                         j++;
//                                                     }
//                                                 }
//                                             }
//                                         }
//                                     }
//
//        );
//        return segmention;
//    }

    private JButton AvgFilterButton() {
        JButton avg = new JButton();
        avg.setText("��ֵ�˲�");
        avg.setSize(80, 40);
        avg.addActionListener(new ActionListener() {
                                  @Override
                                  public void actionPerformed(ActionEvent e) {
                                      if (file == null && files == null) {
                                          imagePro.setIcon(null);
                                          JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                      } else {
                                          if (files != null) {
                                              String path = imageProcess.path_process + "\\avg\\" + files[indexFiles].getName().replace(".jpg", "") + "_avgfilter.bmp";
                                              BufferedImage imageAvgFilter = null;
                                              try {
                                                  imageAvgFilter = ImageIO.read(new File(path));
                                              } catch (IOException e1) {
                                                  e1.printStackTrace();
                                              }
                                              ImageIcon icon = new ImageIcon(imageAvgFilter);
                                              imagePro.setIcon(icon);
                                              imageDenoise = imageAvgFilter;
                                          } else {
                                              String path = imageProcess.path_process + "\\avg\\" + file.getName().replace(".jpg", "") + "_avgfilter.bmp";
                                              BufferedImage imageAvgFilter = null;
                                              try {
                                                  imageAvgFilter = ImageIO.read(new File(path));
                                              } catch (IOException e1) {
                                                  e1.printStackTrace();
                                              }
                                              ImageIcon icon = new ImageIcon(imageAvgFilter);
                                              imagePro.setIcon(icon);
                                              imageDenoise = imageAvgFilter;
                                          }
                                      }
                                  }
                              }
        );
        return avg;
    }

    private JButton MedFilterButton() {
        JButton med = new JButton();
        med.setText("��ֵ�˲�");
        med.setSize(80, 40);
        med.addActionListener(new ActionListener() {
                                  @Override
                                  public void actionPerformed(ActionEvent e) {
                                      if (file == null && files == null) {
                                          imagePro.setIcon(null);
                                          JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                      } else {
                                          if (files != null) {
                                              String path = imageProcess.path_process + "\\median\\" + files[indexFiles].getName().replace(".jpg", "") + "_medfilter.bmp";
                                              BufferedImage imageMedFilter = null;
                                              try {
                                                  imageMedFilter = ImageIO.read(new File(path));
                                              } catch (IOException e1) {
                                                  e1.printStackTrace();
                                              }
                                              ImageIcon icon = new ImageIcon(imageMedFilter);
                                              imagePro.setIcon(icon);
                                              imageDenoise = imageMedFilter;
                                          } else {
                                              String path = imageProcess.path_process + "\\median\\" + file.getName().replace(".jpg", "") + "_medfilter.bmp";
                                              BufferedImage imageMedFilter = null;
                                              try {
                                                  imageMedFilter = ImageIO.read(new File(path));
                                              } catch (IOException e1) {
                                                  e1.printStackTrace();
                                              }
                                              ImageIcon icon = new ImageIcon(imageMedFilter);
                                              imagePro.setIcon(icon);
                                              imageDenoise = imageMedFilter;
                                          }
                                      }
                                  }
                              }
        );
        return med;
    }

    private JButton EightDenoiseButton() {
        JButton eight = new JButton();
        eight.setText("�˵���ͨ��ȥ��");
        eight.setSize(80, 40);
        eight.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (file == null && files == null) {
                                            imagePro.setIcon(null);
                                            JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                        } else {
                                            if (files != null) {
                                                String path = imageProcess.path_process + "\\eightdenoise\\" + files[indexFiles].getName().replace(".jpg", "") + "_eightdenoise.bmp";
                                                BufferedImage imageEightDenoise = null;
                                                try {
                                                    imageEightDenoise = ImageIO.read(new File(path));
                                                } catch (IOException e1) {
                                                    e1.printStackTrace();
                                                }
                                                ImageIcon icon = new ImageIcon(imageEightDenoise);
                                                imagePro.setIcon(icon);
                                                imageDenoise = imageEightDenoise;
                                            } else {
                                                String path = imageProcess.path_process + "\\eightdenoise\\" + file.getName().replace(".jpg", "") + "_eightdenoise.bmp";
                                                BufferedImage imageEightDenoise = null;
                                                try {
                                                    imageEightDenoise = ImageIO.read(new File(path));
                                                } catch (IOException e1) {
                                                    e1.printStackTrace();
                                                }
                                                ImageIcon icon = new ImageIcon(imageEightDenoise);
                                                imagePro.setIcon(icon);
                                                imageDenoise = imageEightDenoise;
                                            }
                                        }
                                    }
                                }
        );
        return eight;
    }

    private JButton ImageTransButton() {
        JButton imageTrans = new JButton();
        imageTrans.setText("�ڰ�ת��");
        imageTrans.setSize(80, 40);
        imageTrans.addActionListener(new ActionListener() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        if (file == null && files == null) {
                                            imagePro.setIcon(null);
                                            JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                        } else {
                                            if (files != null) {
                                                String path = null;
                                                if (binaryThre == 0){
                                                    path = imageProcess.path_process + "\\binary\\" + files[indexFiles].getName().replace(".jpg", "") + "_binary.bmp";
                                                } else {
                                                    path = imageProcess.path_process + "\\binary\\" + files[indexFiles].getName().replace(".jpg", "") + "_binary" + binaryThre + ".bmp";
                                                }

                                                BufferedImage imageBinary = null;
                                                BufferedImage imageTrans = null;
                                                try {
                                                    imageBinary = ImageIO.read(new File(path));
                                                    imageTrans = imageProcess.ImageTransBlackWhite(imageBinary);
                                                    BufferedImage imageErosion = imageProcess.Erosion(imageTrans);
                                                    BufferedImage imageExpand = imageProcess.Expand(imageErosion);
                                                    imageProcess.Segmentation(imageExpand, "\\edge_ero");

                                                    BufferedImage imageAvgFilter = imageProcess.AvrFiltering(imageTrans);
                                                    imageProcess.Segmentation(imageAvgFilter, "\\edge_avg");

                                                    BufferedImage imageEightDenoise = imageProcess.EightDenoise(imageTrans, MaxNearPoints);
                                                    imageProcess.Segmentation(imageEightDenoise, "\\edge_eight");

                                                    BufferedImage imageMed = imageProcess.MedianFiltering(imageTrans);
                                                    imageProcess.Segmentation(imageMed, "\\edge_med");
                                                } catch (IOException e1) {
                                                    e1.printStackTrace();
                                                }
                                                ImageIcon icon = new ImageIcon(imageTrans);
                                                imagePro.setIcon(icon);
                                                imageDenoise = imageTrans;
                                            } else {
                                                String path = null;
                                                if (binaryThre == 0){
                                                    path = imageProcess.path_process + "\\binary\\" + files[indexFiles].getName().replace(".jpg", "") + "_binary.bmp";
                                                } else {
                                                    path = imageProcess.path_process + "\\binary\\" + files[indexFiles].getName().replace(".jpg", "") + "_binary" + binaryThre + ".bmp";
                                                }

                                                BufferedImage imageBinary = null;
                                                BufferedImage imageTrans = null;
                                                try {
                                                    imageBinary = ImageIO.read(new File(path));
                                                    imageTrans = imageProcess.ImageTransBlackWhite(imageBinary);

                                                    BufferedImage imageErosion = imageProcess.Erosion(imageTrans);
                                                    BufferedImage imageExpand = imageProcess.Expand(imageErosion);
                                                    imageProcess.Segmentation(imageExpand, "\\edge_ero");

                                                    BufferedImage imageAvgFilter = imageProcess.AvrFiltering(imageTrans);
                                                    imageProcess.Segmentation(imageAvgFilter, "\\edge_avg");

                                                    BufferedImage imageEightDenoise = imageProcess.EightDenoise(imageTrans, MaxNearPoints);
                                                    imageProcess.Segmentation(imageEightDenoise, "\\edge_eight");

                                                    BufferedImage imageMed = imageProcess.MedianFiltering(imageTrans);
                                                    imageProcess.Segmentation(imageMed, "\\edge_med");
                                                } catch (IOException e1) {
                                                    e1.printStackTrace();
                                                }
                                                ImageIcon icon = new ImageIcon(imageTrans);
                                                imagePro.setIcon(icon);
                                                imageDenoise = imageTrans;
                                            }
                                        }
                                    }
                                }
        );
        return imageTrans;
    }

//    private JButton SegAfterAvg() {
//        JButton avg = new JButton();
//        avg.setText("��ֵ�˲���ָ�");
//        avg.setSize(80, 40);
//        avg.addActionListener(new ActionListener() {
//                                  @Override
//                                  public void actionPerformed(ActionEvent e) {
//                                      if (file == null && files == null) {
//                                          imagePro.setIcon(null);
//                                          JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
//                                      } else {
//                                          if (files != null) {
//                                              String path = imageProcess.path_process + "\\avg\\" + files[indexFiles].getName().replace(".jpg", "") + "_avgfilter.bmp";
//                                              BufferedImage imageAvgFilter = null;
//                                              try {
//                                                  imageAvgFilter = ImageIO.read(new File(path));
//                                              } catch (IOException e1) {
//                                                  e1.printStackTrace();
//                                              }
//                                              ImageIcon icon = new ImageIcon(imageAvgFilter);
//                                              imagePro.setIcon(icon);
//                                          } else {
//                                              String path = imageProcess.path_process + "\\avg\\" + file.getName().replace(".jpg", "") + "_avgfilter.bmp";
//                                              BufferedImage imageAvgFilter = null;
//                                              try {
//                                                  imageAvgFilter = ImageIO.read(new File(path));
//                                              } catch (IOException e1) {
//                                                  e1.printStackTrace();
//                                              }
//                                              ImageIcon icon = new ImageIcon(imageAvgFilter);
//                                              imagePro.setIcon(icon);
//                                          }
//                                      }
//                                  }
//                              }
//        );
//        return avg;
//    }
//
//    private JButton SegAfterEro() {
//        JButton avg = new JButton();
//        avg.setText("��ʴ���ͺ�ָ�");
//        avg.setSize(80, 40);
//        avg.addActionListener(new ActionListener() {
//                                  @Override
//                                  public void actionPerformed(ActionEvent e) {
//                                      if (file == null && files == null) {
//                                          imagePro.setIcon(null);
//                                          JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
//                                      } else {
//                                          if (files != null) {
//                                              String path = imageProcess.path_process + "\\avg\\" + files[indexFiles].getName().replace(".jpg", "") + "_avgfilter.bmp";
//                                              BufferedImage imageAvgFilter = null;
//                                              try {
//                                                  imageAvgFilter = ImageIO.read(new File(path));
//                                              } catch (IOException e1) {
//                                                  e1.printStackTrace();
//                                              }
//                                              ImageIcon icon = new ImageIcon(imageAvgFilter);
//                                              imagePro.setIcon(icon);
//                                          } else {
//                                              String path = imageProcess.path_process + "\\avg\\" + file.getName().replace(".jpg", "") + "_avgfilter.bmp";
//                                              BufferedImage imageAvgFilter = null;
//                                              try {
//                                                  imageAvgFilter = ImageIO.read(new File(path));
//                                              } catch (IOException e1) {
//                                                  e1.printStackTrace();
//                                              }
//                                              ImageIcon icon = new ImageIcon(imageAvgFilter);
//                                              imagePro.setIcon(icon);
//                                          }
//                                      }
//                                  }
//                              }
//        );
//        return avg;
//    }

    private JButton YprojectionButton() {
        JButton ypro = new JButton();
        ypro.setText("Y��ֱͶӰ");
        ypro.setSize(80, 40);
        ypro.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       if (file == null && files == null) {
                                           imagePro.setIcon(null);
                                           JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                                       } else if (imageDenoise == null) {
                                           JOptionPane.showMessageDialog(getContentPane(), "�������ȥ��");
                                       } else {
                                           if (files != null) {
                                               try {
                                                   imageProcess.YProjection(imageDenoise);
                                               } catch (IOException e1) {
                                                   e1.printStackTrace();
                                               }
                                               String path = imageProcess.path_process + "\\Ypro\\" + files[indexFiles].getName().replace(".jpg", "") + "_yprojection.bmp";
                                               BufferedImage imageYpro = null;
                                               try {
                                                   imageYpro = ImageIO.read(new File(path));
                                               } catch (IOException e1) {
                                                   e1.printStackTrace();
                                               }
                                               ImageIcon icon = new ImageIcon(imageYpro);
                                               imagePro.setIcon(icon);
                                           } else {
                                               try {
                                                   imageProcess.YProjection(imageDenoise);
                                               } catch (IOException e1) {
                                                   e1.printStackTrace();
                                               }
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
                                   }
                               }


        );
        return ypro;
    }

    private JButton ClearButton() {
        JButton clearPanel = new JButton();
        clearPanel.setText("������");
        clearPanel.setSize(80, 40);
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
                                             textReco.setText("");
                                             charNumInput.setText("");
                                             binaryThreInput.setText("");
                                         }
                                     }
        );
        return clearPanel;
    }

    private JButton GetCharNum() {
        JButton jButtonInput = new JButton();
        jButtonInput.setText("ȷ��");
        jButtonInput.setSize(new Dimension(100, 28));
        jButtonInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = charNumInput.getText().trim();// ��ȡ�û���
                if (text.equals("")) {
                    JOptionPane.showMessageDialog(null, "����������:0-9");
                } else if (!text.matches("[0-9]")) {
                    JOptionPane.showMessageDialog(null, "����������:0-9");
                } else {
                    charNum = Integer.parseInt(text.trim());
                    JOptionPane.showMessageDialog(null, "�ַ���������ɹ�");
                }
            }
        });
        return jButtonInput;
    }

    private JButton GetBinaryThre() {
        JButton jButtonInput = new JButton();
        jButtonInput.setText("ȷ��");
        jButtonInput.setSize(new Dimension(100, 28));
        jButtonInput.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String text = binaryThreInput.getText().trim();// ��ȡ�û���
                if (text.equals("")) {
                    JOptionPane.showMessageDialog(null, "����������:0-255");
                } else if (!text.matches("[0-9]{1,3}")) {
                    JOptionPane.showMessageDialog(null, "����������:0-255");
                } else {
                    binaryThre = Integer.parseInt(text.trim());
                    JOptionPane.showMessageDialog(null, "��ֵ����ɹ�");
                }
            }
        });
        return jButtonInput;
    }

    ActionListener edgeAfterAvg = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (file == null && files == null) {
                imagePro.setIcon(null);
                JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
            } else {
                if (files != null) {
                    String path = imageProcess.path_process + "\\edge_avg\\" + files[indexFiles].getName().replace(".jpg", "") + "_edgedete.bmp";
                    BufferedImage imageGetedge = null;
                    try {
                        imageGetedge = ImageIO.read(new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageGetedge);
                    imagePro.setIcon(icon);
                } else {
                    String path = imageProcess.path_process + "\\edge_avg\\" + file.getName().replace(".jpg", "") + "_edgedete.bmp";
                    BufferedImage imageGetedge = null;
                    try {
                        imageGetedge = ImageIO.read(new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageGetedge);
                    imagePro.setIcon(icon);
                }
            }
        }
    };

    ActionListener edgeAfterMed = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (file == null && files == null) {
                imagePro.setIcon(null);
                JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
            } else {
                if (files != null) {
                    String path = imageProcess.path_process + "\\edge_med\\" + files[indexFiles].getName().replace(".jpg", "") + "_edgedete.bmp";
                    BufferedImage imageGetedge = null;
                    try {
                        imageGetedge = ImageIO.read(new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageGetedge);
                    imagePro.setIcon(icon);
                } else {
                    String path = imageProcess.path_process + "\\edge_med\\" + file.getName().replace(".jpg", "") + "_edgedete.bmp";
                    BufferedImage imageGetedge = null;
                    try {
                        imageGetedge = ImageIO.read(new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageGetedge);
                    imagePro.setIcon(icon);
                }
            }
        }
    };

    ActionListener edgeAfterEro = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (file == null && files == null) {
                imagePro.setIcon(null);
                JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
            } else {
                if (files != null) {
                    String path = imageProcess.path_process + "\\edge_ero\\" + files[indexFiles].getName().replace(".jpg", "") + "_edgedete.bmp";
                    BufferedImage imageGetedge = null;
                    try {
                        imageGetedge = ImageIO.read(new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ImageIcon icon = new ImageIcon(imageGetedge);
                    imagePro.setIcon(icon);
                } else {
                    String path = imageProcess.path_process + "\\edge_ero\\" + file.getName().replace(".jpg", "") + "_edgedete.bmp";
                    BufferedImage imageGetedge = null;
                    try {
                        imageGetedge = ImageIO.read(new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageGetedge);
                    imagePro.setIcon(icon);
                }
            }
        }
    };

    ActionListener edgeAfterEight = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (file == null && files == null) {
                imagePro.setIcon(null);
                JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
            } else {
                if (files != null) {
                    String path = imageProcess.path_process + "\\edge_eight\\" + files[indexFiles].getName().replace(".jpg", "") + "_edgedete.bmp";
                    BufferedImage imageGetedge = null;
                    try {
                        imageGetedge = ImageIO.read(new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    ImageIcon icon = new ImageIcon(imageGetedge);
                    imagePro.setIcon(icon);
                } else {
                    String path = imageProcess.path_process + "\\edge_eight\\" + file.getName().replace(".jpg", "") + "_edgedete.bmp";
                    BufferedImage imageGetedge = null;
                    try {
                        imageGetedge = ImageIO.read(new File(path));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageGetedge);
                    imagePro.setIcon(icon);
                }
            }
        }
    };

    ActionListener segAfterAvg = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            JLabel[] allSeg = {Single1, Single2, Single3, Single4, Single5, Single6};
            if (charNum == 0) {
                JOptionPane.showMessageDialog(null, "���������ַ����������Ϊ6");
            } else {
                if (file == null && files == null) {
                    Single1.setIcon(null);
                    Single2.setIcon(null);
                    Single3.setIcon(null);
                    Single4.setIcon(null);
                    Single5.setIcon(null);
                    Single6.setIcon(null);
                    JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                } else {
                    Single1.setIcon(null);
                    Single2.setIcon(null);
                    Single3.setIcon(null);
                    Single4.setIcon(null);
                    Single5.setIcon(null);
                    Single6.setIcon(null);
                    BufferedImage imageAvgFilter = null;
                    BufferedImage imageGetedge = null;
                    String pathAvg = null;
                    String pathEdge = null;
                    if (files != null) {
                         pathAvg= imageProcess.path_process + "\\avg\\" + files[indexFiles].getName().replace(".jpg", "") + "_avgfilter.bmp";
                         pathEdge= imageProcess.path_process + "\\edge_avg\\" + files[indexFiles].getName().replace(".jpg", "") + "_edgedete.bmp";
                    } else if (file != null){
                        pathAvg= imageProcess.path_process + "\\avg\\" + file.getName().replace(".jpg", "") + "_avgfilter.bmp";
                        pathEdge= imageProcess.path_process + "\\edge_avg\\" + file.getName().replace(".jpg", "") + "_edgedete.bmp";
                    }
                    try {
                        imageGetedge = ImageIO.read(new File(pathEdge));
                        imageAvgFilter = ImageIO.read(new File(pathAvg));
                        imageProcess.setCharNum(charNum);
                        int[][] alledge = imageProcess.GetSingleChar(imageGetedge);
                        imageProcess.PrintAfterSeg(imageAvgFilter, alledge, "\\single_avg");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String path = imageProcess.path_process + "\\single_avg_normal";
                    File fileDir = new File(path);
                    String[] fileArray = fileDir.list();
                    BufferedImage imageSingle = null;
                    for (int i = 0, j = 0; i < fileArray.length; i++) {
                        if ((file != null && fileArray[i].substring(0, file.getName().replace(".jpg", "").length()).equals(file.getName().replace(".jpg", ""))) ||
                                (files != null && fileArray[i].substring(0, files[indexFiles].getName().replace(".jpg", "").length()).equals(files[indexFiles].getName().replace(".jpg", "")))) {
                            try {
                                imageSingle = ImageIO.read(new File(path + "\\" + fileArray[i]));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ImageIcon icon = new ImageIcon(imageSingle);
                            allSeg[j].setIcon(icon);
                            allSeg[j].setHorizontalAlignment(JLabel.CENTER);
                            j++;
                        }
                    }
                }
            }
        }
    };

    ActionListener segAfterMed = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            JLabel[] allSeg = {Single1, Single2, Single3, Single4, Single5, Single6};
            if (charNum == 0) {
                JOptionPane.showMessageDialog(null, "���������ַ����������Ϊ6");
            } else {
                if (file == null && files == null) {
                    Single1.setIcon(null);
                    Single2.setIcon(null);
                    Single3.setIcon(null);
                    Single4.setIcon(null);
                    Single5.setIcon(null);
                    Single6.setIcon(null);
                    JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                } else {
                    Single1.setIcon(null);
                    Single2.setIcon(null);
                    Single3.setIcon(null);
                    Single4.setIcon(null);
                    Single5.setIcon(null);
                    Single6.setIcon(null);
                    BufferedImage imageMedFilter = null;
                    BufferedImage imageGetedge = null;
                    String pathAvg = null;
                    String pathEdge = null;
                    if (files != null) {
                        pathAvg= imageProcess.path_process + "\\median\\" + files[indexFiles].getName().replace(".jpg", "") + "_medfilter.bmp";
                        pathEdge= imageProcess.path_process + "\\edge_med\\" + files[indexFiles].getName().replace(".jpg", "") + "_edgedete.bmp";
                    } else if (file != null){
                        pathAvg= imageProcess.path_process + "\\median\\" + file.getName().replace(".jpg", "") + "_medfilter.bmp";
                        pathEdge= imageProcess.path_process + "\\edge_med\\" + file.getName().replace(".jpg", "") + "_edgedete.bmp";
                    }
                    try {
                        imageGetedge = ImageIO.read(new File(pathEdge));
                        imageMedFilter = ImageIO.read(new File(pathAvg));
                        imageProcess.setCharNum(charNum);
                        int[][] alledge = imageProcess.GetSingleChar(imageGetedge);
                        imageProcess.PrintAfterSeg(imageMedFilter, alledge, "\\single_med");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String path = imageProcess.path_process + "\\single_med_normal";
                    File fileDir = new File(path);
                    String[] fileArray = fileDir.list();
                    BufferedImage imageSingle = null;
                    for (int i = 0, j = 0; i < fileArray.length; i++) {
                        if ((file != null && fileArray[i].substring(0, file.getName().replace(".jpg", "").length()).equals(file.getName().replace(".jpg", ""))) ||
                                (files != null && fileArray[i].substring(0, files[indexFiles].getName().replace(".jpg", "").length()).equals(files[indexFiles].getName().replace(".jpg", "")))) {
                            try {
                                imageSingle = ImageIO.read(new File(path + "\\" + fileArray[i]));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ImageIcon icon = new ImageIcon(imageSingle);
                            allSeg[j].setIcon(icon);
                            allSeg[j].setHorizontalAlignment(JLabel.CENTER);
                            j++;
                        }
                    }
                }
            }
        }
    };

    ActionListener segAfterEro = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (charNum == 0) {
                JOptionPane.showMessageDialog(null, "���������ַ����������Ϊ6");
            } else {
                JLabel[] allSeg = {Single1, Single2, Single3, Single4, Single5, Single6};
                if (file == null && files == null) {
                    Single1.setIcon(null);
                    Single2.setIcon(null);
                    Single3.setIcon(null);
                    Single4.setIcon(null);
                    Single5.setIcon(null);
                    Single6.setIcon(null);
                    JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                } else {
                    Single1.setIcon(null);
                    Single2.setIcon(null);
                    Single3.setIcon(null);
                    Single4.setIcon(null);
                    Single5.setIcon(null);
                    Single6.setIcon(null);
                    BufferedImage imageExp = null;
                    BufferedImage imageGetedge = null;
                    String pathExp = null;
                    String pathEdge = null;
                    if (files != null) {
                        pathExp= imageProcess.path_process + "\\expand\\" + files[indexFiles].getName().replace(".jpg", "") + "_expand.bmp";
                        pathEdge= imageProcess.path_process + "\\edge_ero\\" + files[indexFiles].getName().replace(".jpg", "") + "_edgedete.bmp";
                    } else if (file != null){
                        pathExp= imageProcess.path_process + "\\expand\\" + file.getName().replace(".jpg", "") + "_expand.bmp";
                        pathEdge= imageProcess.path_process + "\\edge_ero\\" + file.getName().replace(".jpg", "") + "_edgedete.bmp";
                    }
                    try {
                        imageExp = ImageIO.read(new File(pathExp));
                        imageGetedge = ImageIO.read(new File(pathEdge));
                        imageProcess.setCharNum(charNum);
                        int[][] alledge = imageProcess.GetSingleChar(imageGetedge);
                        imageProcess.PrintAfterSeg(imageExp, alledge, "\\single_ero");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String path = imageProcess.path_process + "\\single_ero_normal";
                    File fileDir = new File(path);
                    String[] fileArray = fileDir.list();
                    BufferedImage imageSingle = null;
                    for (int i = 0, j = 0; i < fileArray.length; i++) {
                        if ((file != null && fileArray[i].substring(0, file.getName().replace(".jpg", "").length()).equals(file.getName().replace(".jpg", ""))) ||
                                (files != null && fileArray[i].substring(0, files[indexFiles].getName().replace(".jpg", "").length()).equals(files[indexFiles].getName().replace(".jpg", "")))) {

                            try {
                                imageSingle = ImageIO.read(new File(path + "\\" + fileArray[i]));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ImageIcon icon = new ImageIcon(imageSingle);
                            allSeg[j].setIcon(icon);
                            allSeg[j].setHorizontalAlignment(JLabel.CENTER);
                            j++;
                        }
                    }
                }
            }
        }
    };

    ActionListener segAfterEight = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (charNum == 0) {
                JOptionPane.showMessageDialog(null, "���������ַ����������Ϊ6");
            } else {
                JLabel[] allSeg = {Single1, Single2, Single3, Single4, Single5, Single6};
                if (file == null && files == null) {
                    Single1.setIcon(null);
                    Single2.setIcon(null);
                    Single3.setIcon(null);
                    Single4.setIcon(null);
                    Single5.setIcon(null);
                    Single6.setIcon(null);
                    JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                } else {
                    Single1.setIcon(null);
                    Single2.setIcon(null);
                    Single3.setIcon(null);
                    Single4.setIcon(null);
                    Single5.setIcon(null);
                    Single6.setIcon(null);
                    BufferedImage imageEight = null;
                    BufferedImage imageGetedge = null;
                    String pathEig = null;
                    String pathEdge = null;
                    if (files != null) {
                        pathEig= imageProcess.path_process + "\\eightdenoise\\" + files[indexFiles].getName().replace(".jpg", "") + "_eightdenoise.bmp";
                        pathEdge= imageProcess.path_process + "\\edge_eight\\" + files[indexFiles].getName().replace(".jpg", "") + "_edgedete.bmp";
                    } else if (file != null){
                        pathEig= imageProcess.path_process + "\\eightdenoise\\" + file.getName().replace(".jpg", "") + "_eightdenoise.bmp";
                        pathEdge= imageProcess.path_process + "\\edge_eight\\" + file.getName().replace(".jpg", "") + "_edgedete.bmp";
                    }

                    try {
                        imageGetedge = ImageIO.read(new File(pathEdge));
                        imageEight = ImageIO.read(new File(pathEig));
                        imageProcess.setCharNum(charNum);
                        int[][] alledge = imageProcess.GetSingleChar(imageGetedge);
                        imageProcess.PrintAfterSeg(imageEight, alledge, "\\single_eight");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String path = imageProcess.path_process + "\\single_eight_normal";
                    File fileDir = new File(path);
                    String[] fileArray = fileDir.list();
                    BufferedImage imageSingle = null;
                    for (int i = 0, j = 0; i < fileArray.length; i++) {
                        if ((file != null && fileArray[i].substring(0, file.getName().replace(".jpg", "").length()).equals(file.getName().replace(".jpg", ""))) ||
                                (files != null && fileArray[i].substring(0, files[indexFiles].getName().replace(".jpg", "").length()).equals(files[indexFiles].getName().replace(".jpg", "")))) {
                            try {
                                imageSingle = ImageIO.read(new File(path + "\\" + fileArray[i]));
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                            ImageIcon icon = new ImageIcon(imageSingle);
                            allSeg[j].setIcon(icon);
                            allSeg[j].setHorizontalAlignment(JLabel.CENTER);
                            j++;
                        }
                    }
                }
            }
        }
    };

    ActionListener segAfterProjection = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            JLabel[] allSeg = {Single1, Single2, Single3, Single4, Single5, Single6};
            if (charNum == 0) {
                JOptionPane.showMessageDialog(null, "���������ַ����������Ϊ6");
            } else {
                if (file == null && files == null) {
                    Single1.setIcon(null);
                    Single2.setIcon(null);
                    Single3.setIcon(null);
                    Single4.setIcon(null);
                    Single5.setIcon(null);
                    Single6.setIcon(null);
                    JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                } else {
                    if (imageDenoise == null){
                        JOptionPane.showMessageDialog(getContentPane(), "�������ȥ��", "������ʾ", JOptionPane.WARNING_MESSAGE);
                    } else {
                        Single1.setIcon(null);
                        Single2.setIcon(null);
                        Single3.setIcon(null);
                        Single4.setIcon(null);
                        Single5.setIcon(null);
                        Single6.setIcon(null);
                        try {
                            imageProcess.setCharNum(charNum);
                            int[] horizon = imageProcess.YProjection(imageDenoise);
                            int[] vertical = imageProcess.XProjection(imageDenoise);
                            int[][] alledge = imageProcess.GetByProjection(horizon, vertical);
                            for (int i = 0; i < alledge.length; i++){
                                for (int j = 0; j < alledge[i].length; j++){
                                    System.out.println("alledge[" + i + "][" + j + "]:" + alledge[i][j] );
                                }
                            }
                            imageProcess.PrintAfterSeg(imageDenoise, alledge, "\\single_projection");
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        String path = imageProcess.path_process + "\\single_projection_normal";
                        File fileDir = new File(path);
                        String[] fileArray = fileDir.list();
                        BufferedImage imageSingle = null;
                        for (int i = 0, j = 0; i < fileArray.length; i++) {
                            if ((file != null && fileArray[i].substring(0, file.getName().replace(".jpg", "").length()).equals(file.getName().replace(".jpg", ""))) ||
                                    (files != null && fileArray[i].substring(0, files[indexFiles].getName().replace(".jpg", "").length()).equals(files[indexFiles].getName().replace(".jpg", "")))) {
                                try {
                                    imageSingle = ImageIO.read(new File(path + "\\" + fileArray[i]));
                                } catch (IOException e1) {
                                    e1.printStackTrace();
                                }
                                ImageIcon icon = new ImageIcon(imageSingle);
                                allSeg[j].setIcon(icon);
                                allSeg[j].setHorizontalAlignment(JLabel.CENTER);
                                j++;
                            }
                        }
                    }
                }
            }
        }
    };

    ActionListener recoAvg = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (charNum == 0) {
                JOptionPane.showMessageDialog(null, "���������ַ����������Ϊ6");
            } else {
                if (file == null && files == null) {
                    imagePro.setIcon(null);
                    JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                } else {
                    String path = imageProcess.path_process + "\\single_avg_normal\\";
                    File[] filesReco = new File(path).listFiles();
                    ImageProcess imageProcessReco = new ImageProcess();
                    String result = "";
                    for (int i = 0; i < filesReco.length; i++) {
                        if ((file != null && filesReco[i].getName().substring(0, file.getName().replace(".jpg", "").length()).equals(file.getName().replace(".jpg", ""))) ||
                                (files != null && filesReco[i].getName().substring(0, files[indexFiles].getName().replace(".jpg", "").length()).equals(files[indexFiles].getName().replace(".jpg", "")))) {
                            result += imageProcessReco.MatchTemp(filesReco[i]);
                        }
                    }
                    if (result.equals("")){
                        JOptionPane.showMessageDialog(getContentPane(), "������ɷָ����", "������ʾ", JOptionPane.WARNING_MESSAGE);
                    } else {
                        result = imageProcessReco.PreMatch(result);
                        result = imageProcessReco.OutputResult(result);
                        textReco.setText(result);
                    }
                }
            }
        }
    };

    ActionListener recoMed = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (charNum == 0) {
                JOptionPane.showMessageDialog(null, "���������ַ����������Ϊ6");
            } else {
                if (file == null && files == null) {
                    imagePro.setIcon(null);
                    JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                } else {
                    String path = imageProcess.path_process + "\\single_avg_normal\\";
                    File[] filesReco = new File(path).listFiles();
                    ImageProcess imageProcessReco = new ImageProcess();
                    String result = "";
                    for (int i = 0; i < filesReco.length; i++) {
                        if ((file != null && filesReco[i].getName().substring(0, file.getName().replace(".jpg", "").length()).equals(file.getName().replace(".jpg", ""))) ||
                                (files != null && filesReco[i].getName().substring(0, files[indexFiles].getName().replace(".jpg", "").length()).equals(files[indexFiles].getName().replace(".jpg", "")))) {
                            result += imageProcessReco.MatchTemp(filesReco[i]);
                        }
                    }
                    if (result.equals("")){
                        JOptionPane.showMessageDialog(getContentPane(), "������ɷָ����", "������ʾ", JOptionPane.WARNING_MESSAGE);
                    } else {
                        result = imageProcessReco.PreMatch(result);
                        result = imageProcessReco.OutputResult(result);
                        textReco.setText(result);
                    }
                }
            }
        }
    };

    ActionListener recoEro = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (charNum == 0) {
                JOptionPane.showMessageDialog(null, "���������ַ����������Ϊ6");
            } else {
                if (file == null && files == null) {
                    imagePro.setIcon(null);
                    JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                } else {
                    String path = imageProcess.path_process + "\\single_ero_normal\\";
                    File[] filesReco = new File(path).listFiles();
                    ImageProcess imageProcessReco = new ImageProcess();
                    String result = "";
                    for (int i = 0; i < filesReco.length; i++) {
                        if ((file != null && filesReco[i].getName().substring(0, file.getName().replace(".jpg", "").length()).equals(file.getName().replace(".jpg", ""))) ||
                                (files != null && filesReco[i].getName().substring(0, files[indexFiles].getName().replace(".jpg", "").length()).equals(files[indexFiles].getName().replace(".jpg", "")))) {
                            result += imageProcessReco.MatchTemp(filesReco[i]);
                        }
                    }
                    if (result.equals("")){
                        JOptionPane.showMessageDialog(getContentPane(), "������ɷָ����", "������ʾ", JOptionPane.WARNING_MESSAGE);
                    } else {
                        result = imageProcessReco.PreMatch(result);
                        result = imageProcessReco.OutputResult(result);
                        textReco.setText(result);
                    }
                }
            }
        }
    };

    ActionListener recoEight = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (charNum == 0) {
                JOptionPane.showMessageDialog(null, "���������ַ����������Ϊ6");
            } else {
                if (file == null && files == null) {
                    imagePro.setIcon(null);
                    JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                } else {
                    String path = imageProcess.path_process + "\\single_eight_normal\\";
                    File[] filesReco = new File(path).listFiles();
                    ImageProcess imageProcessReco = new ImageProcess();
                    String result = "";
                    for (int i = 0; i < filesReco.length; i++) {
                        if ((file != null && filesReco[i].getName().substring(0, file.getName().replace(".jpg", "").length()).equals(file.getName().replace(".jpg", ""))) ||
                                (files != null && filesReco[i].getName().substring(0, files[indexFiles].getName().replace(".jpg", "").length()).equals(files[indexFiles].getName().replace(".jpg", "")))) {
                            result += imageProcessReco.MatchTemp(filesReco[i]);
                        }
                    }
                    if (result.equals("")){
                        JOptionPane.showMessageDialog(getContentPane(), "������ɷָ����", "������ʾ", JOptionPane.WARNING_MESSAGE);
                    } else {
                        result = imageProcessReco.PreMatch(result);
                        System.out.println(result);
                        result = imageProcessReco.OutputResult(result);
                        textReco.setText(result);
                    }
                }
            }
        }
    };

    ActionListener recoProjection = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (charNum == 0) {
                JOptionPane.showMessageDialog(null, "���������ַ����������Ϊ6");
            } else {
                if (file == null && files == null) {
                    imagePro.setIcon(null);
                    JOptionPane.showMessageDialog(getContentPane(), "���ȴ�ͼƬ�ļ�", "������ʾ", JOptionPane.WARNING_MESSAGE);
                } else {
                    String path = imageProcess.path_process + "\\single_projection_normal\\";
                    File[] filesReco = new File(path).listFiles();
                    ImageProcess imageProcessReco = new ImageProcess();
                    String result = "";
                    for (int i = 0; i < filesReco.length; i++) {
                        if ((file != null && filesReco[i].getName().substring(0, file.getName().replace(".jpg", "").length()).equals(file.getName().replace(".jpg", ""))) ||
                                (files != null && filesReco[i].getName().substring(0, files[indexFiles].getName().replace(".jpg", "").length()).equals(files[indexFiles].getName().replace(".jpg", "")))) {
                            result += imageProcessReco.MatchTemp(filesReco[i]);
                        }
                    }
                    if (result.equals("")){
                        JOptionPane.showMessageDialog(getContentPane(), "������ɷָ����", "������ʾ", JOptionPane.WARNING_MESSAGE);
                    } else {
                        result = imageProcessReco.PreMatch(result);
                        result = imageProcessReco.OutputResult(result);
                        textReco.setText(result);
                    }
                }
            }
        }
    };

    public RecoInterface() {

        JButton nextButton = NextFilesButton();
        JButton lastButton = LastFilesButton();
        JButton inputCharNumButton = GetCharNum();
        JButton inputThreButton = GetBinaryThre();

//        JComboBox<JButton> segGroup = new JComboBox<JButton>();
        JMenuBar segBar = new JMenuBar();
        JMenu segMenu = new JMenu("  �ָ�  ");
        JMenu openMenu = new JMenu("���ļ�");
        JMenu preMenu = new JMenu(" Ԥ���� ");
        JMenu denoiseMenu = new JMenu("  ȥ��  ");
        JMenu shadowMenu = new JMenu("  ͶӰ  ");
        JMenu edgeMenu = new JMenu("��Ե���");
        JMenu clearMenu = new JMenu("  ���  ");
        JMenu recoMenu = new JMenu("  ʶ��  ");

        JMenuItem openItem = new JMenuItem();
        openItem.setText("���ļ�");
        openItem.addActionListener(OpenButton().getActionListeners()[0]);
        openMenu.add(openItem);
        JMenuItem openFilesItem = new JMenuItem();
        openFilesItem.setText("���ļ���");
        openFilesItem.addActionListener(OpenFilesButton().getActionListeners()[0]);
        openMenu.add(openFilesItem);

        JMenuItem grayItem = new JMenuItem();
        grayItem.setText("�ҶȻ�");
        grayItem.addActionListener(GrayButton().getActionListeners()[0]);
        JMenuItem binaryItem = new JMenuItem();
        binaryItem.setText("��ֵ��");
        binaryItem.addActionListener(BinaryButton().getActionListeners()[0]);
        JMenuItem imageTransItem = new JMenuItem();
        imageTransItem.setText("ͼ��ڰ�ת��");
        imageTransItem.addActionListener(ImageTransButton().getActionListeners()[0]);
        preMenu.add(grayItem);
        preMenu.add(binaryItem);
        preMenu.add(imageTransItem);

        JMenuItem eroItem = new JMenuItem();
        eroItem.setText("��ʴ");
        eroItem.addActionListener(ErosionButton().getActionListeners()[0]);
        JMenuItem expandItem = new JMenuItem();
        expandItem.setText("����");
        expandItem.addActionListener(ExpandButton().getActionListeners()[0]);
        JMenuItem avgItem = new JMenuItem();
        avgItem.setText("��ֵ�˲�");
        avgItem.addActionListener(AvgFilterButton().getActionListeners()[0]);
        JMenuItem eightItem = new JMenuItem();
        eightItem.setText("�˵���ͨ��ȥ��");
        eightItem.addActionListener(EightDenoiseButton().getActionListeners()[0]);
        JMenuItem medItem = new JMenuItem();
        medItem.setText("��ֵ�˲�");
        medItem.addActionListener(MedFilterButton().getActionListeners()[0]);
        denoiseMenu.add(eroItem);
        denoiseMenu.add(expandItem);
        denoiseMenu.add(avgItem);
        denoiseMenu.add(medItem);
        denoiseMenu.add(eightItem);

        JMenuItem edgeEroItem = new JMenuItem();
        edgeEroItem.setText("��ʴ���ͺ��Ե���");
        edgeEroItem.addActionListener(edgeAfterEro);
        JMenuItem edgeAvgItem = new JMenuItem();
        edgeAvgItem.setText("��ֵ�˲����Ե���");
        edgeAvgItem.addActionListener(edgeAfterAvg);
        JMenuItem edgeMedItem = new JMenuItem();
        edgeMedItem.setText("��ֵ�˲����Ե���");
        edgeMedItem.addActionListener(edgeAfterMed);
        JMenuItem edgeEightItem = new JMenuItem();
        edgeEightItem.setText("��ͨ��ȥ����Ե���");
        edgeEightItem.addActionListener(edgeAfterEight);
        edgeMenu.add(edgeEroItem);
        edgeMenu.add(edgeAvgItem);
        edgeMenu.add(edgeMedItem);
        edgeMenu.add(edgeEightItem);

        JMenuItem yproItem = new JMenuItem();
        yproItem.setText("��ֱ����ͶӰ");
        yproItem.addActionListener(YprojectionButton().getActionListeners()[0]);
        shadowMenu.add(yproItem);

        JMenuItem segMenuEro = new JMenuItem();
        segMenuEro.setText("��ʴ���ͺ�ָ�");
        segMenuEro.addActionListener(segAfterEro);
        JMenuItem segMenuAvg = new JMenuItem();
        segMenuAvg.setText("��ֵ�˲���ָ�");
        segMenuAvg.addActionListener(segAfterAvg);
        JMenuItem segMenuMed = new JMenuItem();
        segMenuMed.setText("��ֵ�˲���ָ�");
        segMenuMed.addActionListener(segAfterMed);
        JMenuItem segMenuEight = new JMenuItem();
        segMenuEight.setText("�˵�����ȥ���ָ�");
        segMenuEight.addActionListener(segAfterEight);
        JMenuItem segMenuProjection = new JMenuItem();
        segMenuProjection.setText("ͶӰ���ָ�");
        segMenuProjection.addActionListener(segAfterProjection);
        segMenu.add(segMenuEro);
        segMenu.add(segMenuAvg);
        segMenu.add(segMenuMed);
        segMenu.add(segMenuEight);
        segMenu.add(segMenuProjection);

        JMenuItem clearItem = new JMenuItem();
        clearItem.setText("������");
        clearItem.addActionListener(ClearButton().getActionListeners()[0]);
        clearMenu.add(clearItem);

        JMenuItem recoEroItem = new JMenuItem();
        recoEroItem.setText("��ʴ���ͺ�ʶ��");
        recoEroItem.addActionListener(recoEro);
        JMenuItem recoAvgItem = new JMenuItem();
        recoAvgItem.setText("��ֵ�˲���ʶ��");
        recoAvgItem.addActionListener(recoAvg);
        JMenuItem recoMedItem = new JMenuItem();
        recoMedItem.setText("��ֵȥ���ʶ��");
        recoMedItem.addActionListener(recoMed);
        JMenuItem recoEightItem = new JMenuItem();
        recoEightItem.setText("��ͨ��ȥ���ʶ��");
        recoEightItem.addActionListener(recoEight);
        JMenuItem recoProjectionItem = new JMenuItem();
        recoProjectionItem.setText("ͶӰ����ʶ��");
        recoProjectionItem.addActionListener(recoProjection);
        recoMenu.add(recoEroItem);
        recoMenu.add(recoAvgItem);
        recoMenu.add(recoMedItem);
        recoMenu.add(recoEightItem);
        recoMenu.add(recoProjectionItem);

        segBar.add(openMenu);
        segBar.add(preMenu);
        segBar.add(denoiseMenu);
        segBar.add(edgeMenu);
        segBar.add(shadowMenu);
        segBar.add(segMenu);
        segBar.add(recoMenu);
        segBar.add(clearMenu);

        JLabel imageOriLable = new JLabel("ԭͼΪ��");
        JLabel imageProLable = new JLabel("�����");
        JLabel imageSegLable = new JLabel("�ָ�ͼ��");
        JLabel imageRecoLable = new JLabel("ʶ��");
        JLabel charNumArea = new JLabel("�ַ�������");
        JLabel binaryThreArea = new JLabel("��ֵ����ֵ��");

//        open.setBounds(30, 10, 100, 30);
//        gray.setBounds(130, 10, 100, 30);
//        binary.setBounds(230, 10, 100, 30);
//        erosion.setBounds(330, 10, 100, 30);
//        expand.setBounds(430, 10, 100, 30);
//        getedge.setBounds(530, 10, 100, 30);
//        segmention.setBounds(630, 10, 100, 30);
//        ypro.setBounds(730, 10, 100, 30);
//        avgfilter.setBounds(830, 10, 100, 30);
        nextButton.setBounds(115, 450, 80, 30);
        lastButton.setBounds(35, 450, 80, 30);

//        �˵���
        segBar.setBounds(30, 0, 800, 30);

        imageDis.setHorizontalAlignment(SwingConstants.CENTER);
        imageDis.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageDis.setBounds(90, 85, 200, 80);
        imageOriLable.setBounds(35, 100, 60, 50);
        imagePro.setHorizontalAlignment(SwingConstants.CENTER);
        imagePro.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePro.setBounds(90, 185, 200, 80);
        imageProLable.setBounds(35, 200, 60, 50);
        imageSegLable.setBounds(35, 300, 100, 50);
        imageRecoLable.setBounds(35, 370, 60, 50);
        Single1.setBounds(115, 310, 30, 30);
        Single2.setBounds(165, 310, 30, 30);
        Single3.setBounds(215, 310, 30, 30);
        Single4.setBounds(265, 310, 30, 30);
        Single5.setBounds(315, 310, 30, 30);
        Single6.setBounds(365, 310, 30, 30);
        Single1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Single2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Single3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Single4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Single5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Single6.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        textReco.setBounds(85, 380, 120, 30);
        filePath.setBounds(85, 480, 1000, 30);

//        �ַ������������
        charNumArea.setBounds(320, 40, 80, 30);
        charNumInput.setBounds(400, 40, 120, 30);
        inputCharNumButton.setBounds(520, 40, 60, 30);
//        ��ֵ����ֵ�������
        binaryThreArea.setBounds(35, 40, 80, 30);
        binaryThreInput.setBounds(115, 40, 120, 30);
        inputThreButton.setBounds(235, 40, 60, 30);

//        ���button���
//        add(open);
//        add(gray);
//        add(binary);
//        add(erosion);
//        add(expand);
//        add(getedge);
//        add(segmention);
//        add(ypro);
//        add(avgfilter);
//        add(segGroup);
        add(segBar);
        add(nextButton);
        add(lastButton);
        add(inputCharNumButton);
        add(inputThreButton);

//        ������
        add(imageOriLable);
        add(imageProLable);
        add(imageSegLable);
        add(imageRecoLable);
        add(imageDis);
        add(imagePro);
        add(Single1);
        add(Single1);
        add(Single2);
        add(Single3);
        add(Single4);
        add(Single5);
        add(Single6);
        add(textReco);
        add(filePath);
        add(charNumInput);
        add(charNumArea);
        add(binaryThreArea);
        add(binaryThreInput);

        setLayout(null);
        pack();
        setVisible(true);
        setSize(900, 600);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null, "�Ƿ�Ҫɾ��������ʱ�ļ���", "ɾ��ȷ��", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (result == JOptionPane.YES_OPTION) {
                    ImageProcess imageDelete = new ImageProcess();
                    imageDelete.DeleteFiles(new File("D:\\JaveDev\\Graduation\\imagehandlededge_ero"));
                }
                dispose();
            }
        });
    }

    public static void main(String[] args) {
        RecoInterface rinterface = new RecoInterface();
        rinterface.setTitle("��֤��ʶ��");
    }
}
