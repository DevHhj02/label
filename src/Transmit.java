import javax.swing.*;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Transmit {
    public void transmit(String msg,String host,int port) throws IOException {
        //host= JOptionPane.showInputDialog("请输入服务器ip地址:");
        Socket socket=new Socket(host,port);
        DataOutputStream out=new DataOutputStream(socket.getOutputStream());
        out.write(msg.getBytes("GBK"));
        socket.close();
    }
}
