package service;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static constants.ArticleConstants.ARTICLES_URL;

public class ArticleQueryExecutor {

    private int limit;
    private Map<String, Integer> mapTitles = new LinkedHashMap<>();

    public void readEntry() throws IOException {
        System.out.println(LocalDateTime.now() + " INFO Reading the entry");
        System.out.println("...............");

        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

        System.out.print("Enter the limit value: ");
        limit = Integer.parseInt(reader.readLine());

        if (limit <= 0) {
            System.out.println("\nLimit must be greater than zero");
            System.exit(1);
        }
    }

    public void fetchArticles() throws IOException {
        int page = 1;
        int totalPage = page;
        String response;

        do {
            URL obj = new URL(ARTICLES_URL + "?page=" + page);
            HttpURLConnection con = (HttpURLConnection) obj.openConnection();
            con.setRequestMethod("GET");
            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

            while ((response = in.readLine()) != null) {
                JsonObject jsonResponse = new Gson().fromJson(response, JsonObject.class);
                totalPage = jsonResponse.get("total_pages").getAsInt();
                JsonArray data = jsonResponse.getAsJsonArray("data");

                for (JsonElement e : data) {
                    JsonElement titleElement = e.getAsJsonObject().get("title").isJsonNull() ? e.getAsJsonObject().get("story_title") :
                            e.getAsJsonObject().get("title");

                    if (!titleElement.isJsonNull()) {
                        String title = titleElement.getAsString();

                        JsonElement commentElement = e.getAsJsonObject().get("num_comments");
                        int numComments = commentElement.isJsonNull() ? 0 : commentElement.getAsInt();

                        mapTitles.put(title, numComments);
                    }
                }
            }
        } while (++page <= totalPage);
    }

    public void topArticles() {
        Map<String, Integer> mapSortedTitles = mapTitles.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
                        (e1, e2) -> e1, LinkedHashMap::new));

        List<String> topTitles = new LinkedList<>(mapSortedTitles.keySet());
        topTitles = topTitles.stream().limit(limit).collect(Collectors.toList());

        System.out.println("\nTop " + limit + " Articles:");
        for (String title : topTitles) {
            System.out.println("Comments: " + mapSortedTitles.get(title) + ", Title: " + title);
        }
    }
}
