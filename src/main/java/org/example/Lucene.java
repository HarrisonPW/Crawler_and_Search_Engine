package org.example;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import org.example.Model.Book;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;



public class Lucene {

    public static void createIndex(List<Book> books) throws IOException {

        addDoc(books);

    }

    public static Query createQueryByAuthor(String querystr) throws ParseException {
        Analyzer analyzer = new StandardAnalyzer();
        Query q = new QueryParser("author", analyzer).parse(querystr);
        return q;
    }

    public static Query createQueryByTitle(String querystr) throws ParseException {
        Analyzer analyzer = new StandardAnalyzer();
        Query q = new QueryParser("title", analyzer).parse(querystr);
        return q;
    }

    public static Query createQueryByISBN(String querystr) throws ParseException {
        Analyzer analyzer = new StandardAnalyzer();
        Query q = new QueryParser("isbn", analyzer).parse(querystr);
        return q;
    }

    public static Query createQueryByFormat(String querystr) throws ParseException {
        Analyzer analyzer = new StandardAnalyzer();
        Query q = new QueryParser("format", analyzer).parse(querystr);
        return q;
    }

    public static Query createQueryByPrice(String querystr) throws ParseException {
        Analyzer analyzer = new StandardAnalyzer();
        Query q = new QueryParser("price", analyzer).parse(querystr);
        return q;
    }


    public static IndexSearcher searcher(Query q) throws IOException {
        Directory dir = FSDirectory.open(Paths.get("src/main/java/org/example/dir"));
        IndexReader reader = DirectoryReader.open(dir);
        IndexSearcher searcher = new IndexSearcher(reader);
        return searcher;
    }

    public static ScoreDoc[] search(Query q, IndexSearcher searcher) throws IOException {
        TopDocs search = searcher.search(q, 20);
        ScoreDoc[] hits = search.scoreDocs;
        return hits;
    }

    public static void displayResults(ScoreDoc[] hits, IndexSearcher searcher) throws IOException {
        if (hits != null) {
            System.out.println("Found " + hits.length + " hits.");
            for (int i = 0; i < hits.length; i++) {
                int docId = hits[i].doc;
                Document d = searcher.doc(docId);
                System.out.println("Title: " + d.get("title") + "; Author: " + d.get("author") + "; ISBN: " + d.get("isbn") + "; Format: " + d.get("format") + "; Price: " + d.get("price"));
            }
        }
    }

    public static void addDoc(List<Book> books) throws IOException {
        List<Document> docs = new ArrayList<>();
        for (Book book : books) {
            Document doc = new Document();
            doc.add(new TextField("author", book.getAuthor(), Field.Store.YES));
            doc.add(new TextField("title", book.getTitle(), Field.Store.YES));
            doc.add(new StringField("isbn", book.getIsbn_13(), Field.Store.YES));
            doc.add(new TextField("format", book.getFormat(), Field.Store.YES));
            doc.add(new TextField("price", book.getPrice(), Field.Store.YES));
            docs.add(doc);
        }

        Analyzer analyzer = new StandardAnalyzer();
        Directory dir = FSDirectory.open(Paths.get("src/main/java/org/example/dir"));
        IndexWriterConfig config = new IndexWriterConfig(analyzer);
        IndexWriter w = new IndexWriter(dir, config);
        for (Document doc : docs) {
            w.addDocument(doc);
        }
        w.close();

    }


}
