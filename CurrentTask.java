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
public class CurrentTask extends TaskInfo{
    int isFeasible;
    double TaskReqNow;
    
    public CurrentTask(String TaskTitle, double QualityReq){
        super(TaskTitle, QualityReq);
        
    }
    public String getTaskTitle(){
        return this.TaskTitle;
    }
    public void resetCompleteRequirement(){
        TaskReqNow = QualityReq;
    }
    
    public void completeRquirement(double input){
        if (input >= TaskReqNow){
            TaskReqNow = 0;
        }else {
            TaskReqNow -= input;
        }
        
    }
    
    public boolean IsFeasible(){
        if (isFeasible == 1){
            return true;
        }else {
            return false;
        }
    }
    
}
