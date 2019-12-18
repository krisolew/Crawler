package Crawler.Enums;

public enum RaceType {
    ALL,
    SL,
    GS,
    SG,
    DH;

    @Override
    public String toString() {
        switch (this) {
            case ALL:
                return "Overall";
            case SL:
                return "Slalom";
            case GS:
                return "Slalom Gigant";
            case SG:
                return "Super Gigant";
            case DH:
                return "Downhill";
        }
        return "";
    }
}
