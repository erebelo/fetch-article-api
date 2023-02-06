import service.ArticleQueryExecutor;

import java.io.IOException;

public class ArticleApplication {

    public static void main(String[] args) throws IOException {
        ArticleQueryExecutor queryExecutor = new ArticleQueryExecutor();
        queryExecutor.readEntry();
        queryExecutor.fetchArticles();
        queryExecutor.topArticles();
    }
}
