package top.fan2wan.fileserver.searchengine;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.LongPoint;
import org.apache.lucene.index.IndexableField;
import org.apache.lucene.search.*;
import top.fan2wan.fileserver.searchengine.util.LuceneUtil;

import java.io.IOException;

/**
 * @Author: fanT
 * @Date: 2022/1/14 10:44
 * @Description:
 */
public class LuceneSearchTest {
    private static LuceneUtil searchUtil = new LuceneUtil("F:\\tempdir\\lucene");

    public static void main(String[] args) {
        //search_test(new TermQuery(new Term("name", "无锡")));
        search_test(LongPoint.newRangeQuery("id", 0, 100));
    }

    public static void search_test(Query query) {

        try {
            //TopDocs docs = searchUtil.search(query, 10);
            TopDocs docs = searchUtil.search(query, 10, new Sort(
                    new SortField("createTime", SortField.Type.LONG, true)));
            print(docs);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void print(TopDocs docs) throws IOException {
        //取出每条查询结果
        for (ScoreDoc scoreDoc : docs.scoreDocs) {
            //scoreDoc.doc相当于 docID，根据这个 docID 来获取文档
            Document doc = searchUtil.searchByDocId(scoreDoc.doc);
            //fullPath 是刚刚建立索引的时候我们定义的一个字段，表示路径。也可以取其他的内容，只要我们在建立索引时有定义即可。
            System.out.println(doc.get("id"));
            System.out.println(doc.get("path"));
            System.out.println(doc.get("name"));
            System.out.println(doc.get("createTime"));
            System.out.println(doc.get("size"));
            System.out.println(doc.get("type"));

            System.out.println("size ---------");
            IndexableField size = doc.getField("size");
            System.out.println(size.numericValue().longValue());
        }
    }
}
