package Crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FisPageCrawler {
    private String address;
    private String pageContent;
    private static String namePattern = "<div class=\"g-xs-10 g-sm-9 g-md-4 g-lg-4 justify-left bold align-xs-top\">([^<]*)<";
    private static String countryPattern =  "<span class=\"country__name-short\">(\\w\\w\\w)<";
    private static String pointsPattern = "<div class=\"pl-xs-1 pl-sm-1 g-xs-10 g-sm-7 g-md-9 g-lg-8 justify-right bold\">([^<]*)<";
    private List <String> names;
    private List <String> points;
    private List <String> countries;

    public FisPageCrawler(String address){
        this.address = address;
        names = new LinkedList<>();
        points = new LinkedList<>();
        countries = new LinkedList<>();
    }

    List<String> getNames(){
        return names;
    }

    List<String> getPoints() {
        return points;
    }

    List<String> getCountries() {
        return countries;
    }

    void crawl() throws IOException {
        URL url = new URL(address);

        try ( BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()))) {
            StringBuilder builder = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            pageContent = builder.toString();
            findInformation(namePattern,names);
            findInformation(pointsPattern,points);
            findInformation(countryPattern,countries);
        }
    }

    private void findInformation(String classPattern, List<String> list){
        Pattern pattern = Pattern.compile(classPattern);
        Matcher matcher = pattern.matcher(pageContent);

        while (matcher.find()){
            String w = matcher.group(1);
            list.add(w);
        }
    }
}
