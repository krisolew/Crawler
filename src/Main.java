import java.awt.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        //Spider spider = new Spider();
        //spider.search("https://www.fis-ski.com/DB/alpine-skiing/cup-standings.html?sectorcode=AL&seasoncode=2019&cupcode=WC&disciplinecode=ALL&gendercode=M", "Marcel");

        /*Crawler clawler = new Crawler();
        clawler.search("https://www.fis-ski.com", "Marcel");
        clawler.ptintVisitedPages();*/
        //System.out.println(clawler.getLastPage());

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new MyFrame();
            }
        });
    }
}
