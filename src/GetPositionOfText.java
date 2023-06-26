import org.apache.poi.sl.usermodel.PictureData;
import org.apache.poi.sl.usermodel.PictureShape;
import org.apache.poi.xslf.usermodel.*;

import java.awt.*;
import java.awt.geom.Point2D;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public class GetPositionOfText {
    private String out="^XA\n" +
            "^DFE:XYGS^FS\n" +
            "^POI\n" +
            "^LH0,0\n" +
            "^CI26\n" +
            "^SEE:GB18030.DAT\n" +
            "^CW1,E:SIMSUN.TTF\n" +
            "^CI26\n";
    public   GetPositionOfText(String path) throws IOException {
        //Process p = Runtime.getRuntime().exec("C:\\Program Files\\Microsoft Office\\root\\Office16\\POWERPNT.EXE");

        XSLFPictureShape xs=null;
        int num=1;
        while (true) {
            //if (!p.isAlive()) {
                XMLSlideShow ppt = null;

                try {

                    // 通过输入流读取一个现有的PPT文件，生成PPT类
                    //Scanner s = new Scanner(System.in);
                    //System.out.println("请输入字符串：");
                    //Input in=new Input();
                    //String path = in.getPath();
                    //String path="C:\\Users\\h'h'j\\Desktop\\Test.pptx";

                    ppt = new XMLSlideShow(new FileInputStream(path));
                    /*

                     * 获取PPT的所有文本框里的文字，并进行更改

                     */

// 获取PPT中的所有幻灯片

                    List<XSLFSlide> slides = ppt.getSlides();

// 遍历幻灯片

                    for (XSLFSlide slide : slides) {

                        // 获取幻灯片中的所有图形（文本框、表格、图形...）

                        List<XSLFShape> shapes = slide.getShapes();

                        // 遍历图形

                        for (XSLFShape shape : shapes) {

                            // 判断该图形类是否是文本框类

                            if (shape instanceof XSLFTextShape) {

                                // 将图像类强制装换成文本框类

                                XSLFTextShape ts = (XSLFTextShape) shape;

                                // 获取文本框内的文字

                                String str = ts.getText();
                                Double size =0.0;
                                System.out.println(ts.getTextHeight());
                                System.out.println(ts.getAnchor());
                                ts.getTextParagraphs();
                                for(XSLFTextParagraph tp:ts.getTextParagraphs()){
                                    tp.getTextRuns();
                                    for(XSLFTextRun tr:tp.getTextRuns()){
                                        System.out.println(tr.getFontSize());
                                        size=tr.getFontSize();
                                        break;
                                    }
                                    break;
                                }


                                if(str!=null){
                                    if(str.length()==1&&Character.isDigit((str.charAt(0)))){
                                        num++;
                                        out+="^FO"+(int)ts.getAnchor().getX()*4+","+(int)(ts.getAnchor().getY()*5.71)+"^A1N,"+(int)(size.intValue()/0.222)+","+(int)(size.intValue()/0.222)+"^FN"+ts.getText()+"^FS\n";
                                    }else {
                                        out += "^FO" + (int)ts.getAnchor().getX()*4 + "," + (int)(ts.getAnchor().getY()*5.71) + "^A1N," + (int)(size.intValue()/0.222) + "," + (int)(size.intValue()/0.222)+ "^FD" + ts.getText() + "^FS\n";
                                        System.out.println(str);
                                    }
                                }else System.out.println("wrong");


                            }
                            else if(shape instanceof XSLFPictureShape){
                                xs=(XSLFPictureShape) shape;
                                System.out.println(xs.getAnchor());
                               // out += "^BY3," + "2" + "," + "227" + "^FT" + (int)xs.getAnchor().getX() + "," + (int)xs.getAnchor().getY() + "^BCN,,Y,N^FN" + "^FS\n";
                            }

                        }

                    }

                } catch (Exception e) {

                    e.printStackTrace();

                } finally {

                    if (ppt != null) {

                        try {

                            // 保存完之后要对PPT进行关闭操作

                            ppt.close();

                        } catch (IOException e) {

                            e.printStackTrace();

                        }

                    }

                //}
                break;

            }
        }
        out += "^BY3," + "2" + "," + "227" + "^FT" + (int)xs.getAnchor().getX()*4 + "," + (int)(xs.getAnchor().getY()*5.71) + "^BCN,,Y,N^FN" +num+ "^FS\n";
        out+="^PQ1,0,1,Y^XZ";
        System.out.println(out);
        //Transmit transmit=new Transmit();
        //transmit.transmit(out);
        //transmit.transmit("hhj");
    }
    public String ppt(){
        return out;
    }

}