package Crawler;

import Crawler.GUILogic.RightPanelLogic;

public class SkierData {

    private String name;
    private int points;
    private String country;

    SkierData(String name, int points, String country) {
        this.name = name;
        this.points = points;
        this.country = country;
    }

    public String toString() {
        return RightPanelLogic.formatNumber(points) + "\t" + country + "\t" + name.trim() + "\n";
    }
}
