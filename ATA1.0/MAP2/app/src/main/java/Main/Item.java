package Main;
/**
 * /**
 * Created by Yifan Zhu, Group 27, COMP215, University of Liverpool
 * This class is created to create objects of tourist attractions
 */

public class Item {
    private String  name;
    private int value;
    private int time;
    private int money;

    public Item(String paraName, int paraValue, int paraTime, int paraMoney  ){
        name = paraName;
        value = paraValue;
        time = paraTime;
        money = paraMoney;

    }
    public String getName(){
        return name;
    }

    public int getValue(){
        return value;
    }

    public int getTimeWeight(){
        return time;
    }
    public int getMoneyWeight(){
        return money;
    }
}
