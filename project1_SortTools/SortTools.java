package project1_SortTools;

public class SortTools {
    /**
      * Return whether the first n elements of x are sorted in non-decreasing
      * order.
      * @param x is the array
      * @param n is the size of the input to be checked
      * @return true if array is sorted
      */
    public static boolean isSorted(int[] x, int n) {
        /* Complexity = O(n)
           Assume 0 <= n <= x.length
           Assume x != null */

        if (n == 0) // edge case from Piazza: if n = 0 (no input), return false
            return false;
        for (int i = 0; i < (n-1); i++) {
            if (x[i] > x[i+1])  // if the next element < current, the array is not sorted in non-decreasing order
                return false;
        }
        return true;
    }

    /**
     * Return an index of value v within the first n elements of x.
     * @param x is the array
     * @param n is the size of the input to be checked
     * @param v is the value to be searched for
     * @return any index k such that k < n and x[k] == v, or -1 if no such k exists
     */
    public static int find(int[] x, int n, int v) {
        /* Complexity = O(log(n))
           Assume the array is sorted through n
           Assume 0 <= n <= x.length
           Assume x != null */

        // BINARY SEARCH WITH O(logn)
        int lower = 0, upper = n-1;
        if (n == 0) // edge case
            return -1;
        if (n == 1) // edge case
            if (x[0] == v)
                return 0;
            else
                return -1;
        while (lower <= upper) {    // while lower bound of search portion of array does not transgress the upper bound..
            int mid = lower + (upper - lower) / 2;  // mid always equals the inbetween point of lower and upper
            if (x[mid] == v)    // we have found v
                return mid;
            if (x[mid] < v) // the current mid element is less than v, so we need to push our lower bound up beyond it
                lower = mid + 1;
            else    // the current mid element is greater than v, so we need to reduce our upper bound below it
                upper = mid - 1;
        }
        return -1;
    }

    /**
     * Return a sorted, newly created array containing the first n elements of x
     * and ensuring that v is in the new array.
     * @param x is the array
     * @param n is the number of elements to be copied from x
     * @param v is the value to be added to the new array if necessary
     * @return a new array containing the first n elements of x as well as v
     */
    public static int[] copyAndInsert(int[] x, int n, int v) {
        /* Complexity = O(n)
           Assume the array is sorted through n
           Assume x.length > 0
           Assume 0 <= n <= x.length
           Assume x != null */

        int[] y = new int[n];
        for (int i = 0; i < y.length; i++)    // we need to copy over the first n elems of x into a new array, so populate y with those
            y[i] = x[i];
        if (find(y, n, v) != -1)   // if v is in the array..
            return y;
        if (n == 0) {   // edge case: if there are no elems in y, simply add v to a new empty array
            y = new int[1];
            y[0] = v;
            return y;
        }

        int pos = 0;    // by default, insert position is 0 (this is where v will be placed if it is smaller than all elements in the array)
        if (v > y[n-1]) { // if v is greater than the biggest element..
            pos = n;    // insert position is at the end of the array
        }
        else {
            for (int i = 0; i < n-1; i++) {
                if (v > y[i] && v < y[i+1])   // if we have found the two elements that v can be inserted between..
                    pos = i + 1;  // insert position becomes the index of the upper element
            }
        }
        int[] newY = new int[n+1];
        for (int i = 0; i < newY.length; i++) { // populate the new array..
            if (i < pos)
                newY[i] = y[i]; // if we are at i < position to insert v, new array elem = corresponding old array elem
            else if (i == pos)
                newY[i] = v;    // if we are at i = position to insert v, new array elem = v
            else
                newY[i] = y[i-1];   // we are past the i's below position of v and v itself; since new array size is 1 bigger, new array elem = corresponding old array elem pos - 1
        }
        y = new int[n+1];
        for (int i = 0; i < newY.length; i++)
            y[i] = newY[i];
        return y;   // at this point, y should contain the first n elems of x, as well as v, sorted into them
    }

    /**
     * Insert the value v in the first n elements of x if it is not already
     * there, ensuring those elements are still sorted.
     * @param x is the array
     * @param n is the number of elements in the array
     * @param v is the value to be added
     * @return n if v is already in x, otherwise returns n+1
     */
    public static int insertInPlace(int[] x, int n, int v) {
        /* Complexity: O(n)
           Assume the array is sorted through n
           Assume 0 < n < x.length
           Assume x != null
         */

        if (find(x, n, v) != -1)    // if v is already in the array..
            return n;

        int pos = 0;    // by default, insert position is 0 (this is where v will be placed if it is smaller than all elements in the array)
        if (v > x[n-1]) { // if v is greater than the biggest element..
            pos = n;    // insert position is at the end of the array
        }
        else {
            for (int i = 0; i < n-1; i++) {
                if (v > x[i] && v < x[i+1]) {   // if we have found the two elements that v can be inserted between..
                    pos = i + 1;  // insert position becomes the index of the upper element
                    break;
                }
            }
        }
        int[] xCopy = new int[x.length];   // make a copy of x
        for (int i = 0; i < x.length; i++)
            xCopy[i] = x[i];
        for (int i = 0; i < n+1; i++) { // insert v into the array..
            if (i < pos)
                x[i] = x[i]; // if we are at i < position to insert v, elem stays the same
            else if (i == pos)
                x[i] = v;    // if we are at i = position to insert v, new array elem = v
            else
                x[i] = xCopy[i-1];   // we are past the i's below position of v and past v itself; since new array size is 1 bigger, new array elem = old array elem position - 1
        }
        return n+1;
    }

    /**
     * Sort the first n elements of x in-place in non-decreasing order using
     * insertion sort.
     * @param x is the array to be sorted
     * @param n is the number of elements of the array to be sorted
     */
    public static void insertSort(int[] x, int n) {
        /* Complexity: O(n^2)
           General case of O(n^2), end case of O(n)
           Assume 0 < x.length
           Assume 0 <= n <= x.length
           Assume x != null
         */

        for (int i = 1; i < n; i++) {
            int key = x[i]; // the current elem in array is key
            int j = i - 1;  // index for the sorted portion of the array (ex: 0 1 2 | 5 3 4 7 6 -> in this case, 0 1 2 is the sorted portion, with corresponding indices 0, 1, 2 for j)
            while (j >= 0 && x[j] > key) {  // while we haven't looked through the whole sorted portion to insert key, and the current elem in sorted portion > key..
                x[j+1] = x[j]; // swap current element with element prior to it
                j -= 1; // progress to the next element in the sorted portion (leftwards, or downwards)
            }
            x[j+1] = key;   // wherever j ends up after sorting through the sorted portion, j+1 is the position for key to be inserted into
        }
    }
}
