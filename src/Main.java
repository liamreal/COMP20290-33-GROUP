public class Main {

    public static void PTPSort(Integer[] a, int beg, int end) {

    }

    public static int Hoare(Integer[] a, int b, int c, int P) {
        int i = b, j = c, temp;
        while (true) {
            while (a[i] < P) {
                i = i+1;
            }
            while (a[j] >= P) {
                j = j-1;
            }
            if (i < j) {
                return j - 1;
            }
            temp = i;
            i = j;
            j = temp;
        }
    }
    public static void main(String[] args) {

    }
}
