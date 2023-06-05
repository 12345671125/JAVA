package Sort;
import Elevator.Elevator;
public class qSort {
    public static void quickSort(Elevator[] ElevatorArr,int mode) {

        sort(ElevatorArr, 0, ElevatorArr.length - 1,mode);
    }

    private static void sort(Elevator[] ElevatorArr, int left, int right,int mode) {
        if (left < right) {
            int pivotIdx = partition(ElevatorArr, left, right,mode);
            sort(ElevatorArr, 0, pivotIdx - 1,mode);
            sort(ElevatorArr, pivotIdx + 1, right,mode);
        }
    }

    private static int partition(Elevator[] ElevatorArr, int left, int right,int mode) {
        int idx = left + 1;
        if(mode == 1){
            for (int i = idx; i <= right; i++) {
                if (ElevatorArr[left].getFloor() > ElevatorArr[i].getFloor()) {
                    swap(ElevatorArr, i, idx++);
                }
            }
        }else if(mode == -1){
            for (int i = idx; i <= right; i++) {
                if (ElevatorArr[left].getTarget() > ElevatorArr[i].getTarget()) {
                    swap(ElevatorArr, i, idx++);
                }
            }
        }

        swap(ElevatorArr, left, idx - 1);
        return idx - 1;
    }

    private static void swap(Elevator[] ElevatorArr, int idx1, int idx2) {
        Elevator tmp = ElevatorArr[idx1];
        ElevatorArr[idx1] = ElevatorArr[idx2];
        ElevatorArr[idx2] = tmp;
    }
}

