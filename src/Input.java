import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import java.io.File;

public class Input {
    public JFileChooser jfc;


        public Input() {

             jfc= new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());

            jfc.setAcceptAllFileFilterUsed(false);
            //限制文件只能显示PNG\JPG格式的图片
            FileNameExtensionFilter filter = new FileNameExtensionFilter("pptx", "pptx");
            jfc.addChoosableFileFilter(filter);
            int returnValue = jfc.showOpenDialog(null);

            //int returnValue = jfc.showSaveDialog(null);

            if (returnValue == JFileChooser.APPROVE_OPTION) {
                File selectedFile = jfc.getSelectedFile();
                System.out.println(selectedFile.getAbsolutePath());
            }

        }
        public String getPath(){
            if(jfc.getSelectedFile()!=null) return jfc.getSelectedFile().getAbsolutePath();
            else return null;
        }
}
