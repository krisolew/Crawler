package Crawler;

import Crawler.GUI.SpecialInteager;

public class SkierData {

    private String name;
    private SpecialInteager points;
    private String country;

    SkierData(String name, SpecialInteager points, String country) {
        this.name = name;
        this.points = points;
        this.country = country;
    }

    public String toString(){
        return points + "\t" + country + "\t" + name.trim() + "\n";
    }
}
