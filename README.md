# Java Top Article Fetch
Java program that fetches articles from an external API and sorts them to return the top N elements.

----------

## Description

Query a REST API to get information about a list of articles.
Given an integer, limit, return the top limit article names ordered decreasing by comment count, then increasing alphabetically for those that have the same comment counts.
To access the collection of users perform HTTP GET request to: https://jsonmock.hackerrank.com/api/articles?page={pageNumber} where {pageNumber} is an integer denoting the page of the results to return and 1 <= pageNumber <= total_pages.
- If the title field is null, use the story_title field in its place
- If the story_title field is also null, discard this data

----------

## Dependencies

Add the following libraries found in the "libraries" folder as module dependencies in the project
- gson-2.9.0.jar

----------
