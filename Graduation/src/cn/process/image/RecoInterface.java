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
    public BufferedImage image = null;
    public ImageProcess imageProcess = null;
    public File file = null;


    private JButton OpenButton() {
        JButton open = new JButton();
        open.setText("打开文件");
        open.setSize(80, 40);
        open.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       JFileChooser jFileChooser = new JFileChooser();
                                       jFileChooser.showOpenDialog(getContentPane());
                                       file = jFileChooser.getSelectedFile();
                                       String path = "D:\\JaveDev\\Graduation\\imagehandled\\normalization";
                                       File fileDir = new File(path);
                                       String[] fileArray = fileDir.list();
                                       for (int i = 0; i < fileArray.length; i++) {
                                           File temp = new File(path + "\\" + fileArray[i]);
                                           temp.delete();
                                       }
                                       Single1.setIcon(null);
                                       Single2.setIcon(null);
                                       Single3.setIcon(null);
                                       Single4.setIcon(null);
                                       Single5.setIcon(null);
                                       Single6.setIcon(null);
                                       if (file == null) {
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
                                               JOptionPane.showMessageDialog(getContentPane(), "请输入以\".jpg\"拓展名的图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                                           }
                                           ImageIcon icon = new ImageIcon(image);
                                           icon.setImage(icon.getImage().getScaledInstance(image.getWidth(),image.getHeight(),Image.SCALE_DEFAULT));
//                                           icon = new ImageIcon(icon.getImage().getScaledInstance(icon.getIconWidth(), icon.getIconHeight(), Image.SCALE_DEFAULT));
                                           imageDis.setIcon(icon);
                                       }
                                   }
                               }

        );
        return open;
    }

    private JButton GrayButton() {
        JButton gray = new JButton();
        gray.setText("灰度化");
        gray.setSize(80, 40);
        gray.addActionListener(new ActionListener() {
                                   @Override
                                   public void actionPerformed(ActionEvent e) {
                                       if (file == null) {
                                           imagePro.setIcon(null);
                                           JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
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
                                         if (file == null) {
                                             imagePro.setIcon(null);
                                             JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
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
                                          if (file == null) {
                                              imagePro.setIcon(null);
                                              JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
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
        );
        return erosion;
    }

    private JButton ExpandButton() {
        JButton expand = new JButton();
        expand.setText("腐蚀");
        expand.setSize(80, 40);
        expand.addActionListener(new ActionListener() {
                                     @Override
                                     public void actionPerformed(ActionEvent e) {
                                         if (file == null) {
                                             imagePro.setIcon(null);
                                             JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
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
        );
        return expand;
    }

    private JButton GetEdgeButton() {
        JButton getedge = new JButton();
        getedge.setText("边缘检测");
        getedge.setSize(80, 40);
        getedge.addActionListener(new

                                          ActionListener() {
                                              @Override
                                              public void actionPerformed(ActionEvent e) {
                                                  if (file == null) {
                                                      imagePro.setIcon(null);
                                                      JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
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

                                             if (file == null) {
                                                 Single1.setIcon(null);
                                                 Single2.setIcon(null);
                                                 Single3.setIcon(null);
                                                 Single4.setIcon(null);
                                                 Single5.setIcon(null);
                                                 Single6.setIcon(null);
                                                 JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
                                                 String path = "D:\\JaveDev\\Graduation\\imagehandled\\normalization";
                                                 File fileDir = new File(path);
                                                 String[] fileArray = fileDir.list();
                                                 for (int i = 0; i < fileArray.length; i++) {
                                                     File temp = new File(path + "\\" + fileArray[i]);
                                                     temp.delete();
                                                 }
                                             } else {
                                                 String path = imageProcess.path_process + "\\normalization";
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

    private JButton YprojectionButton() {
        JButton ypro = new JButton();
        ypro.setText("Y竖直投影");
        ypro.setSize(80, 40);
        ypro.addActionListener(new

                                       ActionListener() {
                                           @Override
                                           public void actionPerformed(ActionEvent e) {
                                               if (file == null) {
                                                   imagePro.setIcon(null);
                                                   JOptionPane.showMessageDialog(getContentPane(), "请先打开图片文件", "错误提示", JOptionPane.WARNING_MESSAGE);
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
                                         }
                                     }
        );
        return clearPanel;
    }
//    imageOriginal.setVisible(true);
//    imageOriginal.setEnabled(true);
//    imageOriginal.setSize(300,120);
//
//    add(open);
    public RecoInterface(){
        JButton open = OpenButton();
        JButton gray = GrayButton();
        JButton binary = BinaryButton();
        JButton erosion = ErosionButton();
        JButton expand = ExpandButton();
        JButton getedge = GetEdgeButton();
        JButton ypro = YprojectionButton();
        JButton segmention = SegmentionButton();
        JLabel imageOriLable = new JLabel("原图为：");
        JLabel imageProLable = new JLabel("处理后：");
        JLabel imageSegLable = new JLabel("分割图像：");
        
        open.setBounds(30, 10, 100, 30);
        gray.setBounds(130, 10, 100, 30);
        binary.setBounds(230, 10, 100, 30);
        erosion.setBounds(330, 10, 100, 30);
        expand.setBounds(430, 10, 100, 30);
        getedge.setBounds(530, 10, 100, 30);
        segmention.setBounds(630, 10, 100, 30);
        ypro.setBounds(730, 10, 100, 30);

        imageDis.setHorizontalAlignment(SwingConstants.CENTER);
        imageDis.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imageDis.setBounds(90,85,200,80);
        imageOriLable.setBounds(35,100,60,50);
        imagePro.setHorizontalAlignment(SwingConstants.CENTER);
        imagePro.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        imagePro.setBounds(90 ,185,200,80);
        imageProLable.setBounds(35,200,60,50);
        imageSegLable.setBounds(35,300,100,50);
        Single1.setBounds(115,310,30,30);
        Single2.setBounds(165,310,30,30);
        Single3.setBounds(215,310,30,30);
        Single4.setBounds(265,310,30,30);
        Single5.setBounds(315,310,30,30);
        Single6.setBounds(365,310,30,30);
        Single1.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Single2.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Single3.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Single4.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Single5.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        Single6.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        add(open);
        add(gray);
        add(binary);
        add(erosion);
        add(expand);
        add(getedge);
        add(segmention);
        add(ypro);

        add(imageOriLable);
        add(imageProLable);
        add(imageSegLable);
        add(imageDis);
        add(imagePro);
        add(Single1);
        add(Single1);
        add(Single2);
        add(Single3);
        add(Single4);
        add(Single5);
        add(Single6);


        setLayout(null);
        pack();
        setVisible(true);
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public static void main(String[] args) {
        RecoInterface rinterface = new RecoInterface();
        rinterface.setTitle("验证码识别");
    }
}
