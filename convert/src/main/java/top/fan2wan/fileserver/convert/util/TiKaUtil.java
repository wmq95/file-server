package top.fan2wan.fileserver.convert.util;

import org.apache.tika.Tika;
import org.apache.tika.exception.TikaException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @Author: fanT
 * @Date: 2022/1/7 9:44
 * @Description: util for tika
 */
public class TiKaUtil {

    public static String parseContent(String filePath) throws IOException, TikaException {
        Tika tika = new Tika();
        try (InputStream stream = new FileInputStream(filePath)) {
            return tika.parseToString(stream);
        }
    }
}
