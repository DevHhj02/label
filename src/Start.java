import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class Start extends JFrame{
    private JTextField path;
    private MyMenu menu;
    private JTextField host;
    private JTextField port;
    private JButton send;
    private JPanel panel;
    private JLabel output;
    private JButton pg;
    private JButton again;
    String t;

    public Start(){
        // 设置标题
        this.setTitle("标签编辑器");
        // 设置窗口大小
        this.setSize(950, 600);
        // 居中显示
        this.setLocationRelativeTo(null);
        menu=new MyMenu();
        doFile df=new doFile();
        df.read();
        send.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(Objects.equals(path.getText(), "")){
                    JOptionPane.showMessageDialog(null, "文件路径为空","错误信息",JOptionPane.ERROR_MESSAGE);
                }else if(Objects.equals(host.getText(), "")){
                    JOptionPane.showMessageDialog(null, "ip地址为空","错误信息",JOptionPane.ERROR_MESSAGE);
                }else if(Objects.equals(port.getText(), "")){
                    JOptionPane.showMessageDialog(null, "端口号为空","错误信息",JOptionPane.ERROR_MESSAGE);
                }else{
                    try {
                        df.host=host.getText();
                        df.port=port.getText();
                        df.write();
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "历史记录保存失败","错误信息",JOptionPane.ERROR_MESSAGE);
                    }
                    Transmit transmit=new Transmit();
                    try {
                        transmit.transmit(t,host.getText(),Integer.parseInt(port.getText()));
                        JOptionPane.showMessageDialog(null, "发送成功","",JOptionPane.PLAIN_MESSAGE);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "发送失败","错误信息",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        pg.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> list=new ppttoImage().pptToImage(path.getText());
                for(String s:list){
                    try {
                        File f=new File(s);
                        Desktop desktop = Desktop.getDesktop();
                        desktop.open(f);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "预览失败","错误信息",JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });
        again.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    t = new GetPositionOfText(path.getText()).ppt();
                    output.setText("<html><body>" + t.replace("\n", "<br>") + "<body></html>");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "字符串刷新失败", "错误信息", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        host.setText(df.host);
        if(df.port!=null) port.setText(df.port);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(panel);
        setVisible(true);
    }
    class MyMenu{
        public MyMenu(){
// 菜单条
            JMenuBar jMenuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("ppt");
            JMenu helpMenu = new JMenu("帮助");
            JMenuItem fileItemNew = new JMenuItem("打开ppt模板");
            JMenuItem fileItemOpen = new JMenuItem("选择pptx文件");
            JMenuItem fileItemOpen1 = new JMenuItem("打开pptx文件");
            JMenuItem helpItemUse = new JMenuItem("使用手册");
            fileItemNew.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_MASK));
            fileItemOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_MASK));
            fileItemOpen1.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_MASK));
            helpMenu.add(helpItemUse);
            fileMenu.add(fileItemNew);
            fileMenu.add(fileItemOpen);
            fileMenu.add(fileItemOpen1);
            jMenuBar.add(fileMenu);
            jMenuBar.add(helpMenu);
            // 添加菜单条
            setJMenuBar(jMenuBar);
            fileItemOpen.addActionListener(e -> {
                // 打开文件
                Input in=new Input();
                if(in.getPath()!=null) {
                    path.setText(in.getPath());
                    try {
                        t = new GetPositionOfText(path.getText()).ppt();
                        output.setText("<html><body>" + t.replace("\n", "<br>") + "<body></html>");
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "字符串编写失败", "错误信息", JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            fileItemNew.addActionListener(e -> {
                try {
                    Runtime.getRuntime().exec("cmd.exe /c start Demo.pot");
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(null, "ppt模板打开失败","错误信息",JOptionPane.ERROR_MESSAGE);
                }
            });
            fileItemOpen1.addActionListener(e -> {
                // 打开文件
                Input in=new Input();
                if(in.getPath()!=null) {
                    File f=new File(in.getPath());
                    Desktop desktop = Desktop.getDesktop();
                    try {
                        desktop.open(f);
                    } catch (IOException ex) {
                        JOptionPane.showMessageDialog(null, "ppt打开失败","错误信息",JOptionPane.ERROR_MESSAGE);
                    }
                }
            });
            helpItemUse.addActionListener(e -> JOptionPane.showMessageDialog(null,"" + "##################\r\n" + "#标签编辑软件使用说明书#\r\n"
                    + "####################\r\n" + "1.本软件可以实现以下功能：\r\n" +"(1) 从PPT模板上新建演示文稿\r\n"
            +"(2) 选择本地上的pptx文件，选择文件的路径和形成的字符串可以显示在屏幕上\r\n"+"(3) 打开本地上的ppt进行编辑和修改\r\n"
            +"(4) 对选择好的ppt文件进行预览，预览将以图片的形式展现\r\n"+"(5) 将选择好的字符串发送到指定的IP地址和端口上\r\n"
            +"注意事项\r\n"+"(1) 选择的ppt文件中必须包含条码图片，否则字符串编写会报错\r\n"+"(2) 每次重新选择文件或修改已选择的文件时需点击刷新重新刷新字符串\r\n"
            +"(3) ppt文件中的文字以文本框的形式呈现，字体为宋体，可改变文字同样以文本框形式呈现，变量内容按顺序输入阿拉伯数字\r\n\n"
                    +"开发人员：胡皓钧    学校：哈尔滨工业大学（深圳）  邮箱：13479886248@163.com    时间：2023年\r\n","使用说明",JOptionPane.PLAIN_MESSAGE));
        }
    }
    public static void main(String[] args) {
        new Start();
    }
}
