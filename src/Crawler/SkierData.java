package Crawler;

public class SkierData {
    private String name;
    private String points;
    private String country;

    public SkierData(String name, String points, String country) {
        this.name = name;
        this.points = points;
        this.country = country;
    }

    public String toString(){
        return points.trim() + "\t" + country + "\t" + name.trim() + "\n";
    }

    public String getPoints(){
        return points;
    }
}
