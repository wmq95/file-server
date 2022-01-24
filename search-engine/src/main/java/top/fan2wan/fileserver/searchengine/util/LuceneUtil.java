package top.fan2wan.fileserver.searchengine.util;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * @Author: fanT
 * @Date: 2022/1/17 13:14
 * @Description: util for lucene
 */
public class LuceneUtil {

    private final String indexDir;
    private IndexWriter writer;
    private IndexSearcher searcher;

    public LuceneUtil(String indexDir) {
        this.indexDir = indexDir;
        try {
            initLucene();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initLucene() throws IOException {
        /**
         * writer
         */
        initWriter();
        /**
         * search
         */
        initSearcher();
    }

    private void initSearcher() throws IOException {
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        IndexReader reader = DirectoryReader.open(dir);
        searcher = new IndexSearcher(reader);
    }

    private void initWriter() throws IOException {
        Directory dir = FSDirectory.open(Paths.get(indexDir));
        Analyzer analyzer = new IKAnalyzer();
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        writer = new IndexWriter(dir, config);
        /**
         * 确保建立索引 不然之后search 会出现找不到索引的异常
         */
        this.writer.commit();
    }

    public boolean save(Document document) throws IOException {
        this.writer.addDocument(document);
        /**
         * 需要调用commit
         */
        this.writer.commit();
        return true;
    }

    public TopDocs search(Query query, int num) throws IOException {
        return this.searcher.search(query, num);
    }

    public TopDocs search(Query query, int num, Sort sort) throws IOException {
        return this.searcher.search(query, num, sort);
    }

    public TopDocs searchWithPage(Query query, int pageSize, int previous, Sort sort) throws IOException {

        return this.searcher.searchAfter(new ScoreDoc(previous, 1f), query, pageSize, sort);
    }

    public Document searchByDocId(int doc) throws IOException {
        return this.searcher.doc(doc);
    }

    public void deleteAll() throws IOException {
        this.writer.deleteAll();
        this.writer.commit();
    }

    public long deleteByQuery(Query query) throws IOException {
        return this.writer.deleteDocuments(query);
    }
}
