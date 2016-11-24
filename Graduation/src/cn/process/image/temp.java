//package cn.image.plusmulti;
//
//import java.awt.*;
//import java.awt.event.ActionEvent;
//import java.awt.event.ActionListener;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.IOException;
//import java.net.*;
//
//import javax.imageio.ImageIO;
//import javax.swing.DefaultListModel;
//import javax.swing.JButton;
//import javax.swing.JFileChooser;
//import javax.swing.JFormattedTextField;
//import javax.swing.JFrame;
//import javax.swing.JLabel;
//import javax.swing.JList;
//import javax.swing.JPanel;
//import javax.swing.JScrollPane;
//import javax.swing.JTextArea;
//import javax.swing.JTextField;
//import javax.swing.WindowConstants;
//
//import cn.image.plusmulti.Imageread;
//import cn.image.plusmulti.Fileopen.CanvasPanel;
//
//
//
//public class Fileopen extends JFrame {
//
//    BufferedImage bi = null;
//    BufferedImage pro_aft = null;
//    private int Delay_time = 1000;
//
//    public JButton FileChooser(JTextField filename, JPanel jTextArea,JPanel pro_after){
//        JButton button_File = new JButton();
//        Imageread imageRead = new Imageread();
//
//        JFileChooser fileChooser = new JFileChooser();
//        button_File.setText("打开文件");
//
//        button_File.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                int i = fileChooser.showOpenDialog(getContentPane());
//                if (i == JFileChooser.APPROVE_OPTION) {
//                    File file = fileChooser.getSelectedFile();
//                    // BufferedImage bi = null;
//                    try {
//                        bi = (BufferedImage) ImageIO.read(file);
//                        pro_aft = (BufferedImage) ImageIO.read(file);
//                    } catch (IOException e1) {
//                        // TODO Auto-generated catch block
//                        e1.printStackTrace();
//                    }
//
//                    jTextArea.updateUI();
//                    pro_after.updateUI();
//                    jTextArea.removeAll();
//                    pro_after.removeAll();
//
//                    int num = imageRead.NumPrint(bi);
//                    filename.setText(Integer.toString(num));
//                    try {
//                        jTextArea.add(new CanvasPanel(imageRead.ImageProcess(bi)));
//                    } catch (IOException e1) {
//                        // TODO Auto-generated catch block
//                        e1.printStackTrace();
//                    }
//                    pro_after.add(new CanvasPanel(pro_aft));
//                    // repaint();
//
//                }
//            }
//        });
//        return button_File;
//    }
//
//    public JButton DireChooser(DefaultListModel items,JTextField textarea,JPanel jTextArea,JPanel pro_af){
//        JButton button_Dire = new JButton();
//        Imageread imageRead = new Imageread();
//
//        JFileChooser Direchooser = new JFileChooser();
//        button_Dire.setText("打开目录");
//        button_Dire.addActionListener(new ActionListener() {
//
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                Direchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
//                int i = Direchooser.showOpenDialog(getContentPane());
//                if (i == JFileChooser.APPROVE_OPTION) {
//                    File files = Direchooser.getSelectedFile();
//                    File filelist = new File(files.getAbsolutePath());
//                    File[] filename = filelist.listFiles();
//
//                    new Thread(new Runnable() {
//                        public void run() {
//                            for (int j = 0; j < filename.length; j++) {
//
//                                try {
//                                    bi = (BufferedImage) ImageIO.read(filename[j]);
//
//                                    pro_aft = (BufferedImage) ImageIO.read(filename[j]);
//                                    Print(imageRead.ImageProcess(bi),pro_aft, jTextArea, pro_af, imageRead,items,textarea);
//                                } catch (IOException e1) {
//
//                                    e1.printStackTrace();
//                                }
//                            }
//                        }
//                    }).start();
//
//                }
//            }
//        });
//        return button_Dire;
//    }
//
//    public void Print(BufferedImage bi,BufferedImage pro_aft, JPanel jTextArea, JPanel pro_af,Imageread imageRead,DefaultListModel items,JTextField filename) {
//        int num = imageRead.NumPrint(bi);
//
//        items.addElement(num);
//        filename.setText(Integer.toString(num));
//        JPanel jPanel = new CanvasPanel(bi);
//        JPanel pro = new CanvasPanel(pro_aft);
//        jTextArea.add(jPanel);
//        pro_af.add(pro);
//
//        jTextArea.updateUI();
//        pro_af.updateUI();
//        try {
//
//            Thread.currentThread().sleep(Delay_time);
//            jTextArea.removeAll();
//            pro_af.removeAll();
//        } catch (InterruptedException ex) {
//
//        }
//    }
//
//    class CanvasPanel extends JPanel {
//        BufferedImage bi;
//
//        public CanvasPanel(BufferedImage image) {
//            this.bi = image;
//        }
//
//        public void paintComponent(Graphics g) {
//            super.paintComponent(g);
//            Graphics2D g2 = (Graphics2D) g;
//            g2.drawImage(bi, 0, 0, this);
//            setSize(86, 31);
//        }
//    }
//
//    public Fileopen (String title) {
//        Container container = getContentPane();
//        JTextField filename = new JTextField();
//        DefaultListModel items = new DefaultListModel();
//        JList jl = new JList(items);
//        JScrollPane js = new JScrollPane(jl);
//        JPanel jTextArea = new JPanel();
//        JPanel pro_aft = new JPanel();
//        JLabel jlLabel1 = new JLabel("处理之前:");
//        JLabel jlLabel2 = new JLabel("处理之后：");
//        JLabel jlLabel3 = new JLabel("识别数字为：");
//        Imageread imageRead = new Imageread();
//
//        JButton Filechooser = FileChooser(filename, jTextArea,pro_aft);
//        JButton Direchooser = DireChooser(items, filename,jTextArea,pro_aft);
//
//        Filechooser.setBounds(60, 10, 100, 30);
//        Direchooser.setBounds(240, 10, 100, 30);
//        pro_aft.setBounds(-30, 80, 200, 40);
//        jTextArea.setBounds(150, 80, 200, 40);
//        jlLabel1.setBounds(80, 50, 100, 30);
//        jlLabel2.setBounds(260, 50, 100, 30);
//        filename.setBounds(150, 150, 100, 30);
//        jlLabel3.setBounds(70, 150, 100, 30);
//
//        add(Filechooser);
//        add(filename);
//        add(Direchooser);
//        add(js);
//        add(jTextArea);
//        add(pro_aft);
//        add(jlLabel1);
//        add(jlLabel2);
//        add(jlLabel3);
//
//        setLayout(null);
//        setBounds(50, 50, 200, 200);
//        setLocation(400, 300);
//        setTitle(title);
//        setSize(400, 250);
//        setVisible(true);
//        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
//        setResizable(false);
//    }
//}
