/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package crowdsourcing;
//This is for general message of all possible tasks
public class TaskInfo {
    public String TaskTitle;
    public double QualityReq;
    
    public TaskInfo(String taskTitle, double qualityReq){
        this.TaskTitle = taskTitle;
        this.QualityReq = qualityReq;
    }
}
