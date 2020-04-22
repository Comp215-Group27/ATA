package Community.Common;

/**
 * Created by Haopeng Song, Group 27, Comp215, University of Liverpool
 */

/** A class to define comment format */

public class commentFormat {
    private String randomATA;
    private int userID;
    private String userName;
    private String places;
    private String comment;

    public commentFormat(String randomATA, int userID,String userName, String places, String comment) {
        this.randomATA=randomATA;
        this.userName=userName;
        this.userID=userID;
        this.places = places;
        this.comment = comment;
    }
    public int getuserID() {
        return userID;
    }
    public void setuserID(int userID) {
        this.userID= userID;
    }
    public String getuserName() {
        return userName;
    }
    public void setuserName(String userName) {
        this.userName= userName;
    }
    public String getRandomATA() {
        return randomATA;
    }
    public void setRandomATA(String randomATA) {
        this.randomATA= randomATA;
    }
    public String getPlaces() {
        return places;
    }
    public void setPlaces(String places) {
        this.places = places;
    }
    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        this.comment = comment;
    }
    @Override
    public String toString() {
        return  "   User Name: "+userName +"\n"+
                "   User ID: "+userID +"\n"+
                "   Time: "+ randomATA + ","+"\n"+
                "   Places: " + "\n"+places + ","+"\n"+
                "   Comment: " + comment + '\n' + '\n' ;
    }
}

