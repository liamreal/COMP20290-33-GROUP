import java.util.Arrays;
import java.util.Random;

public class Main {
    static Random rand = new Random();

    private Main() {}

    public static void PTPSort(Integer[] a, int beg, int end) {
        if (end <= beg + 1) {
            return; // Base case for recursion: return if the section to sort is 1 or 0 elements
        }

        // Selecting three distinct indices for pivots
        int iPLo = 0, iPMi = 0, iPHi = 0;

        // Ensure the pivots are distinct
        while ((iPLo == iPMi) || (iPMi == iPHi) || (iPHi == iPLo)) {
            iPLo = Sampling(beg, end);
            iPMi = Sampling(beg, end);
            iPHi = Sampling(beg, end);
        }

        // we get the pivot values
        int PLo = a[iPLo], PMi = a[iPMi], PHi = a[iPHi];

        // Order pivot values
        if (PMi < PLo) { int tmp = PMi; PMi = PLo; PLo = tmp; }
        if (PHi < PMi) { int tmp = PHi; PHi = PMi; PMi = tmp; }
        if (PMi < PLo) { int tmp = PMi; PMi = PLo; PLo = tmp; }

        System.out.println("Pivots: " + PLo + ", " + PMi + ", " + PHi);

        // Partitioning the array around each pivot
        int iL = Hoare(a, beg, beg-1, PLo);
        int iM = Hoare(a, iL + 1, end, PMi);
        int iH = Hoare(a, iM + 1, end, PHi);

        System.out.println("Partitions at: " + iL + ", " + iM + ", " + iH);

        // Recursively sorting the partitions
        PTPSort(a, beg, iL);
        PTPSort(a, iL + 1, iM);
        PTPSort(a, iM + 1, iH);
        PTPSort(a, iH + 1, end);
    }

    public static int Hoare(Integer[] a, int b, int c, int P) {
        int i = b+1, j = c-1;
        int temp;
        while (true) {
            while (a[i] < P) {
                if (i == c) break;
                i++;
            }
            while (a[j] >= P) {
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
        return rand.nextInt(end - beg) + beg;
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

        PTPSort(arr, 0, arr.length);
        System.out.println(Arrays.toString(arr)); // Print the sorted array
    }
}