package search.util;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import search.interfaces.Node;

public class Util {
    public static int[][] deepCopy(int[][] matrix) {
        return java.util.Arrays.stream(matrix).map(el -> el.clone()).toArray($ -> matrix.clone());
    }

    public static void sortedInsert(List<Node> list, Node element) {
        int index = Collections.binarySearch(list, element, Comparator.comparing(Node::getEur));
        if (index < 0) {
            index = -index - 1;
        }
        list.add(index, element);
    }

    public static void main(String[] args) {
        int[][] m = {{1,2,3},{4,5,6}};

        int[][] mm = Util.deepCopy(m);

        m[0][0] = -1;

        System.out.println(Arrays.toString(m[0]));
    }
    
}
