package top.fan2wan.fileserver.convert;

import com.sun.star.xml.sax.SAXException;
import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import top.fan2wan.fileserver.convert.util.PdfBoxUtil;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;


/**
 * @Author: fanT
 * @Date: 2021/10/20 13:50
 * @Description:
 */
public class Test {
    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) throws Exception {
        /*new Thread(() -> {
            OpenOfficeUtil openOfficeUtil = new OpenOfficeUtil();
            logger.info("start to convert ...");
            try {
                openOfficeUtil.convert2Pdf("F:\\tempdir\\convert\\2021\\10\\12\\1.doc", "F:\\tempdir\\convert\\2021\\10\\12\\1.pdf");
            } catch (ConnectException e) {
                e.printStackTrace();
            }
            logger.info("convert success...");
        }, "BB").start();*/

        /*new Thread(() -> {
            OpenOfficeUtil openOfficeUtil = new OpenOfficeUtil();
            logger.info("start to convert ...");
            openOfficeUtil.convert2Pdf("d:/openoffice/work/test.pptx", "d:/openoffice/work/a/test1.pdf");
            logger.info("convert success...");
        }, "AA").start();*/


        /*PdfBoxUtil util = new PdfBoxUtil();
        Long pdfStart = Instant.now().toEpochMilli();
        logger.info("file length was :{}", util.readPdf("F:\\tempdir\\convert\\2021\\10\\12\\2.pdf").length());
        logger.info("pdfBox cost was :{}", Instant.now().toEpochMilli() - pdfStart);*/

        /*Long start = Instant.now().toEpochMilli();
        logger.info("file length was :{}", parseToStringExample("F:\\tempdir\\convert\\2021\\10\\12\\2.pdf"));
        logger.info("cost was :{}", Instant.now().toEpochMilli() - start);*/

    }

    public static String parseToStringExample(String path) throws IOException, SAXException, TikaException, org.xml.sax.SAXException {
        Tika tika = new Tika();
        try (InputStream stream = new FileInputStream(path)) {
            return tika.parseToString(stream);
        }
    }
}
