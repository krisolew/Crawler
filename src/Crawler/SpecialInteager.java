package Crawler;

public class SpecialInteager {
    Integer i;

    public SpecialInteager(int i){
        this.i = i;
    }

    public SpecialInteager(String i){
        this.i = Integer.parseInt(i);
    }

    public String toString(){
        int numOfDigits=3;
        int tmp = i;
        while (tmp > 0){
            numOfDigits--;
            tmp/=10;
        }
        String space="";
        while (numOfDigits>0){
            space = space + " ";
            numOfDigits--;
        }
        return space + i;
    }
}
