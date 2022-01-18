package top.fan2wan.fileserver.searchengine;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import top.fan2wan.fileserver.searchengine.util.LuceneUtil;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Paths;

/**
 * @Author: fanT
 * @Date: 2022/1/10 13:59
 * @Description: test for lucence
 */
public class LuceneTest {
    //索引保存到的路径
    private static String indexDir = "F:\\tempdir\\lucene";
    //需要索引的文件数据存放的目录
    private static String dataDir = "F:\\tempdir\\lucene\\data";
    private static String text = "Lucene自带多种分词器，其中对中文分词支持比较好的是smartcn。";

    public static void main(String[] args) throws Exception {
        //index();
        Searcher.search(indexDir, "info");
        //analyzer_test(new SmartChineseAnalyzer());
        System.out.println("====================");
        Searcher.search(indexDir, "呵呵");
    }

    private static void analyzer_test(Analyzer analyzer) throws IOException {
        TokenStream tokenStream = analyzer.tokenStream("test", new StringReader(text));
        CharTermAttribute attribute = tokenStream.addAttribute(CharTermAttribute.class);
        tokenStream.reset();
        while (tokenStream.incrementToken()) {
            System.out.println(attribute.toString());
        }
    }

    private static void index() {

        Indexer indexer = null;
        int indexedNum = 0;
        //记录索引开始时间
        long startTime = System.currentTimeMillis();
        try {
            // 开始构建索引
            indexer = new Indexer(indexDir);
            indexedNum = indexer.indexAll(dataDir);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (null != indexer) {
                    indexer.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        //记录索引结束时间
        long endTime = System.currentTimeMillis();
        System.out.println("索引耗时" + (endTime - startTime) + "毫秒");
        System.out.println("共索引了" + indexedNum + "个文件");
    }
}

