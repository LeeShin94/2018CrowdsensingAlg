/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crowdsourcing;

/**
 *
 * @author shinlee
 */


public class CurrentProvider extends ProviderInfo{
    public double Bid = 0.0;
    public String[] TaskBundle;
    public int NoTasks = 0;
    public double Utility = 0.0;
    public CurrentProvider(String PName, double Pq, String[] Bundle, double bid){
        super(PName, Pq);
        this.Bid = bid;
        this.TaskBundle = Bundle;
    }
    
    public void updateBidBundle(int count){
        NoTasks = TaskBundle.length - count;
        Bid -= count*0.2*Pq;
        //System.out.println(NoTasks + ","+ Bid);
    }
    public void setUtility(){
        Utility = NoTasks* 0.2 * Pq - Bid;
        Utility = Math.round(Utility*100.0)/100.0;
        //System.out.println(Utility);
    }
    
        
    
    
}

