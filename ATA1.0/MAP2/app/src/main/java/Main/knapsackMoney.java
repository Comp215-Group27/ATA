package Main;
import java.util.ArrayList;
/**
 * Created by Yifan Zhu, Group 27, COMP215, University of Liverpool
 * This class is a knapsack, it takes visiting money as its parameter
 * The reuse of this code is adapted from:
 * https://medium.com/@ssaurel/solving-the-knapsack-problem-in-java-c985c71a7e64
 */
public class knapsackMoney {
    public double table[][];
    public ArrayList<Item> list;
    public int knapWeight;
    public double optimalvalue;

    public knapsackMoney(ArrayList<Item> items, int knapWeight)
    {
        table = new double[items.size()+1][knapWeight+1];
        list = items;
        this.knapWeight = knapWeight;
        for(int i=0;i<=items.size();i++)
        {
            for(int j=0;j<=knapWeight;j++)
            {
                table[i][j] = -1;
            }
        }
    }
    public ArrayList<Item> getKnapsack()
    {
        optimalvalue = fillTable(list.size(),knapWeight);
        ArrayList<Item> sackList = returnItems();
        return sackList;
    }
    private double fillTable(int n,int weight)
    {
        double t;
        if(weight==0)
        {
            table[n][weight]=0;
            t = 0;
        }
        else if(n==0)
        {
            table[n][weight]=0;
            t = 0;
        }
        else if(table[n][weight]!=-1)
        {
            t = table[n][weight];
        }
        else
        {
            double a  = fillTable(n-1,weight);
            double b;
            //System.out.println("the "+n);
            if(weight>=list.get(n-1).getMoneyWeight())
            {
                b = fillTable(n-1,weight-list.get(n-1).getMoneyWeight())+list.get(n-1).getValue();
                if(a>=b)
                {
                    table[n][weight] = a;
                    t = a;
                }
                else
                {
                    table[n][weight] = b;
                    t = b;
                }
            }
            else
            {
                table[n][weight] = a;
                t = a;
            }
        }
        return t;
    }
    private ArrayList<Item> returnItems()
    {
        ArrayList<Item> l = new ArrayList<Item>();
        int j = knapWeight;
        for(int i=list.size();i>0;i--)
        {
            double val = table[i][j];
            double val2;
            if(j>=list.get(i-1).getMoneyWeight())
            {
                val2 = table[i-1][j-list.get(i-1).getMoneyWeight()];
                if(val2+list.get(i-1).getValue()==val)
                {
                    l.add(list.get(i-1));
                    j = j-list.get(i-1).getMoneyWeight();
                }
            }
        }
        return l;
    }

}
