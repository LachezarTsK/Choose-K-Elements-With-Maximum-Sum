
using System;
using System.Collections.Generic;

public class Solution
{
    private record Unit(int index, int toCompare, int toSum) { }

    public long[] FindMaxSum(int[] valuesToCompare, int[] valuesToSum, int maxNumberOfValuesToSum)
    {
        Unit[] sortedUnits = CreateSortedUnits(valuesToCompare, valuesToSum);
        PriorityQueue<int, int> minHeap = new PriorityQueue<int, int>();

        long currentMaxSum = 0;
        long[] maxSum = new long[sortedUnits.Length];
        maxSum[sortedUnits[0].index] = currentMaxSum;

        for (int i = 1; i < sortedUnits.Length; ++i)
        {
            minHeap.Enqueue(sortedUnits[i - 1].toSum, sortedUnits[i - 1].toSum);
            currentMaxSum += sortedUnits[i - 1].toSum;

            if (minHeap.Count > maxNumberOfValuesToSum)
            {
                currentMaxSum -= minHeap.Dequeue();
            }

            if (sortedUnits[i - 1].toCompare < sortedUnits[i].toCompare)
            {
                maxSum[sortedUnits[i].index] = currentMaxSum;
                continue;
            }
            maxSum[sortedUnits[i].index] = maxSum[sortedUnits[i - 1].index];
        }

        return maxSum;
    }

    private Unit[] CreateSortedUnits(int[] valuesToCompare, int[] valuesToSum)
    {
        Unit[] sortedUnits = new Unit[valuesToCompare.Length];
        for (int i = 0; i < sortedUnits.Length; ++i)
        {
            sortedUnits[i] = new Unit(i, valuesToCompare[i], valuesToSum[i]);
        }
        Array.Sort(sortedUnits, (x, y) => x.toCompare - y.toCompare);

        return sortedUnits;
    }
}
