package org.example;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.example.Model.Book;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class BookCrawler {

    public static List<Book> crawl(int page) {

        String url = "http://n3.datasn.io/data/api/v1/n3a2/book_16/main/list/" + page + "/";

        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(url);
            String html = EntityUtils.toString(client.execute(request).getEntity());
            Document doc = Jsoup.parse(html);

            Elements rows = doc.select(".api-data-table tbody tr");

            List<Book> books = new ArrayList<>();
            for (Element row : rows) {

                Elements td = row.select("td");
                String title = td.get(2).text();
                String isbn_13 = td.get(3).text();
                String author = td.get(5).text();
                String format = td.get(9).text();
                String price = td.get(12).text();
                if (price.equals("")) {
                    price = "0";
                } else {
                    price = price.substring(1);
                }
                Book book = new Book(title, isbn_13, author, format, price);
                books.add(book);
            }
            return books;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
