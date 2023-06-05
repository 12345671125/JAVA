import Elevator.Elevator;
import Sort.qSort;
//import People.People;
import java.util.Scanner;
public class Main {
   static Elevator[] ElevatorArr = new Elevator[2]; //设置电梯数组内电梯数为2
   static int now = 0; //设置静态变量用于记录当前所在层
   static int to = 0; //设置静态变量用于记录当前想去层

   static public void main(String[] args) {
       for (int i = 0; i < 2; i++) {   //循环实例化两台电梯
           ElevatorArr[i] = new Elevator(i + 1);
       }
        while (true) { //循环
            String stasus;  //用于记录人的状态 上行/下行
            System.out.println("请输入当前楼层数");
            Scanner s = new Scanner(System.in); //通过Scanner获取输入
             now = s.nextInt(); //通过Scanner获取输入
             if(now == -1){   //当楼层数输入-1时退出
                 System.out.println("即将结束程序");
                 break; //跳出循环
             }
            System.out.println("请输入想去楼层数");
             to = s.nextInt();//通过Scanner获取输入
             if(now>to){
                 stasus = "下行"; //设置人的状态
             }else{
                 stasus = "上行"; //设置人的状态
             }
            dispatch(now,to,stasus); //调度
             for(int i = 0;i<ElevatorArr.length;i++){  //循环生成新的电梯状态
                 ElevatorArr[i].createTarget();
             }
        }

    }
    static void  dispatch(int now,int to,String status) {     //用于电梯调度
//        qSort qsort = new qSort(); //创建快速排序对象
        int index = -1;//电梯编号
        int flag = 0; //标志位
        for(int i = 0;i<ElevatorArr.length;i++){      //查看当前是否有电梯在同一层并且方向相同
                if(ElevatorArr[i].getFloor() == now && ElevatorArr[i].getStatus() == status){  //循环查看是否有电梯在同一层且方向相同
                    index = ElevatorArr[i].getCode(); //如果有记录电梯的id
                    break;//跳出循环 只需要一台电梯
                }
        }
        if(index != -1 && flag == 0)  //如果有电梯在同一层且方向相同，且任务并未分配
        {
//            System.out.println("情况1");
            for(int i = 0;i<ElevatorArr.length;i++){
                if(ElevatorArr[i].getCode() == index)  //选择对应代号的电梯
                {
                    System.out.println("将调用电梯"+ElevatorArr[i].getCode()); //这句用于输出调用选择的电梯
                    flag = 1; //设置标志位为任务完成
                    if(ElevatorArr[i].getTarget() < to){  //如果当前电梯原先目标楼层<新的目标楼层
                        ElevatorArr[i].setFloor(ElevatorArr[i].getTarget()); //完成原先目标
                        ElevatorArr[i].setFloor(to); //完成新的目标
                    }else if(ElevatorArr[i].getTarget() > to){   //如果当前电梯原先目标楼层 > 新的目标楼层
                        ElevatorArr[i].setFloor(to); //完成新的目标
                        ElevatorArr[i].setFloor(ElevatorArr[i].getTarget()); //完成原先目标
                    }else{
                        ElevatorArr[i].setFloor(to); //完成新的目标
                    }
                    ElevatorArr[i].setWorking(true);//设置当前电梯工作状态为正在工作 其实并没什么用
                    break;//退出循环
                }
            }
        }
        if(flag  == 0) //如果并不满足上一种情况，判断是否有电梯方向相同
        {
//            System.out.println("情况2");
            for(int i = 0;i<ElevatorArr.length;i++){      //循环查看当前是否有电梯方向相同
                if(ElevatorArr[i].getStatus() == status){
                    index = ElevatorArr[i].getCode();//如果有则记录下满足条件的电梯编号
                    break; //跳出循环
                }
            }
            if(index != -1){ //有电梯方向相同
                    qSort.quickSort(ElevatorArr,1); //按照当前所在楼层从小到大排序
                for(int i = 0;i<ElevatorArr.length;i++){
                    if(ElevatorArr[0].getFloor() >= now){
                        break;
                    }
                    if(ElevatorArr[i].getStatus() == status){ //选择方向相同且最近的
                        System.out.println("将调用电梯"+ElevatorArr[i].getCode());
                        flag = 1; //设置标志位为任务完成
                        if(ElevatorArr[i].getTarget() < to){  //如果当前电梯原先目标楼层<新的目标楼层
                            ElevatorArr[i].setFloor(ElevatorArr[i].getTarget()); //完成原先目标
                            ElevatorArr[i].setFloor(to); //完成新的目标
                        }else if(ElevatorArr[i].getTarget() > to){   //如果当前电梯原先目标楼层 > 新的目标楼层
                            ElevatorArr[i].setFloor(to); //完成新的目标
                            ElevatorArr[i].setFloor(ElevatorArr[i].getTarget()); //完成原先目标
                        }else{
                            ElevatorArr[i].setFloor(to); //完成新的目标
                        }
                        ElevatorArr[i].setWorking(true);
                        break;//退出循环
                    }
                }
            }

        }
        if(flag == 0){  //如果以上两种情况均无,则电梯方向均不同,那么选择完成原先目标后最近的
//            System.out.println("情况3");
            qSort.quickSort(ElevatorArr,-1); //按目标楼层排序
            System.out.println("将调用电梯"+ElevatorArr[0].getCode());
            flag = 1; //设置标志位为任务完成
            ElevatorArr[0].setFloor(ElevatorArr[0].getTarget());//先完成目标楼层
            ElevatorArr[0].setFloor(now);//到达所在楼层
            ElevatorArr[0].setFloor(to);//完成新目标
            ElevatorArr[0].setWorking(true);
        }
            for(int i = 0;i<ElevatorArr.length;i++){
                if(ElevatorArr[i].getWorking() == false){
                    ElevatorArr[i].setFloor(ElevatorArr[i].getTarget());
                }
                ElevatorArr[i].setWorking(false);
            }
   }
}
