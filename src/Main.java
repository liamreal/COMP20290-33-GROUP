import javax.swing.*;
import java.sql.Time;
import java.util.Arrays;
import java.util.Random;

public class Main {
    static Random rand = new Random();
    static Random seedGen = new Random();
    private Main(){}

    public static void PTPSort(Integer[] a, int beg, int end) {

        int seed = seedGen.nextInt();
//        System.out.println("Seed: "+ seed);
        rand.setSeed(2046273340);

        //-1917837701
        //-1539768376 index -1 error
        //66046558 index 100 error
        //2046273340

        //finding middle index of current subarray
        int mid = (beg + end)/2;

        //finding different random indexes in subarray
        int iPLo = 0, iPMi = 0, iPHi = 0;
        while ((iPLo == iPMi) || (iPMi == iPHi) || (iPHi == iPLo)) {
            iPLo = Sampling(beg, end);
            iPMi = Sampling(beg, end);
            iPHi = Sampling(beg, end);


        }
        System.out.printf("\n\niPLo: %d, iPMi: %d, iPHi: %d", iPLo, iPMi, iPHi);

        //finding correspondent values of indexes
        int PLo = a[iPLo], PMi = a[iPMi], PHi = a[iPHi];
        if (PMi < PLo) {
            int temp = PLo;
            PLo = PMi;
            PMi = temp;
        }
        if (PHi < PMi) {
            int temp = PMi;
            PMi = PHi;
            PHi = temp;
        }

        //finding 3 initial pivots
        //  NOTE 1 : each will be a different task
        //  NOTE 2 : iL and iH are temporary and used to find ilm and imh
        System.out.println("\n1");
        int iL = Hoare(a, beg, beg+mid-1, PLo);
        System.out.println("2");
        int iH = Hoare(a, beg+mid, end, PHi);
        System.out.println("3");
        int iM = Hoare(a, iL, iH-1, PMi);
        System.out.println("4");
        int ilm = Hoare(a, iL, iM-1, PLo);
        //recursion here on first 2 subarrays on different threads
        System.out.println("5");
        int imh = Hoare(a, iM, iH-1, PHi);
        //recursion here on last 2 subarrays on different threads


        System.out.printf("\n\nPLo: %d, PMi: %d, PHi: %d", PLo, PMi, PHi);
        System.out.printf("\niL: %d, iM: %d, iH: %d", iL, iM, iH);
        System.out.printf("\nilm: %d, imh: %d\n", ilm, imh);
    }

    public static int Hoare(Integer[] a, int b, int c, int P) {
        int i = b+1, j = c-1;
        int temp;
        while (true) {
            while (a[i] < P) {
                if (i == c) break;
                i++;
            }
            while (a[j] > P) {
                if (j == b) break;
                j--;
            }
            if (i > j) {
                return j;
            }
                temp = a[i];
                a[i] = a[j];
                a[j] = temp;


        }
    }

    public static int Sampling(int beg, int end) {
        return rand.nextInt(beg, end-1);
    }

    public static void main(String[] args) {
        Integer[] arr = {
                51, 67, 23, 98, 14, 29, 82, 41, 55, 72,
                39, 88, 63, 17, 25, 31, 49, 92, 76, 10,
                19, 84, 37, 58, 46, 91, 61, 70, 32, 80,
                26, 99, 68, 11, 47, 74, 28, 36, 53, 65,
                43, 75, 22, 97, 18, 64, 34, 50, 87, 13,
                30, 89, 56, 71, 93, 62, 24, 40, 77, 45,
                83, 20, 60, 78, 35, 21, 48, 95, 15, 79,
                69, 42, 66, 27, 96, 57, 12, 38, 94, 59,
                33, 86, 52, 54, 16, 81, 73, 44, 90, 85,
                7, 3, 2, 1, 4, 5, 6, 8, 9, 0
        };
//                                     [1,2,5,6,8,10,23,43,65,76];
//        int temp = arr[6];
//        arr[6] = arr[2];
//        arr[2] = temp;
//        for (int i = 0; i < arr.length-1; i++) {
//            System.out.print(arr[i]+" ");
//        }
//        System.out.println(Hoare(arr, 0, arr.length - 1, 23));

        PTPSort(arr, 0, arr.length-1);


    }
}


