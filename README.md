# Open-Source-Search-Engine

This project illustrates how to develop a Search search engine using Apache Lucene which is a powerful search library written in Java. Such system would comprise a custom crawler for collecting book data from a specified source, indexing of this data with Lucene and the user will be able to provide search functionalities over this indexed data. Queries should be supported on various book attributes such as author, title, ISBN, format and price fields.

# Prerequisites
Java 8 or later
Apache Maven (for dependency management)
Apache Lucene 8.x
JSoup (for HTML parsing in the crawler)

# Starting the Search Engine
1. Clone the repository to your local machine.
2. Change into the project directory and run the following command: mvn clean install, which will install the dependencies for you.
3. Validate the references made to Lucene and JSoup libraries in your project pom.xml file which is added by default.

# Usage
Using the search engine:

**Indexing Books:** Invocation of ```Lucene.createIndex()``` method with a list of ```Book``` objects to index the initial set of documents.
**Adding more Books:** Use the ```BookCrawler.crawl(page)``` method to fetch more books and index those using the same ```createIndex()``` method.
**Searching:** Form queries on different attributes of book using ```createQueryBy*``` and execute those queries using ```search()```.

# Showcases
<img width="379" alt="Screenshot 2024-01-21 at 9 24 21 PM" src="https://github.com/HarrisonPW/Open-Source-Search-Engine/assets/32474200/6f07465d-527e-4f05-baf2-62ce82643065">

# Index and Search Performance
<img width="562" alt="Screenshot 2024-01-13 at 1 43 02 PM" src="https://github.com/HarrisonPW/Open-Source-Search-Engine/assets/32474200/1489c5cb-f18b-493f-9281-655aa0305140">

There is a picture to show adding K documents and display the time cost on y-axis with x-axis varing K from 100, 200, 400.
We can conclude that adding enormous data won't affect the time of indexing and searching cost a lot.

# Example index entries
1. In the addDoc method there is an example to show adding the index of author, title, isbn, format, and price.

<img width="718" alt="Screenshot 2024-01-13 at 1 44 20 PM" src="https://github.com/HarrisonPW/Open-Source-Search-Engine/assets/32474200/3e0c5ba9-0c85-4c69-8aa5-00a5b7fadaa3">

2. The indexing file in be stored at ``` src/main/java/org/example/dir ```

<img width="261" alt="Screenshot 2024-01-13 at 1 45 48 PM" src="https://github.com/HarrisonPW/Open-Source-Search-Engine/assets/32474200/f011bdd9-248b-4446-bf95-6b3dfb800356">

# Design and Implementation
## Crawler Designs
The BookCrawler class fetches book data from a web source by issuing HTTP GET requests. Returned HTML content is parsed using JSoup to get book details, and then Book objects are created with this.

### Benefits:
**Flexibility:** Customizable for any given data source by modifying the URL and parsing logic.

**Simplicity:** Simple HTML parsing is done using JSoup, and data can be pulled out from there.
### Disadvantages:
**Scalability:** The current implementation is synchronous and therefore may not scale well if the pages increase significantly in number or data size.

**Error handling:** Dealing with request failures, as well as unexpected changes in the HTML structure, are limited.
# Search Engine Architecture
The searching engine makes use of Lucene for the purpose of indexing and searching. It builds an inverted index of book documents, which leads to efficient search operations as per numerous attributes.

### Pros:
**Performance:** The Lucene itself is optimized at a greater extent to offer fast search operations.

**Flexibility:** The same supports complex queries and a wide variety of search capabilities.
### Cons:
**Complex Setup:** Lucene's initial setup and configuration can be a bit complex for beginners.

**Consume many resources:** Indexing and searching consume many system resources, especially in the case of a large dataset.


# Experience with Lessons Learned
The experience gained during the development of the toy search engine and crawler was proved to be very educative on how search engines as well the web crawlers works. Key takeaways include good indexing data structures, difficulties in web scraping (for example dynamic content and rate limiting), and power of Lucene either by itself or applied to build custom searches.

