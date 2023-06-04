package People;

import java.util.Random;

public class People {
   private String name;  //姓名
   private int floorOn;  //所在楼层
   private int floorTo;  //想去楼层

   public People(String name){
       this.name = name;
       this.floorOn = (new Random()).nextInt(1,6);
       this.floorTo = (new Random()).nextInt(1,6);
   }
    public String getName() {
        return name;
    }

    public int getFloorOn() {
        return floorOn;
    }

//    public int getFloorTo() {
//        return floorTo;
//    }
}
