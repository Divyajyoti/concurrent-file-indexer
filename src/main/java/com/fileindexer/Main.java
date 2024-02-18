package com.fileindexer;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.TopDocs;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException, ParseException {

        //Input folder
        String docsPath = "src/main/resources/sourceFiles";

        //Output folder
        String indexPath = "src/main/resources/IndexedFiles";

        long startTime = System.currentTimeMillis();
        FileIndexer fileIndexer = new FileIndexer(indexPath);
        fileIndexer.createIndex(docsPath);
        fileIndexer.close();
        long endTime = System.currentTimeMillis();
        System.out.println(" Files indexed, time taken: " +(endTime-startTime)+" ms");

        FileSearcher fileSearcher = new FileSearcher(indexPath);
        TopDocs topDocs = fileSearcher.search("External");
        System.out.println(topDocs.totalHits);
        System.out.println(fileSearcher.getDocumentsPaths(topDocs));
    }
}