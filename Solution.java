
import java.util.Arrays;
import java.util.PriorityQueue;

public class Solution {

    private record Unit(int index, int toCompare, int toSum) {}

    public long[] findMaxSum(int[] valuesToCompare, int[] valuesToSum, int maxNumberOfValuesToSum) {
        Unit[] sortedUnits = createSortedUnits(valuesToCompare, valuesToSum);
        PriorityQueue<Integer> minHeap = new PriorityQueue<>();

        long currentMaxSum = 0;
        long[] maxSum = new long[sortedUnits.length];
        maxSum[sortedUnits[0].index] = currentMaxSum;

        for (int i = 1; i < sortedUnits.length; ++i) {
            minHeap.add(sortedUnits[i - 1].toSum);
            currentMaxSum += sortedUnits[i - 1].toSum;

            if (minHeap.size() > maxNumberOfValuesToSum) {
                currentMaxSum -= minHeap.poll();
            }

            if (sortedUnits[i - 1].toCompare < sortedUnits[i].toCompare) {
                maxSum[sortedUnits[i].index] = currentMaxSum;
                continue;
            }
            maxSum[sortedUnits[i].index] = maxSum[sortedUnits[i - 1].index];
        }

        return maxSum;
    }

    private Unit[] createSortedUnits(int[] valuesToCompare, int[] valuesToSum) {
        Unit[] sortedUnits = new Unit[valuesToCompare.length];
        for (int i = 0; i < sortedUnits.length; ++i) {
            sortedUnits[i] = new Unit(i, valuesToCompare[i], valuesToSum[i]);
        }
        Arrays.sort(sortedUnits, (x, y) -> x.toCompare - y.toCompare);

        return sortedUnits;
    }
}
