package com.fileindexer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static com.fileindexer.Constansts.CONTENT;
import static com.fileindexer.Constansts.PATH;

public class FileSearcher {

    private IndexSearcher searcher;
    private QueryParser queryParser;


    public FileSearcher(String indexDirectoryPath) throws IOException {
        Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
        IndexReader indexReader = DirectoryReader.open(indexDirectory);
        searcher = new IndexSearcher(indexReader);
        queryParser = new QueryParser(CONTENT, new StandardAnalyzer());
    }

    public TopDocs search(String searchText) throws ParseException, IOException {
        Query query = queryParser.parse(searchText);
        return searcher.search(query, 5);
    }

    public List<String> getDocumentsPaths(TopDocs topDocs){
        List<String> resultedDocuments = new ArrayList<>();
        for (ScoreDoc scoreDoc : topDocs.scoreDocs) {
            try {
                Document doc = searcher.doc(scoreDoc.doc);
                resultedDocuments.add(doc.get(PATH));
            } catch (IOException e) {
                System.out.println("Error occurred while getting documents");
            }

        }
        return resultedDocuments;
    }

}
