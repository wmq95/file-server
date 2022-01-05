package top.fan2wan.fileserver.convert.util;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * @Author: fanT
 * @Date: 2021/10/20 13:31
 * @Description: util for pdfBox
 */
public class PdfBoxUtil {
    private String tempDir;
    private Long maxMemoryUsage;

    private MemoryUsageSetting setting;

    public PdfBoxUtil() {
        this(null, DEFAULT_SIZE);
    }

    public PdfBoxUtil(String tempDir) {
        this(tempDir, DEFAULT_SIZE);
    }

    public PdfBoxUtil(String tempDir, Long maxMemoryUsage) {
        this.tempDir = tempDir;
        this.maxMemoryUsage = maxMemoryUsage;
        this.setting = getMemorySetting();
    }

    private final static Long DEFAULT_SIZE = 1024 * 1024 * 100L;
    private static Logger logger = LoggerFactory.getLogger(PdfBoxUtil.class);

    /**
     * 读取pdf 内容 只读取text内同
     *
     * @param path 文件路径
     * @return text内同
     */
    public String readPdf(String path) {
        Preconditions.checkArgument(!Strings.isNullOrEmpty(path), " path can not be null");
        String content = null;
        try {
            PDDocument document = PDDocument.load(new File(path), setting);
            // 读文本内容
            PDFTextStripper stripper = new PDFTextStripper();
            // 设置按顺序输出
            stripper.setSortByPosition(true);
            stripper.setStartPage(0);
            stripper.setEndPage(document.getNumberOfPages());
            //获取内容
            content = stripper.getText(document);
        } catch (IOException e) {
            logger.error("fail to read pdf file ,error was\n", e);
        }
        return content;
    }

    private MemoryUsageSetting getMemorySetting() {
        MemoryUsageSetting memoryUsageSetting = MemoryUsageSetting.setupMixed(maxMemoryUsage);
        if (!Strings.isNullOrEmpty(tempDir)) {
            memoryUsageSetting.setTempDir(new File(tempDir));
        }
        return memoryUsageSetting;
    }
}
