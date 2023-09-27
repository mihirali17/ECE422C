package project1_SortTools;

import static org.junit.Assert.*;
import org.junit.Test;
import org.junit.Rule;
import org.junit.rules.Timeout;

public class SampleTest {
    @Rule
    public Timeout globalTimeout = Timeout.seconds(2);
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void isSortedT1() {  // Edge Case: when n = 0 (no input to be checked); based on Piazza, result should be false
        int[] x = new int[5];
        int[] original = x.clone();

        assertEquals(SortTools.isSorted(x, 0), false);
        assertArrayEquals(original, x);
    }

    @Test
    public void isSortedT2() {  // Case 1: unsorted array, but first n elems are sorted; result should be true
        int[] x = {1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 10, 12, 13, 14, 15, 16};
        int[] original = x.clone();

        assertEquals(SortTools.isSorted(x, 9), true);
        assertArrayEquals(original, x);
    }

    @Test
    public void isSortedT3() {  // Case 2: unsorted array, within first n elems; result should be false
        int[] x = {1, 2, 3, 4, 5, 6, 7, 8, 9, 11, 10, 12, 13, 14, 15, 16};
        int[] original = x.clone();

        assertEquals(SortTools.isSorted(x, 13), false);
        assertArrayEquals(original, x);
    }

    @Test
    public void isSortedT4() {  // Case 3: sorted array, but only checks first n elems; result should be true
        int[] x = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        int[] original = x.clone();

        assertEquals(SortTools.isSorted(x, 10), true);
        assertArrayEquals(original, x);
    }

    @Test
    public void isSortedT5() {  // Case 4: sorted array, n is all elems; result should be true
        int[] x = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
        int[] original = x.clone();

        assertEquals(SortTools.isSorted(x, x.length), true);
        assertArrayEquals(original, x);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void findT1() {  // Edge Case: n = 0; should return -1
        int[] x = new int[5];
        int[] original = x.clone();

        assertEquals(-1, SortTools.find(x, 0, 9));
        assertArrayEquals(original, x);
    }

    @Test
    public void findT2() {  // Edge Case: n = 1; should find 1 at i=0
        int[] x = {1, 2, 3};
        int[] original = x.clone();

        assertEquals(0, SortTools.find(x, 1, 1));
        assertArrayEquals(original, x);
    }

    @Test
    public void findT3() {  // Case 1: v is within n, n = length of array; should find -3 at i=2
        int[] x = {-5, -4, -3, -2, -1, 0};
        int[] original = x.clone();

        assertEquals(2, SortTools.find(x, x.length, -3));
        assertArrayEquals(original, x);
    }

    @Test
    public void findT4() {  // Case 2: v is within the array but not within n, n < length of array; should not find -3 and return -1
        int[] x = {-5, -4, -3, -2, -1, 0};
        int[] original = x.clone();

        assertEquals(-1, SortTools.find(x, 1, -3));
        assertArrayEquals(original, x);
    }

    @Test
    public void findT5() {  // Case 3: v is not within the array, n = length of array; should not find 7 and return -1
        int[] x = {-5, -4, -3, -2, -1, 0};
        int[] original = x.clone();

        assertEquals(-1, SortTools.find(x, x.length, 7));
        assertArrayEquals(original, x);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void copyAndInsertT1() { // Edge Case: n = 0, so no elems are copied from x; only v is in new array
        int[] x = {1, 2, 3, 4, 5};
        int[] original = x.clone();
        int[] y = SortTools.copyAndInsert(x, 0, 17);
        int[] yExpect = {17};

        assertArrayEquals(y, yExpect);
        assertArrayEquals(original, x);
    }

    @Test
    public void copyAndInsertT2() { // Edge Case: n = 1, v is within n
        int[] x = {1, 2, 3};
        int[] original = x.clone();
        int[] y = SortTools.copyAndInsert(x, 1, 1);
        int[] yExpect = {1};

        assertArrayEquals(y, yExpect);
        assertArrayEquals(original, x);
    }

    @Test
    public void copyAndInsertT3() { // Edge Case: n = 1, v must be inserted
        int[] x = {1, 2, 3, 5, 7};
        int[] original = x.clone();
        int[] y = SortTools.copyAndInsert(x, 1, 2);
        int[] yExpect = {1, 2};

        assertArrayEquals(y, yExpect);
        assertArrayEquals(original, x);
    }

    @Test
    public void copyAndInsertT4() { // Case 1: n < length of array, v must be inserted
        int[] x = {1, 2, 3, 4, 5, 6, 8, 9, 10, 11};
        int[] original = x.clone();
        int[] y = SortTools.copyAndInsert(x, 7, 7);
        int[] yExpect = {1, 2, 3, 4, 5, 6, 7, 8};

        assertArrayEquals(y, yExpect);
        assertArrayEquals(original, x);
    }

    @Test
    public void copyAndInsertT5() { // Case 2: n = length of array, v must be inserted
        int[] x = {2, 3, 4, 5, 7, 9, 13, 17, 68, 77};
        int[] original = x.clone();
        int[] y = SortTools.copyAndInsert(x, x.length, 11);
        int[] yExpect = {2, 3, 4, 5, 7, 9, 11, 13, 17, 68, 77};

        assertArrayEquals(y, yExpect);
        assertArrayEquals(original, x);
    }

    @Test
    public void copyAndInsertT6() { // Case 3: n < length of array, v is within n
        int[] x = {1, 2, 3, 4, 5, 6, 7};
        int[] original = x.clone();
        int[] y = SortTools.copyAndInsert(x, 5, 5);
        int[] yExpect = {1, 2, 3, 4, 5};

        assertArrayEquals(y, yExpect);
        assertArrayEquals(original, x);
    }

    @Test
    public void copyAndInsertT7() { // Case 4: n = length of array, v is within n
        int[] x = {1, 2, 3, 4, 5, 6, 7};
        int[] original = x.clone();
        int[] y = SortTools.copyAndInsert(x, x.length, 5);
        int[] yExpect = {1, 2, 3, 4, 5, 6, 7};

        assertArrayEquals(y, yExpect);
        assertArrayEquals(original, x);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void insertInPlaceT1() { // Case 1a: elem must be inserted
        int[] x = {0, 2, 3, 5};
        int[] xExpect = {0, 1, 2, 3};
        int result = SortTools.insertInPlace(x, 3, 1);

        assertEquals(result, 4);
        assertArrayEquals(x, xExpect);
    }

    @Test
    public void insertInPlaceT2() { // Case 1b: elem must be inserted
        int[] x = {0, 2, 3, 5, 7, 9, 11, 14, 17};
        int[] xExpect = {0, 2, 3, 5, 7, 9, 11, 12, 17};
        int result = SortTools.insertInPlace(x, 7, 12);

        assertEquals(result, 8);
        assertArrayEquals(x, xExpect);
    }

    @Test
    public void insertInPlaceT3() { // Case 1c: elem must be inserted
        int[] x = {0, 2, 3, 5, 7, 9, 11, 14, 17, 19, 22};
        int[] xExpect = {0, 2, 3, 5, 6, 7, 11, 14, 17, 19, 22};
        int result = SortTools.insertInPlace(x, 5, 6);

        assertEquals(result, 6);
        assertArrayEquals(x, xExpect);
    }

    @Test
    public void insertInPlaceT4() { // Case 2: elem is already in array, no insertion
        int[] x = {1, 2, 3, 4, 5, 6};
        int[] xExpect = {1, 2, 3, 4, 5, 6};
        int result = SortTools.insertInPlace(x, 5, 4);

        assertEquals(result, 5);
        assertArrayEquals(x, xExpect);
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Test
    public void insertSortT1() {    // Edge Case: unsorted array, n = 1 or 0; should not sort array at all
        int[] x = {1, 3, 2, -6, 5};
        int[] xExpect = {1, 3, 2, -6, 5};
        SortTools.insertSort(x, 1);

        assertArrayEquals(x, xExpect);
    }

    @Test
    public void insertSortT2() {    // Case 1: unsorted array, n = length of array; should sort whole array
        int[] x = {45, 34, 65 ,76, 33, 2, 8};
        int[] xExpect = {2, 8, 33, 34, 45, 65, 76};
        SortTools.insertSort(x, x.length);

        assertArrayEquals(x, xExpect);
    }

    @Test
    public void insertSortT3() {    // Case 2: unsorted array, n < length of array; should only sort first 9 elements
        int[] x = {1, 3, 2, 6, 5, 4, 9, 7, 11, 10, 8, 12, 16, 14, 13, 15};
        int[] xExpect = {1, 2, 3, 4, 5, 6, 7, 9, 11, 10, 8, 12, 16, 14, 13, 15};
        SortTools.insertSort(x, 9);

        assertArrayEquals(x, xExpect);
    }
}
