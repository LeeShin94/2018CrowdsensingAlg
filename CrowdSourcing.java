/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crowdsourcing;

import java.util.ArrayList;
import java.util.Collections;


/**
 *
 * @author shinlee
 */
public class CrowdSourcing {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        //create list of tasks at current round
        ArrayList <CurrentTask> Tasklist = new ArrayList<CurrentTask>();
        Tasklist.add(new CurrentTask("T1",1.2));
        Tasklist.add(new CurrentTask("T2",1.0));
        Tasklist.add(new CurrentTask("T3",0.7));
        Tasklist.add(new CurrentTask("T4",0.9));
        Tasklist.add(new CurrentTask("T5",1.5));
        
        ArrayList <String> TaskLeft = new ArrayList<String>(); //store infeasible tasks
        
        
        String[][] Ptask = {{"T1", "T2", "T5"},{"T3", "T4", "T5"},{"T1", "T5"},{"T2", "T4"}};
        
        ArrayList <CurrentProvider> Providerlist = new ArrayList<CurrentProvider>();
        Providerlist.add(new CurrentProvider("P1",0.7,Ptask[0],1.2));
        Providerlist.add(new CurrentProvider("P2",0.9,Ptask[1],0.8));
        Providerlist.add(new CurrentProvider("P3",1.1,Ptask[2],0.6));
        Providerlist.add(new CurrentProvider("P4",0.8,Ptask[3],1.5));
        
        
        //feasible(correct)
        
        for (int i = 0; i < Tasklist.size(); i++){
            int count = 0;
            for (int j = 0; j<Providerlist.size();j++){
                String[] PBundle = Providerlist.get(j).TaskBundle;
                for (int x =0; x< PBundle.length;x++){
                    if (PBundle[x].equals(Tasklist.get(i).TaskTitle.toString()) == true){
                        count ++;
                    }
                }
            }
            
            if (count <= 1){
                TaskLeft.add(Tasklist.get(i).TaskTitle); //store infeasible tasks
                Tasklist.remove(i);//update task list
            }
        }
        
        //update provider bundle
        for (int i = 0; i< Providerlist.size();i++){
            int count = 0;
            String[] currentPB = Providerlist.get(i).TaskBundle;
            for (String x: TaskLeft){
                for (String y: currentPB){
                    if (x.equals(y)){
                        count ++;
                    }
                }
            }
            Providerlist.get(i).updateBidBundle(count);
            Providerlist.get(i).setUtility();
            
        }
        
        /*for (int i = 0; i< Tasklist.size(); i++){
            System.out.println(Tasklist.get(i).getTaskTitle());
        }
        for (int i = 0; i< TaskLeft.size(); i++){
            System.out.println(TaskLeft.get(i).toString());
        }*/
        
        //Winner Selection
        ArrayList <Integer> WinnerNo = new ArrayList<>(); //store winning provider's no
        ArrayList <Integer> NegativeP = new ArrayList<>(); //store negative provider's no
        for(CurrentTask x: Tasklist){ //rest the task requirements first
            x.resetCompleteRequirement();
        }
        
        for(int i =0; i< Providerlist.size();i++){
            if (Providerlist.get(i).Utility >= 0){ //find positive utility
                WinnerNo.add(i);
                String[] ProviderBundle = Providerlist.get(i).TaskBundle;
                for (int j = 0; j <Tasklist.size();j++){
                    for (String z: ProviderBundle){
                        if (z.equals(Tasklist.get(j).TaskTitle) == true){ //use positive P's quality to complete task
                            Tasklist.get(j).completeRquirement(Providerlist.get(i).Pq);
                        }
                    }
                }
            } else { //negative providers
                NegativeP.add(i);
                //System.out.println(NegativeP.toString());
            } 
        }
        
        double RequirementsLeft = 0;
        for (CurrentTask x: Tasklist){  //calculate sum requirement after positive selected
            RequirementsLeft+=x.TaskReqNow;
        }
        //System.out.println(RequirementsLeft);
        /*while (RequirementsLeft != 0){ //complete rest of the requirment
            for (int x: NegativeP){
                
                
            }
        }*/
        ArrayList <Double> PMarginal = new ArrayList<>();
        for (int i: NegativeP){
            double sumReq =0;
            for (String x :Providerlist.get(i).TaskBundle){
                //System.out.println(x);
                for (CurrentTask y: Tasklist){
                    if (x.equals(y.TaskTitle) == true){
                        sumReq += Math.min(y.TaskReqNow, Providerlist.get(i).Pq);
                    }
                }
            }
            double marginal =  (-Providerlist.get(i).Utility)/sumReq;
            PMarginal.add(marginal);
            //System.out.println(PMarginal);
        }
        int minIndex = PMarginal.indexOf(Collections.min(PMarginal)); //get index of the winner
        //System.out.println(minIndex);
        //System.out.println(NegativeP.get(minIndex));
        String[] ProviderBundle = Providerlist.get(minIndex).TaskBundle; //update requirments
                for (int j = 0; j <Tasklist.size();j++){
                    for (String z: ProviderBundle){
                        if (z.equals(Tasklist.get(j).TaskTitle) == true){ 
                            Tasklist.get(j).completeRquirement(Providerlist.get(minIndex).Pq);
                        }
                    }
                }
        RequirementsLeft = 0;
        for (CurrentTask x: Tasklist){  //calculate sum requirement after positive selected
            RequirementsLeft+=x.TaskReqNow;
        }
        //System.out.println(RequirementsLeft);        
        
        WinnerNo.add(NegativeP.get(minIndex));
        NegativeP.remove(minIndex);
        
        
        //System.out.println(WinnerNo.toString());
        //System.out.println(NegativeP.toString());
        //System.out.println(Tasklist.get(0).TaskReqNow);
        
     
            
    }
    
    
    
}
