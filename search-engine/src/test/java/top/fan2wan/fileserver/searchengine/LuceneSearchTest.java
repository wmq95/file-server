package top.fan2wan.fileserver.searchengine;

import org.apache.lucene.document.Document;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import top.fan2wan.fileserver.searchengine.util.LuceneUtil;

import java.io.IOException;

/**
 * @Author: fanT
 * @Date: 2022/1/14 10:44
 * @Description:
 */
public class LuceneSearchTest {
    private static LuceneUtil searchUtil = new LuceneUtil("F:\\tempdir\\lucene\\file");

    public static void main(String[] args) {
        //search_test(new TermQuery(new Term("name", "无锡")));
        //search_test(LongPoint.newRangeQuery("_id", 0, 1222222222L));
        BooleanQuery.Builder builder = new BooleanQuery.Builder();

        builder
                //.add(new TermQuery(new Term("name", "测试")), BooleanClause.Occur.MUST)
                .add(new TermQuery(new Term("content", "一个")), BooleanClause.Occur.MUST);
        search_test(builder.build());
    }

    public static void search_test(Query query) {

        try {
            //TopDocs docs = searchUtil.search(query, 10);
            TopDocs docs = searchUtil.search(query, 10, new Sort(
                    new SortField("_createTime", SortField.Type.LONG, false)));
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
            System.out.println("==============end======");
        }
    }
}
