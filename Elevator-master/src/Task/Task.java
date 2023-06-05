package Task;

public class Task {
    public int in;
    public int target;
    public String status;
    public int Priority; //任务优先级 20为优先级最高
    public Task(int in,int target,int Priority){
        this.in = in;
        this.target = target;
        if(this.in > this.target)
            status = new String("下行");
        else if(this.in < this.target)
            status = new String("上行");
        this.Priority = Priority;

    }

    @Override
    public String toString() {
        return "in="+this.in+";target:"+this.target+";priority:"+this.Priority;
    }
}
