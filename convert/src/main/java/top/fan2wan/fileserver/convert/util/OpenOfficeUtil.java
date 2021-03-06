package top.fan2wan.fileserver.convert.util;


import com.artofsolving.jodconverter.DocumentConverter;
import com.artofsolving.jodconverter.openoffice.connection.OpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.connection.SocketOpenOfficeConnection;
import com.artofsolving.jodconverter.openoffice.converter.OpenOfficeDocumentConverter;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.net.ConnectException;

/**
 * @Author: fanT
 * @Date: 2021/10/12 13:49
 * @Description: 利用jodconverter(基于OpenOffice服务)将文件(* .doc 、 * .docx 、 * .xls 、 * .ppt)转化为html格式或者pdf格式，
 * 使用前请检查OpenOffice服务是否已经开启, OpenOffice进程名称：soffice.exe | soffice.bin
 */
public class OpenOfficeUtil {
    private int port;

    public OpenOfficeUtil() {
        this(8100);
    }

    public OpenOfficeUtil(int port) {
        this.port = port;
    }

    private static Logger logger = LoggerFactory.getLogger(OpenOfficeUtil.class);

    /**
     * 转换文件
     *
     * @param path   源文件路径
     * @param outPut 输出路径 路径不存在 会自己创建上级目录
     * @throws ConnectException
     */
    public void convert2Pdf(String path, String outPut) throws ConnectException {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(path), "path can not be null");
        Preconditions.checkArgument(!Strings.isNullOrEmpty(outPut), "outPath can not be null");
        File in = new File(path);
        Preconditions.checkArgument(in.exists(), "origin file not exists");
        File out = new File(outPut);
        Preconditions.checkArgument(!out.exists(), "out file was exists");

        OpenOfficeConnection connection = new SocketOpenOfficeConnection(port);
        try {
            connection.connect();
            DocumentConverter converter = new OpenOfficeDocumentConverter(connection);
            converter.convert(in, out);
        } finally {
            connection.disconnect();
        }
    }
}
