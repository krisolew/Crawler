package Crawler.GUILogic;

import Crawler.SkierData;

import java.util.List;

public class RightPanelLogic {

    public static String formatNumber(int number){
        int numOfDigits=4;
        int tmp = number;
        while (tmp > 0){
            numOfDigits--;
            tmp/=10;
        }
        String space="";
        while (numOfDigits>0){
            space = space + " ";
            numOfDigits--;
        }
        return space + number + space;
    }

    public String getContentFromList(List <SkierData> skiers)
    {
        String content;
        StringBuilder builder = new StringBuilder();
        int number = 1;

        builder.append("  Nr   " + "   Pkt  " + "    Country\t" + "\tName\n" );

        while (!skiers.isEmpty()) {
            SkierData skier = skiers.remove(0);
            builder.append(RightPanelLogic.formatNumber(number));
            builder.append("  ");
            builder.append(skier.toString());
            number++;
        }

        content = builder.toString();
        return content;
    }
}
