package com.fileindexer;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

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
    }
}