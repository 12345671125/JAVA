import Elevator.Elevator;
import Sort.qSort;
//import People.People;
import java.util.Scanner;
public class Main {
   static Elevator[] ElevatorArr = new Elevator[3];
   static int now = 0;
   static int to = 0;

   static public void main(String[] args) {
       for (int i = 0; i < 3; i++) {   //实例化
           ElevatorArr[i] = new Elevator(i + 1);
       }
        while (true) {
            String stasus;
            System.out.println("请输入当前楼层数");
            Scanner s = new Scanner(System.in);
             now = s.nextInt();
             if(now == -1){   //当楼层数输入-1时退出
                 System.out.println("即将结束程序");
                 break;
             }
            System.out.println("请输入想去楼层数");
             to = s.nextInt();
             if(now>to){
                 stasus = "下行";
             }else{
                 stasus = "上行";
             }
            dispatch(now,to,stasus);
             for(int i = 0;i<ElevatorArr.length;i++){
                 ElevatorArr[i].createTarget();
             }
        }

    }
    static void  dispatch(int now,int to,String status) {  
//        qSort qsort = new qSort(); //创建快速排序对象
        int index = -1;
        int flag = 0; //标志位
        for(int i = 0;i<ElevatorArr.length;i++){      //查看当前是否有电梯在同一层并且方向相同
                if(ElevatorArr[i].getFloor() == now && ElevatorArr[i].getStatus() == status){
                    index = ElevatorArr[i].getCode();
                    break;
                }
        }
        if(index != -1 && flag == 0)  //如果有电梯在同一层且方向相同
        {
//            System.out.println("情况1");
            for(int i = 0;i<ElevatorArr.length;i++){
                if(ElevatorArr[i].getCode() == index)  //选择对应代号的电梯
                {
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
        if(flag  == 0) //判断是否有电梯方向相同
        {
//            System.out.println("情况2");
            for(int i = 0;i<ElevatorArr.length;i++){      //查看当前是否有电梯方向相同
                if(ElevatorArr[i].getStatus() == status){
                    index = ElevatorArr[i].getCode();
                    break;
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
