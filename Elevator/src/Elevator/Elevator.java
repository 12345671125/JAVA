package Elevator;

import java.util.Random;
public class Elevator {
    private boolean doorOpen; //电梯门是否打开
    private int code;  //当前电梯编号
    private int floor; //当前楼层数
    private int weight; //当前载客量
    private String status;//运行状态
    private boolean working = false; //是否在工作
    static final int CAPACITY = 1000; //最大载客量
    static final int TOP_FLOOR = 10; //最大楼层
    static final int BOTTOM_FLOOR = 1; //最小楼层
    private int target; //目标楼层

    private Thread workThread;

    public Elevator(int code){   //构造函数，完成初始化
        this.code = code;
        this.doorOpen = false;
        Random r = new Random();
        this.floor  = r.nextInt(1,11);  //构造电梯时随机楼层
        this.target  = r.nextInt(1,11);  //构造电梯时随机楼层
        if(this.floor > this.target){
            this.status = "下行";
        }else if(this.floor < this.target){
            this.status = "上行";
        }else{
            this.status = "停止";
        }
        System.out.println("当前电梯"+this.code + "在"+this.floor+"层准备"+this.status+"到第"+this.target+"层");
        this.weight = 0;

    }
    public void openDoor() {
        doorOpen = true;
        System.out.println("门已打开");
    }
    public void closeDoor(){
        if(!checkWeight()){
            doorOpen = false;
            System.out.println("门已关闭");
        }else{
            System.out.println("超重! 电梯无法运行");
        }

    }
    public boolean checkWeight(){            //检查体重
        if(this.weight <= CAPACITY) {
            return false;
        }else{
            return true;
        }
    }
    public void goUp(){   //电梯上升
        if(!this.doorOpen){
            floor++;
            System.out.println("电梯"+this.code+"已上到第"+this.floor+"层");
        }else if(this.floor == TOP_FLOOR){
            System.out.println("电梯已到顶层");
        }else{
            System.out.println("电梯门未关");
        }
    }
    public void goDown(){    //电梯下降
        if(!this.doorOpen){
            floor--;
            System.out.println("电梯"+this.code+"已下降到第"+this.floor+"层");
        }else if(this.floor == BOTTOM_FLOOR){
            System.out.println("电梯已到底层");
        }else{
            System.out.println("电梯门未关");
        }
    }
    public void setFloor(int goal) {    //指定电梯停靠
        if(goal<=TOP_FLOOR && goal >= BOTTOM_FLOOR){   //防御性编程
            this.working = true;
            while(this.floor != goal){
                if(goal > this.floor){
                    goUp();
                }else{
                    goDown();
                }
            }
            System.out.println("电梯停在第"+goal+"层");
            this.working = false;
            this.openDoor();
            this.floor = goal;
            this.closeDoor();
        }else{
            System.out.println("没有给定的楼层");
        }
    }
    public int getFloor(){   //获取当前楼层
        return  floor;
    }
    public  boolean isDoorOpen(){
        return this.doorOpen;
    }
    public boolean getWorking(){
        return this.working;
    }

    public int getCode() {
        return code;
    }

    public int getTarget() {
        return target;
    }

    public String getStatus() {
        return status;
    }

    public void setWorking(boolean working) {
        this.working = working;
    }
    public void createTarget(){
        Random r = new Random();
        this.target  = r.nextInt(1,11);  //构造电梯时随机楼层
        if(this.floor > this.target){
            this.status = "下行";
        }else if(this.floor < this.target){
            this.status = "上行";
        }else{
            this.status = "停止";
        }
        System.out.println("当前电梯"+this.code + "在"+this.floor+"层准备"+this.status+"到第"+this.target+"层");
    }

}
