package org.example;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;

import org.example.Model.Book;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {

    public static void main(String[] args) throws IOException, ParseException {

        System.out.println("=====================================");
        System.out.println("Preloading initial data");
        String path = "src/main/java/org/example/data/main_dataset.csv";


        List<Book> books = new ArrayList<>();


        try {
            Reader reader = new FileReader(path);
            CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);

            for (CSVRecord csvRecord : csvParser) {
                String title = csvRecord.get(1);
                String author = csvRecord.get(2);
                String format = csvRecord.get(3);
                String price = csvRecord.get(5);
                String isbn_13 = csvRecord.get(8);
                books.add(new Book(title, isbn_13, author, format, price));
            }
            csvParser.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Lucene.createIndex(books);


        System.out.println("Finished preloading initial data");
        System.out.println("=====================================");


        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=====================================");

            System.out.println("\n*** Main Menu ***");
            System.out.println("1. Query books by author");
            System.out.println("2. Query books by title");
            System.out.println("3. Query books by publication");
            System.out.println("4. Query books by format");
            System.out.println("5. Query books by ISBN");
            System.out.println("6. Query books by price");
            System.out.println("7. Add new book data to system and index it");
            System.out.println("q. Exit");

            System.out.print("Enter your choice: ");
            String choice = scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("You selected Option 1.");
                    System.out.print("Enter author name: ");
                    String author = scanner.nextLine();
//                    Lucene.createIndex(books, index, w);
//                    IndexReader reader = DirectoryReader.open(index);
//                    searcher = new IndexSearcher(reader);
                    queryByAuthor(author);
                    break;

                case "2":
                    System.out.println("You selected Option 2.");
                    System.out.print("Enter title: ");
                    String title = scanner.nextLine();
                    queryByTitle(title);
                    break;

                case "3":
                    System.out.println("You selected Option 3.");
                    System.out.print("Enter ISBN: ");
                    String isbn = scanner.nextLine();
                    queryByISBN(isbn);
                    break;

                case "4":
                    System.out.println("You selected Option 4.");
                    System.out.print("Enter format: ");
                    String format = scanner.nextLine();
                    queryByFormat(format);
                    break;

                case "5":
                    System.out.println("You selected Option 5.");
                    System.out.print("Enter ISBN: ");
                    String isbn_13 = scanner.nextLine();
                    queryByISBN(isbn_13);
                    break;

                case "6":
                    System.out.println("You selected Option 6.");
                    System.out.print("Enter price: ");
                    String price = scanner.nextLine();
                    queryByPrice(price);
                    break;

                case "7":
                    System.out.println("You selected Option 7.");
                    System.out.println("The Max number of pages to crawl is 20.");
                    System.out.print("Enter number of pages (1 page contains 20 documents) to crawl: ");
                    int numOfPage = Integer.parseInt(scanner.nextLine());
                    if (numOfPage > 20) {
                        System.out.println("The Max number of pages to crawl is 20.");
                        break;
                    }
                    addNewBook(numOfPage);
                    break;

                case "q":
                case "Q":
                    System.out.println("Exiting the program...");
                    return;

                default:
                    System.out.println("Invalid choice. Please try again.");

            }
        }
    }

    public static void queryByAuthor(String author) throws IOException, ParseException {
        long l = System.currentTimeMillis();
        Query q = Lucene.createQueryByAuthor(author);
        IndexSearcher searcher = Lucene.searcher(q);
        ScoreDoc[] search = Lucene.search(q, searcher);
        Lucene.displayResults(search, searcher);
        System.out.println("Time to search: " + (System.currentTimeMillis() - l) + "ms");
    }

    public static void queryByTitle(String title) throws IOException, ParseException {
        long l = System.currentTimeMillis();
        Query q = Lucene.createQueryByTitle(title);
        IndexSearcher searcher = Lucene.searcher(q);
        ScoreDoc[] search = Lucene.search(q, searcher);
        Lucene.displayResults(search, searcher);
        System.out.println("Time to search: " + (System.currentTimeMillis() - l) + "ms");
    }

    public static void queryByISBN(String isbn) throws IOException, ParseException {
        long l = System.currentTimeMillis();
        Query q = Lucene.createQueryByISBN(isbn);
        IndexSearcher searcher = Lucene.searcher(q);
        ScoreDoc[] search = Lucene.search(q, searcher);
        Lucene.displayResults(search, searcher);
        System.out.println("Time to search: " + (System.currentTimeMillis() - l) + "ms");
    }

    public static void queryByFormat(String format) throws IOException, ParseException {
        long l = System.currentTimeMillis();
        Query q = Lucene.createQueryByFormat(format);
        IndexSearcher searcher = Lucene.searcher(q);
        ScoreDoc[] search = Lucene.search(q, searcher);
        Lucene.displayResults(search, searcher);
        System.out.println("Time to search: " + (System.currentTimeMillis() - l) + "ms");
    }

    public static void queryByPrice(String price) throws IOException, ParseException {
        long l = System.currentTimeMillis();
        Query q = Lucene.createQueryByPrice(price);
        IndexSearcher searcher = Lucene.searcher(q);
        ScoreDoc[] search = Lucene.search(q, searcher);
        Lucene.displayResults(search, searcher);
        System.out.println("Time to search: " + (System.currentTimeMillis() - l) + "ms");
    }


    public static void addNewBook(int numOfPage) throws IOException {
        List<Book> books = new ArrayList<>();
        for (int i = 1; i <= numOfPage; i++) {
            System.out.println("=====================================");
            System.out.println("Crawling page " + i);
            System.out.println("=====================================");
            List<Book> crawl = BookCrawler.crawl(i);
            for (Book book : crawl) {
                System.out.println(book);
            }
            books.addAll(crawl);
        }
        Lucene.addDoc(books);

    }


}