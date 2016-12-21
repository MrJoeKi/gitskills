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
    public JTextField textReco = new JTextField();
    public BufferedImage image = null;
    public ImageProcess imageProcess = null;
    public File file = null;
    public File[] files = null;
    public int indexFiles = 0;


    private JButton OpenButton() {
        JButton open = new JButton();
        open.setText("打开文件");
        open.setSize(80, 40);
        open.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       JFileChooser jFileChooser = new JFileChooser();
                                       jFileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                                       jFileChooser.showOpenDialog(getContentPane());
                                       file = jFileChooser.getSelectedFile();
                                       String[] path ={
                                               "D:\\JaveDev\\Graduation\\imagehandled\\single_ero_normal",
                                               "D:\\JaveDev\\Graduation\\imagehandled\\single_avg_normal"
                                       };

                                       for (int j = 0; j < path.length; j++) {
                                           File fileDir = new File(path[j]);
                                           String[] fileArray = fileDir.list();
                                           for (int i = 0; i < fileArray.length; i++) {
                                               File temp = new File(path[j] + "\\" + fileArray[i]);
                                               temp.delete();
                                           }
                                       }
                                       Single1.setIcon(null);
                                       Single2.setIcon(null);
                                       Single3.setIcon(null);
                                       Single4.setIcon(null);
                                       Single5.setIcon(null);
                                       Single6.setIcon(null);
                                       if (file == null) {
                                           imageDis.setIcon(null);
                                           imagePro.setIcon(null);
                                       } else {
                                           if (file.getName().matches(".+?\\.jpg")) {
                                               try {
                                                   files = null;
                                                   image = ImageIO.read(file);
                                                   imageProcess = new ImageProcess(file, image);
                                                   BufferedImage imageGray = imageProcess.Gray(image);
                                                   BufferedImage imageBinary = imageProcess.Binary(imageGray);
                                                   BufferedImage imageErosion = imageProcess.Erosion(imageBinary);
                                                   BufferedImage imageExpand = imageProcess.Expand(imageErosion);
                                                   BufferedImage imageEdge = imageProcess.Segmentation(imageExpand,"\\edge_ero");
                                                   int[][] alledge = imageProcess.GetSingleChar(imageEdge);
                                                   imageProcess.PrintAfterSeg(imageExpand, alledge, "\\single_ero");
                                                   imageProcess.YProjection(imageExpand);

                                                   BufferedImage imageAvgFilter = imageProcess.AvrFiltering(imageBinary);
                                                   BufferedImage imageEdgeAvg = imageProcess.Segmentation(imageAvgFilter,"\\edge_avg");
                                                   int[][] alledgeAvg = imageProcess.GetSingleChar(imageEdgeAvg);
                                                   imageProcess.PrintAfterSeg(imageAvgFilter, alledgeAvg, "\\single_avg");
                                                   imageProcess.YProjection(imageAvgFilter);
                                                   ImageIcon icon_pro = new ImageIcon(imageGray);
                                                   icon_pro.setImage(icon_pro.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                                   imagePro.setIcon(icon_pro);
                                               } catch (IOException e1) {
                                                   e1.printStackTrace();
                                               }
                                           } else {
                                               JOptionPane.showMessageDialog(getContentPane(), "请输入以\".jpg\"拓展名的图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                                           }
                                           ImageIcon icon = new ImageIcon(image);
                                           icon.setImage(icon.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                           imageDis.setIcon(icon);
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
                                       jFileChooser.showOpenDialog(getContentPane());
                                       File directory = jFileChooser.getSelectedFile();
                                       String direPath = directory.getAbsolutePath();
                                       files = new File(direPath).listFiles();
                                       file = null;
                                       String[] path ={
                                               "D:\\JaveDev\\Graduation\\imagehandled\\single_ero_normal",
                                               "D:\\JaveDev\\Graduation\\imagehandled\\single_avg_normal"
                                       };

                                       for (int j = 0; j < path.length; j++) {
                                           File fileDir = new File(path[j]);
                                           String[] fileArray = fileDir.list();
                                           for (int i = 0; i < fileArray.length; i++) {
                                               File temp = new File(path[j] + "\\" + fileArray[i]);
                                               temp.delete();
                                           }
                                       }
                                       Single1.setIcon(null);
                                       Single2.setIcon(null);
                                       Single3.setIcon(null);
                                       Single4.setIcon(null);
                                       Single5.setIcon(null);
                                       Single6.setIcon(null);
                                       if (files[indexFiles] == null) {
                                           imageDis.setIcon(null);
                                           imagePro.setIcon(null);
                                       } else {
                                           if (files[indexFiles].getName().matches(".+?\\.jpg")) {
                                               try {
                                                   image = ImageIO.read(files[indexFiles]);
                                                   imageProcess = new ImageProcess(files[indexFiles], image);
                                                   BufferedImage imageGray = imageProcess.Gray(image);
                                                   BufferedImage imageBinary = imageProcess.Binary(imageGray);
                                                   BufferedImage imageErosion = imageProcess.Erosion(imageBinary);
                                                   BufferedImage imageExpand = imageProcess.Expand(imageErosion);
                                                   BufferedImage imageEdge = imageProcess.Segmentation(imageExpand,"\\edge_ero");
                                                   int[][] alledge = imageProcess.GetSingleChar(imageEdge);
                                                   imageProcess.PrintAfterSeg(imageExpand, alledge, "\\single_ero");
                                                   imageProcess.YProjection(imageExpand);

                                                   BufferedImage imageAvgFilter = imageProcess.AvrFiltering(imageBinary);
                                                   BufferedImage imageEdgeAvg = imageProcess.Segmentation(imageAvgFilter,"\\edge_avg");
                                                   int[][] alledgeAvg = imageProcess.GetSingleChar(imageEdgeAvg);
                                                   imageProcess.PrintAfterSeg(imageAvgFilter, alledgeAvg, "\\single_avg");
                                                   imageProcess.YProjection(imageAvgFilter);
                                                   ImageIcon icon_pro = new ImageIcon(imageGray);
                                                   icon_pro.setImage(icon_pro.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                                   imagePro.setIcon(icon_pro);
                                               } catch (IOException e1) {
                                                   e1.printStackTrace();
                                               }
                                           } else {
                                               JOptionPane.showMessageDialog(getContentPane(), "请输入以\".jpg\"拓展名的图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                                           }
                                           ImageIcon icon = new ImageIcon(image);
                                           icon.setImage(icon.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                           imageDis.setIcon(icon);
                                       }
                                   }
                               }

        );
        return openFiles;
    }

    private JButton NextFilesButton() {
        JButton openFiles = new JButton();
        openFiles.setText("下一个");
        openFiles.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (files == null) {
                                                JOptionPane.showMessageDialog(getContentPane(), "请先打开文件夹", "错误提示", JOptionPane.WARNING_MESSAGE);
                                            } else {
                                                if (indexFiles >= files.length - 1) {
                                                    JOptionPane.showMessageDialog(getContentPane(), "已经是最后一个文件！", "错误提示", JOptionPane.WARNING_MESSAGE);
                                                } else {
                                                    imagePro.setIcon(null);
                                                    textReco.setText("");
                                                    indexFiles++;
                                                    String[] path = {
                                                            "D:\\JaveDev\\Graduation\\imagehandled\\single_ero_normal",
                                                            "D:\\JaveDev\\Graduation\\imagehandled\\single_avg_normal"
                                                    };
                                                    for (int j = 0; j < path.length; j++) {
                                                        File fileDir = new File(path[j]);
                                                        String[] fileArray = fileDir.list();
                                                        for (int i = 0; i < fileArray.length; i++) {
                                                            File temp = new File(path[j] + "\\" + fileArray[i]);
                                                            temp.delete();
                                                        }
                                                    }
                                                    Single1.setIcon(null);
                                                    Single2.setIcon(null);
                                                    Single3.setIcon(null);
                                                    Single4.setIcon(null);
                                                    Single5.setIcon(null);
                                                    Single6.setIcon(null);
                                                    if (files[indexFiles] == null) {
                                                        imageDis.setIcon(null);
                                                        imagePro.setIcon(null);
                                                    } else {
                                                        if (files[indexFiles].getName().matches(".+?\\.jpg")) {
                                                            try {
                                                                image = ImageIO.read(files[indexFiles]);
                                                                imageProcess = new ImageProcess(files[indexFiles], image);
                                                                BufferedImage imageGray = imageProcess.Gray(image);
                                                                BufferedImage imageBinary = imageProcess.Binary(imageGray);
                                                                BufferedImage imageErosion = imageProcess.Erosion(imageBinary);
                                                                BufferedImage imageExpand = imageProcess.Expand(imageErosion);
                                                                BufferedImage imageEdge = imageProcess.Segmentation(imageExpand, "\\edge_ero");
                                                                int[][] alledge = imageProcess.GetSingleChar(imageEdge);
                                                                imageProcess.PrintAfterSeg(imageExpand, alledge, "\\single_ero");
                                                                imageProcess.YProjection(imageExpand);

                                                                BufferedImage imageAvgFilter = imageProcess.AvrFiltering(imageBinary);
                                                                BufferedImage imageEdgeAvg = imageProcess.Segmentation(imageAvgFilter, "\\edge_avg");
                                                                int[][] alledgeAvg = imageProcess.GetSingleChar(imageEdgeAvg);
                                                                imageProcess.PrintAfterSeg(imageAvgFilter, alledgeAvg, "\\single_avg");
                                                                imageProcess.YProjection(imageAvgFilter);
                                                                ImageIcon icon_pro = new ImageIcon(imageGray);
                                                                icon_pro.setImage(icon_pro.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                                                imagePro.setIcon(icon_pro);
                                                            } catch (IOException e1) {
                                                                e1.printStackTrace();
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(getContentPane(), "请输入以\".jpg\"拓展名的图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                                                        }
                                                        ImageIcon icon = new ImageIcon(image);
                                                        icon.setImage(icon.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                                        imageDis.setIcon(icon);
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
        openFiles.setText("上一个");
        openFiles.addActionListener(new ActionListener() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            if (files == null) {
                                                JOptionPane.showMessageDialog(getContentPane(), "请先打开文件夹", "错误提示", JOptionPane.WARNING_MESSAGE);
                                            } else {
                                                if (indexFiles < 1) {
                                                    JOptionPane.showMessageDialog(getContentPane(), "已经是第一个文件！", "错误提示", JOptionPane.WARNING_MESSAGE);
                                                } else {
                                                    imagePro.setIcon(null);
                                                    textReco.setText("");
                                                    indexFiles--;
                                                    String[] path = {
                                                            "D:\\JaveDev\\Graduation\\imagehandled\\single_ero_normal",
                                                            "D:\\JaveDev\\Graduation\\imagehandled\\single_avg_normal"
                                                    };
                                                    for (int j = 0; j < path.length; j++) {
                                                        File fileDir = new File(path[j]);
                                                        String[] fileArray = fileDir.list();
                                                        for (int i = 0; i < fileArray.length; i++) {
                                                            File temp = new File(path[j] + "\\" + fileArray[i]);
                                                            temp.delete();
                                                        }
                                                    }
                                                    Single1.setIcon(null);
                                                    Single2.setIcon(null);
                                                    Single3.setIcon(null);
                                                    Single4.setIcon(null);
                                                    Single5.setIcon(null);
                                                    Single6.setIcon(null);
                                                    if (files[indexFiles] == null) {
                                                        imageDis.setIcon(null);
                                                        imagePro.setIcon(null);
                                                    } else {
                                                        if (files[indexFiles].getName().matches(".+?\\.jpg")) {
                                                            try {
                                                                image = ImageIO.read(files[indexFiles]);
                                                                imageProcess = new ImageProcess(files[indexFiles], image);
                                                                BufferedImage imageGray = imageProcess.Gray(image);
                                                                BufferedImage imageBinary = imageProcess.Binary(imageGray);
                                                                BufferedImage imageErosion = imageProcess.Erosion(imageBinary);
                                                                BufferedImage imageExpand = imageProcess.Expand(imageErosion);
                                                                BufferedImage imageEdge = imageProcess.Segmentation(imageExpand, "\\edge_ero");
                                                                int[][] alledge = imageProcess.GetSingleChar(imageEdge);
                                                                imageProcess.PrintAfterSeg(imageExpand, alledge, "\\single_ero");
                                                                imageProcess.YProjection(imageExpand);

                                                                BufferedImage imageAvgFilter = imageProcess.AvrFiltering(imageBinary);
                                                                BufferedImage imageEdgeAvg = imageProcess.Segmentation(imageAvgFilter, "\\edge_avg");
                                                                int[][] alledgeAvg = imageProcess.GetSingleChar(imageEdgeAvg);
                                                                imageProcess.PrintAfterSeg(imageAvgFilter, alledgeAvg, "\\single_avg");
                                                                imageProcess.YProjection(imageAvgFilter);
                                                                ImageIcon icon_pro = new ImageIcon(imageGray);
                                                                icon_pro.setImage(icon_pro.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                                                imagePro.setIcon(icon_pro);
                                                            } catch (IOException e1) {
                                                                e1.printStackTrace();
                                                            }
                                                        } else {
                                                            JOptionPane.showMessageDialog(getContentPane(), "请输入以\".jpg\"拓展名的图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                                                        }
                                                        ImageIcon icon = new ImageIcon(image);
                                                        icon.setImage(icon.getImage().getScaledInstance(image.getWidth(), image.getHeight(), Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                                        imageDis.setIcon(icon);
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
        gray.setText("灰度化");
        gray.setSize(80, 40);
        gray.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       if (file == null && files == null) {
                                           imagePro.setIcon(null);
                                           JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                                       } else {
                                           if (files != null){
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
        binary.setText("二值化");
        binary.setSize(80, 40);
        binary.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent e) {
                                         if (file == null && files == null) {
                                             imagePro.setIcon(null);
                                             JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                                         } else {
                                             if (files != null) {
                                                 String path = imageProcess.path_process + "\\binary\\" + files[indexFiles].getName().replace(".jpg", "") + "_binary.bmp";
                                                 BufferedImage imageBinary = null;
                                                 try {
                                                     imageBinary = ImageIO.read(new File(path));
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

        );
        return binary;
    }

    private JButton ErosionButton() {
        JButton erosion = new JButton();
        erosion.setText("腐蚀");
        erosion.setSize(80, 40);
        erosion.addActionListener(new ActionListener() {
                                      @Override
                                      public void actionPerformed(ActionEvent e) {
                                          if (file == null && files == null) {
                                              imagePro.setIcon(null);
                                              JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
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
        expand.setText("膨胀");
        expand.setSize(80, 40);
        expand.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent e) {
                                         if (file == null && files == null) {
                                             imagePro.setIcon(null);
                                             JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
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
                                     }
                                 }
        );
        return expand;
    }

    private JButton GetEdgeButton() {
        JButton getedge = new JButton();
        getedge.setText("边缘检测");
        getedge.setSize(80, 40);
        getedge.addActionListener(new ActionListener() {
                                              @Override
                                              public void actionPerformed(ActionEvent e) {
                                                  if (file == null && files == null) {
                                                      imagePro.setIcon(null);
                                                      JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                                                  } else {
                                                      if (files != null) {
                                                          String path = imageProcess.path_process + "\\getedge\\" + files[indexFiles].getName().replace(".jpg", "") + "_edgedete.bmp";
                                                          BufferedImage imageGetedge = null;
                                                          try {
                                                              imageGetedge = ImageIO.read(new File(path));
                                                          } catch (IOException e1) {
                                                              e1.printStackTrace();
                                                          }
                                                          ImageIcon icon = new ImageIcon(imageGetedge);
                                                          imagePro.setIcon(icon);
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
                                              }
                                          }

        );
        return getedge;
    }

    private JButton SegmentionButton() {
        JButton segmention = new JButton();
        segmention.setText("分割");
        segmention.setSize(80, 40);
        segmention.addActionListener(new ActionListener() {
                                         @Override
                                         public void actionPerformed(ActionEvent e) {
                                             JLabel[] allSeg = {Single1, Single2, Single3, Single4, Single5, Single6};
                                             if (file == null && files == null) {
                                                 Single1.setIcon(null);
                                                 Single2.setIcon(null);
                                                 Single3.setIcon(null);
                                                 Single4.setIcon(null);
                                                 Single5.setIcon(null);
                                                 Single6.setIcon(null);
                                                 JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                                                 String path = "D:\\JaveDev\\Graduation\\imagehandled\\single_ero_normal";
                                                 File fileDir = new File(path);
                                                 String[] fileArray = fileDir.list();
                                                 for (int i = 0; i < fileArray.length; i++) {
                                                     File temp = new File(path + "\\" + fileArray[i]);
                                                     temp.delete();
                                                 }
                                             } else {
                                                 String path = imageProcess.path_process + "\\single_ero_normal";
                                                 File fileDir = new File(path);
                                                 String[] fileArray = fileDir.list();
                                                 BufferedImage imageSingle = null;
                                                 for (int i = 0; i < fileArray.length; i++) {
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
                                     }

        );
        return segmention;
    }

    private JButton AvgFilterButton() {
        JButton avg = new JButton();
        avg.setText("均值滤波");
        avg.setSize(80, 40);
        avg.addActionListener(new ActionListener() {
                                  @Override
                                  public void actionPerformed(ActionEvent e) {
                                      if (file == null && files == null) {
                                          imagePro.setIcon(null);
                                          JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
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
                                          }
                                      }
                                  }
                              }
        );
        return avg;
    }

    private JButton SegAfterAvg() {
        JButton avg = new JButton();
        avg.setText("均值滤波后分割");
        avg.setSize(80, 40);
        avg.addActionListener(new ActionListener() {
                                  @Override
                                  public void actionPerformed(ActionEvent e) {
                                      if (file == null && files == null) {
                                          imagePro.setIcon(null);
                                          JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
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
                                          }
                                      }
                                  }
                              }
        );
        return avg;
    }

    private JButton SegAfterEro() {
        JButton avg = new JButton();
        avg.setText("腐蚀膨胀后分割");
        avg.setSize(80, 40);
        avg.addActionListener(new ActionListener() {
                                  @Override
                                  public void actionPerformed(ActionEvent e) {
                                      if (file == null && files == null) {
                                          imagePro.setIcon(null);
                                          JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
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
                                          }
                                      }
                                  }
                              }
        );
        return avg;
    }

    private JButton YprojectionButton() {
        JButton ypro = new JButton();
        ypro.setText("Y竖直投影");
        ypro.setSize(80, 40);
        ypro.addActionListener(new ActionListener() {
                                           @Override
                                           public void actionPerformed(ActionEvent e) {
                                               if (file == null && files == null) {
                                                   imagePro.setIcon(null);
                                                   JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                                               } else {
                                                   if (files != null) {
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
        clearPanel.setText("清空面板");
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
                                         }
                                     }
        );
        return clearPanel;
    }

    ActionListener segAfterAvg = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            JLabel[] allSeg = {Single1, Single2, Single3, Single4, Single5, Single6};
            if (file == null && files == null) {
                Single1.setIcon(null);
                Single2.setIcon(null);
                Single3.setIcon(null);
                Single4.setIcon(null);
                Single5.setIcon(null);
                Single6.setIcon(null);
                JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                String path = "D:\\JaveDev\\Graduation\\imagehandled\\single_avg_normal";
                File fileDir = new File(path);
                String[] fileArray = fileDir.list();
                for (int i = 0; i < fileArray.length; i++) {
                    File temp = new File(path + "\\" + fileArray[i]);
                    temp.delete();
                }
            } else {
                String path = imageProcess.path_process + "\\single_avg_normal";
                File fileDir = new File(path);
                String[] fileArray = fileDir.list();
                BufferedImage imageSingle = null;
                for (int i = 0; i < fileArray.length; i++) {
                    try {
                        imageSingle = ImageIO.read(new File(path + "\\" + fileArray[i]));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageSingle);
                    allSeg[i].setIcon(icon);
                    allSeg[i].setHorizontalAlignment(JLabel.CENTER);
                }
            }
        }
    };

    ActionListener segAfterEro = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            JLabel[] allSeg = {Single1, Single2, Single3, Single4, Single5, Single6};
            if (file == null && files == null) {
                Single1.setIcon(null);
                Single2.setIcon(null);
                Single3.setIcon(null);
                Single4.setIcon(null);
                Single5.setIcon(null);
                Single6.setIcon(null);
                JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                String path = "D:\\JaveDev\\Graduation\\imagehandled\\single_ero_normal";
                File fileDir = new File(path);
                String[] fileArray = fileDir.list();
                for (int i = 0; i < fileArray.length; i++) {
                    File temp = new File(path + "\\" + fileArray[i]);
                    temp.delete();
                }
            } else {
                String path = imageProcess.path_process + "\\single_ero_normal";
                File fileDir = new File(path);
                String[] fileArray = fileDir.list();
                BufferedImage imageSingle = null;
                for (int i = 0; i < fileArray.length; i++) {
                    try {
                        imageSingle = ImageIO.read(new File(path + "\\" + fileArray[i]));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageSingle);
                    allSeg[i].setIcon(icon);
                    allSeg[i].setHorizontalAlignment(JLabel.CENTER);
                }
            }
        }
    };

    ActionListener edgeAfterAvg = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (file == null && files == null) {
                imagePro.setIcon(null);
                JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
            } else {
                if (files != null) {
                    String path = imageProcess.path_process + "\\edge_avg\\" + files[indexFiles].getName().replace(".jpg", "") + "_edgedete.bmp";
                    BufferedImage imageGetedge = null;
                    try {
                        imageGetedge = ImageIO.read(new File(path));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageGetedge);
                    imagePro.setIcon(icon);
                } else {
                    String path = imageProcess.path_process + "\\edge_avg\\" + file.getName().replace(".jpg", "") + "_edgedete.bmp";
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
        }
    };

    ActionListener edgeAfterEro = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (file == null && files == null) {
                imagePro.setIcon(null);
                JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
            } else {
                if (files != null) {
                    String path = imageProcess.path_process + "\\edge_ero\\" + files[indexFiles].getName().replace(".jpg", "") + "_edgedete.bmp";
                    BufferedImage imageGetedge = null;
                    try {
                        imageGetedge = ImageIO.read(new File(path));
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    ImageIcon icon = new ImageIcon(imageGetedge);
                    imagePro.setIcon(icon);
                } else {
                    String path = imageProcess.path_process + "\\edge_ero\\" + file.getName().replace(".jpg", "") + "_edgedete.bmp";
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
        }
    };

    ActionListener recoAvg = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (file == null && files == null) {
                imagePro.setIcon(null);
                JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
            } else {
                String path = imageProcess.path_process + "\\single_avg_normal\\";
                File[] filesReco = new File(path).listFiles();
                ImageProcess imageProcessReco = new ImageProcess();
                String result = "";
                for (int i = 0; i < filesReco.length; i++){
                    result += imageProcessReco.MatchTemp(filesReco[i]);
                }
                textReco.setText(result);
            }
        }
    };

    ActionListener recoEro = new ActionListener() {
        public void actionPerformed(ActionEvent event) {
            if (file == null && files == null) {
                imagePro.setIcon(null);
                JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
            } else {
                String path = imageProcess.path_process + "\\single_ero_normal\\";
                File[] filesReco = new File(path).listFiles();
                ImageProcess imageProcessReco = new ImageProcess();
                String result = "";
                for (int i = 0; i < filesReco.length; i++){
                    result += imageProcessReco.MatchTemp(filesReco[i]);
                }
                textReco.setText(result);
            }
        }
    };

    public RecoInterface() {
        JButton open = OpenButton();
        JButton gray = GrayButton();
        JButton binary = BinaryButton();
        JButton erosion = ErosionButton();
        JButton expand = ExpandButton();
        JButton getedge = GetEdgeButton();
        JButton ypro = YprojectionButton();
        JButton segmention = SegmentionButton();
        JButton avgfilter = AvgFilterButton();
        JButton nextButton = NextFilesButton();
        JButton lastButton = LastFilesButton();

//        JComboBox<JButton> segGroup = new JComboBox<JButton>();
        JMenuBar segBar = new JMenuBar();
        JMenu segMenu = new JMenu("  分割  ");
        JMenu openMenu = new JMenu("打开文件");
        JMenu preMenu = new JMenu(" 预处理 ");
        JMenu denoiseMenu = new JMenu("  去噪  ");
        JMenu shadowMenu = new JMenu("  投影  ");
        JMenu edgeMenu = new JMenu("边缘检测");
        JMenu clearMenu = new JMenu("  清空  ");
        JMenu recoMenu = new JMenu("  识别  ");

        JMenuItem segMenuOne = new JMenuItem();
        JMenuItem segMenuTwo = new JMenuItem();
        segMenuOne.setText("均值滤波后分割");
        segMenuTwo.setText("腐蚀膨胀后分割");
        segMenuOne.addActionListener(segAfterAvg);
        segMenuTwo.addActionListener(segAfterEro);
        segMenu.add(segMenuOne);
        segMenu.add(segMenuTwo);

        JMenuItem openItem = new JMenuItem();
        openItem.setText("打开文件");
        openItem.addActionListener(open.getActionListeners()[0]);
        openMenu.add(openItem);
        JMenuItem openFilesItem = new JMenuItem();
        openFilesItem.setText("打开文件夹");
        openFilesItem.addActionListener(OpenFilesButton().getActionListeners()[0]);
        openMenu.add(openFilesItem);

        JMenuItem grayItem = new JMenuItem();
        grayItem.setText("灰度化");
        grayItem.addActionListener(gray.getActionListeners()[0]);
        JMenuItem binaryItem = new JMenuItem();
        binaryItem.setText("二值化");
        binaryItem.addActionListener(binary.getActionListeners()[0]);
        preMenu.add(grayItem);
        preMenu.add(binaryItem);

        JMenuItem eroItem = new JMenuItem();
        eroItem.setText("腐蚀");
        eroItem.addActionListener(erosion.getActionListeners()[0]);
        JMenuItem expandItem = new JMenuItem();
        expandItem.setText("膨胀");
        expandItem.addActionListener(expand.getActionListeners()[0]);
        JMenuItem avgItem = new JMenuItem();
        avgItem.setText("均值滤波");
        avgItem.addActionListener(avgfilter.getActionListeners()[0]);
        denoiseMenu.add(eroItem);
        denoiseMenu.add(expandItem);
        denoiseMenu.add(avgItem);

        JMenuItem edgeAvgItem = new JMenuItem();
        edgeAvgItem.setText("均值滤波后边缘检测");
        edgeAvgItem.addActionListener(edgeAfterAvg);
        JMenuItem edgeEroItem = new JMenuItem();
        edgeEroItem.setText("腐蚀膨胀后边缘检测");
        edgeEroItem.addActionListener(edgeAfterEro);
        edgeMenu.add(edgeAvgItem);
        edgeMenu.add(edgeEroItem);

        JMenuItem yproItem = new JMenuItem();
        yproItem.setText("竖直方向投影");
        yproItem.addActionListener(ypro.getActionListeners()[0]);
        shadowMenu.add(yproItem);

        JMenuItem clearItem = new JMenuItem();
        clearItem.setText("清空面板");
        clearItem.addActionListener(ClearButton().getActionListeners()[0]);
        clearMenu.add(clearItem);

        JMenuItem recoAvgItem = new JMenuItem();
        recoAvgItem.setText("均值滤波后识别");
        recoAvgItem.addActionListener(recoAvg);
        JMenuItem recoEroItem = new JMenuItem();
        recoEroItem.setText("腐蚀膨胀后识别");
        recoEroItem.addActionListener(recoEro);
        recoMenu.add(recoAvgItem);
        recoMenu.add(recoEroItem);

        segBar.add(openMenu);
        segBar.add(preMenu);
        segBar.add(denoiseMenu);
        segBar.add(edgeMenu);
        segBar.add(shadowMenu);
        segBar.add(segMenu);
        segBar.add(recoMenu);
        segBar.add(clearMenu);

        JLabel imageOriLable = new JLabel("原图为：");
        JLabel imageProLable = new JLabel("处理后：");
        JLabel imageSegLable = new JLabel("分割图像：");
        JLabel imageRecoLable = new JLabel("识别：");

//        open.setBounds(30, 10, 100, 30);
//        gray.setBounds(130, 10, 100, 30);
//        binary.setBounds(230, 10, 100, 30);
//        erosion.setBounds(330, 10, 100, 30);
//        expand.setBounds(430, 10, 100, 30);
//        getedge.setBounds(530, 10, 100, 30);
//        segmention.setBounds(630, 10, 100, 30);
//        ypro.setBounds(730, 10, 100, 30);
//        avgfilter.setBounds(830, 10, 100, 30);
        nextButton.setBounds(115,450,80,30);
        lastButton.setBounds(35,450,80,30);

        segBar.setBounds(30, 50, 800, 30);

        imageDis.setHorizontalAlignment(SwingConstants.CENTER);
        imageDis.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageDis.setBounds(90, 85, 200, 80);
        imageOriLable.setBounds(35, 100, 60, 50);
        imagePro.setHorizontalAlignment(SwingConstants.CENTER);
        imagePro.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePro.setBounds(90, 185, 200, 80);
        imageProLable.setBounds(35, 200, 60, 50);
        imageSegLable.setBounds(35, 300, 100, 50);
        imageRecoLable.setBounds(35,370,60,50);
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
        textReco.setBounds(85,380,120,30);

//        添加button组件
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

//        添加面板
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

        setLayout(null);
        pack();
        setVisible(true);
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        RecoInterface rinterface = new RecoInterface();
        rinterface.setTitle("验证码识别");
    }
}
