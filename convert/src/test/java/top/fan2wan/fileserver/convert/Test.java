package top.fan2wan.fileserver.convert;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.fan2wan.fileserver.convert.util.OpenOfficeUtil;
import top.fan2wan.fileserver.convert.util.PdfBoxUtil;

import java.net.ConnectException;


/**
 * @Author: fanT
 * @Date: 2021/10/20 13:50
 * @Description:
 */
public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        new Thread(() -> {
            OpenOfficeUtil openOfficeUtil = new OpenOfficeUtil();
            logger.info("start to convert ...");
            try {
                openOfficeUtil.convert2Pdf("F:\\tempdir\\convert\\2021\\10\\12\\1.doc", "F:\\tempdir\\convert\\2021\\10\\12\\1.pdf");
            } catch (ConnectException e) {
                e.printStackTrace();
            }
            logger.info("convert success...");
        }, "BB").start();

        /*new Thread(() -> {
            OpenOfficeUtil openOfficeUtil = new OpenOfficeUtil();
            logger.info("start to convert ...");
            openOfficeUtil.convert2Pdf("d:/openoffice/work/test.pptx", "d:/openoffice/work/a/test1.pdf");
            logger.info("convert success...");
        }, "AA").start();*/


        //PdfBoxUtil util = new PdfBoxUtil();
        //logger.info(util.readPdf("d:/openoffice/work/aspose.pdf"));
    }
}
