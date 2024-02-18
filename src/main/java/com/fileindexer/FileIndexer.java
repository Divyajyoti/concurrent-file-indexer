package com.fileindexer;

import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static com.fileindexer.Constansts.*;

public class FileIndexer {

    private IndexWriter writer;

    /**
     * Constructor
     *
     * @params: directory path where index will be stored
     */
    FileIndexer(String indexDirectoryPath) throws IOException {
        Directory indexDirectory = FSDirectory.open(Paths.get(indexDirectoryPath));
        IndexWriterConfig indexWriterConfig = new IndexWriterConfig();
        writer = new IndexWriter(indexDirectory, indexWriterConfig);
    }

    /**
     * Creates index on file
     *
     * @params: source path of directory to be indexed
     */
    public void createIndex(String sourceDirPath) throws IOException {
        //Todo: concurrent index
        Path path = Path.of(sourceDirPath);
        if (Files.isDirectory(path)) {
            Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    try {
                        indexFile(file);
                    } catch (IOException ioe) {
                        ioe.printStackTrace();
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            indexFile(path);
        }
    }

    public void close() throws IOException {
        writer.close();
    }

    private void indexFile(Path filePath) throws IOException {
        //Todo: add index per page content
        Document document = getDocument(filePath.toFile());
        writer.addDocument(document);
    }

    private Document getDocument(File file) throws IOException {
        Document document = new Document();
        document.add(new StringField(PATH, file.getCanonicalPath(), Field.Store.YES));
        document.add(new StringField(NAME, file.getName(), Field.Store.YES));
        document.add(new TextField(CONTENT, new FileReader(file)));
        return document;
    }

}
