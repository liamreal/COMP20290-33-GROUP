import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.stream.IntStream;



public class Main {


    static boolean isSorted(Integer[] array) {
        for (int i = 0; i < array.length - 1; i++) {
            if (array[i] > array[i + 1])
                return false;
        }
        return true;
    }


    public static class MyThreadRunner implements Runnable {

        public Integer[] array;
        public int beg;
        public int iX;

        // constructor assigns variables to this thread (because cannot be pass as args in run() )
        MyThreadRunner(Integer[] a, int b, int i) {
            array = a;
            beg = b;
            iX = i;
        }

        // run PTPSort on this new thread using local variables
        public void run() {
            if (iX > beg) {
                Main.PTPSort(array, beg, iX);
            }
        }
    }

    static int seed = 0;
    static Random rand = new Random();

    private Main() {}

    public static void PTPSort(Integer[] a, int beg, int end) {
        if (end <= beg) {
            return; // Base case for recursion: return if the section to sort is 1 or 0 elements
        }

//        // switch to STLSort when under cutoff point
//        if ((end - beg) < Ustl) {
////            Arrays.sort();
//            return;}

        if (a.length < 7) return;

        // Selecting three distinct indices for pivots
        int iPLo = 0, iPMi = 0, iPHi = 0;


        iPLo = Sampling(beg, end);
        iPMi = Sampling(beg, end);
        iPHi = Sampling(beg, end);


        // we get the pivot values
        int PLo = a[iPLo], PMi = a[iPMi], PHi = a[iPHi];

        // Order pivot values
        if (PMi < PLo) { int tmp = PMi; PMi = PLo; PLo = tmp; }
        if (PHi < PMi) { int tmp = PHi; PHi = PMi; PMi = tmp; }
        if (PMi < PLo) { int tmp = PMi; PMi = PLo; PLo = tmp; }

//        System.out.println("Pivots: " + PLo + ", " + PMi + ", " + PHi);

        // Partitioning the array around each pivot
        int iL = Hoare(a, beg, end, PLo);
        int iM = Hoare(a, iL + 1, end, PMi);
        int iH = Hoare(a, iM + 1, end, PHi);

//        System.out.println("Partitions at: " + iL + ", " + iM + ", " + iH);

//        System.out.println("Array after partitioning: " + Arrays.toString(a));

        // Recursively sorting the partitions

//        // these provide additional checks so that there is no unnecessary sorts
//        StartingThreads.CustomRunnable.run(a, beg, iL);
//        StartingThreads.CustomRunnable.run(a, iL + 1, iM);
//        StartingThreads.CustomRunnable.run(a, iM + 1, iH);
//        StartingThreads.CustomRunnable.run(a, iH + 1, end);

        // these provide additional checks so that there is no unnecessary sorts
        MyThreadRunner thread_1 = new MyThreadRunner(a, beg, iL);
        thread_1.run();
        MyThreadRunner thread_2 = new MyThreadRunner(a, iL + 1, iM);
        thread_2.run();
        MyThreadRunner thread_3 = new MyThreadRunner(a, iM + 1, iH);
        thread_3.run();
        MyThreadRunner thread_4 = new MyThreadRunner(a, iH + 1, end);
        thread_4.run();

//        PTPSort(a, beg, iL);
//        PTPSort(a, iL + 1, iM);
//        PTPSort(a, iM + 1, iH);
//        PTPSort(a, iH + 1, end);
    }

    public static int Hoare(Integer[] a, int b, int c, int P) {
        int i = b-1, j = c+1;
        int temp;
        while (true) {

            do { // to make sure i does not go out of bounds
                i++;
                if (i >= a.length) i--; // extra check for out of bounds
            } while (a[i] < P);
            do {
                j--;
            }while (a[j] > P);
            if (i >= j) {
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
                7, 3, 2, 1, 4, 5, 6, 8, 9, 0,
        };

        // generate 100 random number between 0 to n
        int n = 100000;
        Integer[] array = Arrays.stream( new Random().ints(n, 0, n).toArray() ).boxed().toArray( Integer[]::new );


         // check if arr sorted
        PTPSort(array, 0, array.length-1);
        System.out.println(Arrays.toString(array)); // Print the sorted array

        // JUST for checking if is sorted, not efficient but gets job done
        if (isSorted(array)) {
            System.out.println("\nSorted");
        } else {
            System.out.println("\nNOT sorted");
        }


        System.out.println("\n");
        computeResults();



    }

    private static void computeResults() {
        double[] Ustl_cutoff = {2.0, 1.0, 0.5, 0.2, 0.1}; // cutoffs for normal sort (in millions, by * by M)
        int trials = 100; // number of times we run a test (so each time you see is averaged over this many tests)

        HashMap<Character, Integer> multiples = new HashMap<>();
        multiples.put('-', 1); // 1
        multiples.put('K', 1000); // 1 thousand
        multiples.put('M', 1000000); // 1 million
        Character multiple_index = 'K'; // HERE we choose what multiple of n to use, e.g. M = million, K = thousand, etc.

        int n = 0;

        printLine();
        System.out.println();
        System.out.printf(" %-14s| %-14s| %-14s %-14s| %-14s %-14s", "N", "Ustl", "Tptp", "", "Torg", "");
        System.out.println();
        System.out.printf(" %-14s| %-14s|  %-13s  %-13s|  %-13s  %-13s", "", "", "sort", "part", "sort", "part");
        printLine();
        printLine();

        // 100M - 500M
        for (int i = 0; i < 5; i++) {
            // this is number of elements and their multiples, some of this stuff is JUST for printing in table,
            // but n below goes 100, 200, 300, 400, 500, then letter is the multiple in hashtable, SO
            // 100 * K = 100 thousand, 100 * M = 100 million, as specified previously (higher than K is memory intensive)
            n += 100;
            String string_n = String.valueOf(n);
            Character letter = multiple_index;

            for (int j = 0; j < 5; j++) {

                // times for sort (NOTE: we do n * multiple, so 100 * M = 100M = 100 million elements)
                double ptp_sort = timedPTP(n * multiples.get(multiple_index), trials);
                double ptp_part = 0.0;
                double org_sort = QuickSortMultiThreading.timedQuickSort(n *multiples.get(multiple_index), trials);
                double org_part = 0.0;

                // this is JUST so we only print the very left column numbers only once, e.g.
                //      100K
                //
                //  --------
                //      200K
                //
                //  --------
                //  etc.
                if (j > 0) {
                    string_n = "";
                    letter = ' ';
                }
                System.out.println();
                System.out.printf(" %-14s| %-14s| %-14s %-14s| %-14s %-14s", string_n + letter, String.valueOf(Ustl_cutoff[j]) + multiple_index, ptp_sort, ptp_part, org_sort, org_part);
            }
            printLine();
        }

    }

    // returns time (in seconds) of how long took to run code, takes in how many trials to run
    private static double timedPTP(int n, int trials) {
        double total_time = 0;
        int j = 0;

        while (j < trials) {
            // generate array of n random numbers between 0 to n
            Integer[] array = Arrays.stream( new Random().ints(n, 0, n).toArray() ).boxed().toArray( Integer[]::new );


            // start and end time of program using System.nanoTime, we are only interested in the sorting, which is why it
            // does not start before line above, where it creates the array
            double start_time = System.nanoTime();
            PTPSort(array, 0, array.length - 1);
            double end_time = System.nanoTime();
            // end of sorting test for specified number of trials

            total_time += (end_time - start_time);
            j++;
        }


        // return time (converted from nanoseconds to seconds AND dividing number of trials to get average)
        return (total_time / trials) / 1_000_000_000;
    }

    // divider for new table segments
    private static void printLine() {
        System.out.println();
        for (int i = 0; i < 93; i++) {
            System.out.printf("-");
        }
    }


















}