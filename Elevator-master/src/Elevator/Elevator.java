package Elevator;
import Task.Task;

import java.util.PriorityQueue;
import java.util.Queue;
import java.util.Random;
import java.util.Comparator;
//import java.util.Timer;
//import java.util.TimerTask;

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
    private Comparator<Task> cmp; //比较器
    private int target; //目标楼层

    public Thread workThread;
    public PriorityQueue<Task> taskQueue; //优先任务队列

    public Elevator(int code){   //构造函数，完成初始化
        this.code = code;
        this.doorOpen = false;
        Random r = new Random();
        this.floor  = r.nextInt(10)+1;  //构造电梯时随机楼层
        this.target  = r.nextInt(10)+1;  //构造电梯时随机楼层
        if(this.floor > this.target){
            this.status = "下行";
        }else if(this.floor < this.target){
            this.status = "上行";
        }else{
            this.status = "停止";
        }
        this.cmp = new Comparator<Task>() {   //设置比较器
            @Override
            public int compare(Task o1, Task o2) {
                return o2.Priority - o1.Priority;
            }
        };
        this.taskQueue = new PriorityQueue<Task>(5,cmp); //初始化任务队列
        System.out.println("当前电梯"+this.code + "在"+this.floor+"层准备"+this.status+"到第"+this.target+"层");
        this.weight = 0;
        Task task = new Task(floor,target,5);
        this.taskQueue.add(task);
    }
    public void openDoor() throws InterruptedException {
        doorOpen = true;
        Thread.sleep(1000);
        System.out.println(this.code+"门已打开");
    }
    public void closeDoor() throws InterruptedException {
        if(!checkWeight()){
            doorOpen = false;
            Thread.sleep(1000);
            System.out.println(this.code+"门已关闭");
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
    public void goUp() throws InterruptedException {   //电梯上升
        if(!this.doorOpen){
            floor++;
            Thread.sleep(1000);
            System.out.println("电梯"+this.code+"已上到第"+this.floor+"层");
        }else if(this.floor == TOP_FLOOR){
            System.out.println("电梯已到顶层");
        }else{
            System.out.println("电梯门未关");
        }
    }
    public void goDown() throws InterruptedException {    //电梯下降
        if(!this.doorOpen){
            floor--;
            Thread.sleep(1000);
            System.out.println("电梯"+this.code+"已下降到第"+this.floor+"层");
        }else if(this.floor == BOTTOM_FLOOR){
            System.out.println("电梯已到底层");
        }else{
            System.out.println("电梯门未关");
        }
    }
    public void setFloor(int goal) throws InterruptedException {    //指定电梯停靠
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
//        this.workThread.sleep(10000);
//        this.createTarget();
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
        this.target  = r.nextInt(10)+1;  //构造电梯时随机楼层
        if(this.floor > this.target){
            this.status = "下行";
        }else if(this.floor < this.target){
            this.status = "上行";
        }else{
            this.status = "停止";
        }
        Task task = new Task(floor,target,5);
        this.taskQueue.add(task);
     System.out.println("当前电梯"+this.code + "在"+this.floor+"层准备"+this.status+"到第"+this.target+"层");
    }
    public void addTask(int in,int target,int Priority){
        Task task = new Task(in,target,Priority);
        this.taskQueue.add(task);
        System.out.println(this.taskQueue.isEmpty());
    }
    public void startThread(){
            this.workThread.start();
    }
    public void setThreadJoin() throws InterruptedException {
        this.workThread.join();
    }

    public void setTarget(int target) {
        this.target = target;
    }
}
