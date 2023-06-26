import java.io.*;
import java.util.ArrayList;

public class doFile {
    public String host="";
    public String port=null;
    File f=new File("front.txt");
    public doFile(){

    }
    public void write() throws IOException {
                 FileOutputStream fos = new FileOutputStream(f);

                 BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
                 bw.write(host);
                 bw.newLine();
                 bw.write(port);

                 bw.close();
    }
    public void read(){
        ArrayList<String> arrayList = new ArrayList<>();
        if(f.exists()) {
            try {
                InputStreamReader inputReader = new InputStreamReader(new FileInputStream(f));
                BufferedReader bf = new BufferedReader(inputReader);
                // 按行读取字符串
                String str;
                while ((str = bf.readLine()) != null) {
                    arrayList.add(str);
                }
                bf.close();
                inputReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (arrayList.size() >= 2) {
                host = arrayList.get(0);
                port = arrayList.get(1);
            }
        }
    }
}
