package org.example;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Extractor {
    public List<Chair> getChairs(String url){

        String role = null, name = null, email = null;
        List<Chair> chairs = new ArrayList<>();

        try {
            Document document = Jsoup.connect(url).get();
            Elements allPersons = document.select("tr");

            for (Element person: allPersons) {
                Elements selectAllPersons = person.select("td");
                if (!(selectAllPersons.isEmpty()) && selectAllPersons.first().text().toLowerCase().contains("chair")) {
                    role = selectAllPersons.first().text();
                    name = person.select("td > strong > a").text();
                    email = person.select("td > a").text();

                    chairs.add(new Chair(name, role, email));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return chairs;
    }
    public List<ResearchLab> findResearchLabs(String url) {

        List<ResearchLab> researchLabs = new ArrayList<>();

        try {
            Document document = Jsoup.connect(url).get();
            Elements allLabs = document.select("li");

            for (Element lab: allLabs) {
                String labName = lab.select("a").text();
                String labLink = lab.select("a").attr("href");

                if (labLink.startsWith("research-areas/") && !(labLink.endsWith("/index.html"))) {
                    if (url.endsWith("index.html")) {
                        url = url.substring(0, url.length() - "index.html".length());
                    }
                    labLink = url + labLink;
                    researchLabs.add(new ResearchLab(labName, labLink));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return researchLabs;
    }

}
