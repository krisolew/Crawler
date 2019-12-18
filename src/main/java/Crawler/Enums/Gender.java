package Crawler.Enums;

public enum Gender {
    MALE,
    FEMALE;


    @Override
    public String toString() {
        switch (this) {
            case MALE:
                return "Men";
            case FEMALE:
                return "Ladies";
        }
        return "";
    }

    public String getFirstLetter() {
        switch (this) {
            case MALE:
                return "M";
            case FEMALE:
                return "L";
        }
        return "";
    }

    public String getOppositeGenderFirstLetter() {
        switch (this) {
            case MALE:
                return "L";
            case FEMALE:
                return "M";
        }
        return "";
    }
}
