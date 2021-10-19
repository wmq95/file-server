package top.fan2wan.fileserver.convert.util;

import com.aspose.words.Document;
import com.aspose.words.License;
import com.aspose.words.SaveFormat;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;


/**
 * @Author: fanT
 * @Date: 2021/10/13 13:52
 * @Description:
 */
public class AsposeUtil {

    public static void main(String[] args) throws Exception {
        AsposeUtil util = new AsposeUtil();
//        util.word2pdf(word_path, "d:/test.pdf");
//        util.word2Pdf_02(word_path, "d:/aspose2.pdf");
    }

    public void word2pdf(String path, String outPath) throws Exception {
        Document wpd = new Document(path);
        wpd.save(outPath, SaveFormat.PDF);
    }

    public void word2Pdf_02(String path, String outPath) throws FileNotFoundException {
        if (!getLicense()) {
            System.out.println("非法");
            return;
        }
        long old = System.currentTimeMillis();
        FileOutputStream os = new FileOutputStream(outPath);

        //Address是将要被转化的word文档
        Document doc = null;
        try {
            doc = new Document(path);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        try {
            //全面支持DOC, DOCX, OOXML, RTF HTML, OpenDocument, PDF, EPUB, XPS, SWF 相互转换
            doc.save(os, SaveFormat.PDF);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        long now = System.currentTimeMillis();
        //转化用时
        System.out.println("共耗时：" + ((now - old) / 1000.0) + "秒");
    }

    /**
     * 去水印失败了 应该是license 失效 可以去官网获取30天试用得license
     *
     * @return
     */
    public boolean getLicense() {
        boolean result = false;
        try {
            //引入license.xml文件,去除水印
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("license.xml");

            //注意此处为apose-slides的jar包
            License aposeLic = new License();
            aposeLic.setLicense(is);
            result = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
