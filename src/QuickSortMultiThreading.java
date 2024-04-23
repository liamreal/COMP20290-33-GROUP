// --------------------------------------------

// QUICKSORT FOR COMPARISON
// SOURCE: https://www.geeksforgeeks.org/quick-sort-using-multi-threading/

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class QuickSortMultiThreading
        extends RecursiveTask<Integer> {

    int start, end;
    Integer[] arr;

    /**
     * Finding random pivoted and partition
     * array on a pivot.
     * There are many different
     * partitioning algorithms.
     *
     * @param start
     * @param end
     * @param arr
     * @return
     */
    private int partition(int start, int end,
                          Integer[] arr) {

        int i = start, j = end;

        // Decide random pivot
        int pivoted = new Random()
                .nextInt(j - i)
                + i;

        // Swap the pivoted with end
        // element of array;
        int t = arr[j];
        arr[j] = arr[pivoted];
        arr[pivoted] = t;
        j--;

        // Start partitioning
        while (i <= j) {

            if (arr[i] <= arr[end]) {
                i++;
                continue;
            }

            if (arr[j] >= arr[end]) {
                j--;
                continue;
            }

            t = arr[j];
            arr[j] = arr[i];
            arr[i] = t;
            j--;
            i++;
        }

        // Swap pivoted to its
        // correct position
        t = arr[j + 1];
        arr[j + 1] = arr[end];
        arr[end] = t;
        return j + 1;
    }

    // Function to implement
    // QuickSort method
    public QuickSortMultiThreading(int start,
                                   int end,
                                   Integer[] arr) {
        this.arr = arr;
        this.start = start;
        this.end = end;
    }

    @Override
    protected Integer compute() {
        // Base case
        if (start >= end)
            return null;

        // Find partition
        int p = partition(start, end, arr);

        // Divide array
        QuickSortMultiThreading left
                = new QuickSortMultiThreading(start,
                p - 1,
                arr);

        QuickSortMultiThreading right
                = new QuickSortMultiThreading(p + 1,
                end,
                arr);

        // Left subproblem as separate thread
        left.fork();
        right.compute();

        // Wait until left thread complete
        left.join();

        // We don't want anything as return
        return null;
    }

    // Driver Code
    public static double timedQuickSort(int n, int trials) {
        double total_time = 0;
        int j = 0;

        // Forkjoin ThreadPool to keep
        // thread creation as per resources
        ForkJoinPool pool = ForkJoinPool.commonPool();

        for (int i = 0; i < trials; i++) {
        }

        while (j < trials) {
            // generate array of n random numbers between 0 to n
            Integer[] array = Arrays.stream( new Random().ints(n, 0, n).toArray() ).boxed().toArray( Integer[]::new );


            // start and end time of program using System.nanoTime, we are only interested in the sorting, which is why it
            // does not start before line above, where it creates the array
            double start_time = System.nanoTime();
            // Start the first thread in fork
            // join pool for range 0, n-1
            pool.invoke(new QuickSortMultiThreading(0, array.length - 1, array));
            double end_time = System.nanoTime();
            // end of sorting test for specified number of trials

            total_time += (end_time - start_time);
            j++;
        }


        // return time (converted from nanoseconds to seconds AND dividing number of trials to get average)
        return (total_time / trials) / 1_000_000_000;
    }
}

// --------------------------------------------
