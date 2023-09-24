package unipiloto.edu.co;

public class User {
    public String FULL_NAME;
    public String USERNAME;
    public String EMAIL;
    public String PASSWORD;
    public String BORN_DATE;
    public String LATITUDE;
    public String LENGTH;
    public String ALTITUDE;
    public String PRECISION;
    public String ROL;
    public int GENDER;

    public User(String FULL_NAME, String USERNAME, String EMAIL, String PASSWORD,
                String BORN_DATE, String LATITUDE, String LENGTH, String ALTITUDE,
                String PRECISION, String ROL, int GENDER) {
        this.FULL_NAME = FULL_NAME;
        this.USERNAME = USERNAME;
        this.EMAIL = EMAIL;
        this.PASSWORD = PASSWORD;
        this.BORN_DATE = BORN_DATE;
        this.LATITUDE = LATITUDE;
        this.LENGTH = LENGTH;
        this.ALTITUDE = ALTITUDE;
        this.PRECISION = PRECISION;
        this.ROL = ROL;
        this.GENDER = GENDER;
    }

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public String getUSERNAME() {
        return USERNAME;
    }

    public String getEMAIL() {
        return EMAIL;
    }

    public String getPASSWORD() {
        return PASSWORD;
    }

    public String getBORN_DATE() {
        return BORN_DATE;
    }

    public String getLATITUDE() {
        return LATITUDE;
    }

    public String getLENGTH() {
        return LENGTH;
    }

    public String getALTITUDE() {
        return ALTITUDE;
    }

    public String getPRECISION() {
        return PRECISION;
    }

    public String getROL() {
        return ROL;
    }

    public int getGENDER() {
        return GENDER;
    }
}
