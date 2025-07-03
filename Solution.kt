
import java.util.*

class Solution {

    private data class Unit(var index: Int, var toCompare: Int, var toSum: Int) {}

    fun findMaxSum(valuesToCompare: IntArray, valuesToSum: IntArray, maxNumberOfValuesToSum: Int): LongArray {
        val sortedUnits: Array<Unit> = createSortedUnits(valuesToCompare, valuesToSum)
        val minHeap = PriorityQueue<Int>()

        var currentMaxSum: Long = 0
        val maxSum = LongArray(sortedUnits.size)
        maxSum[sortedUnits[0].index] = currentMaxSum

        for (i in 1..<sortedUnits.size) {
            minHeap.add(sortedUnits[i - 1].toSum)
            currentMaxSum += sortedUnits[i - 1].toSum

            if (minHeap.size > maxNumberOfValuesToSum) {
                currentMaxSum -= minHeap.poll()
            }

            if (sortedUnits[i - 1].toCompare < sortedUnits[i].toCompare) {
                maxSum[sortedUnits[i].index] = currentMaxSum
                continue
            }
            maxSum[sortedUnits[i].index] = maxSum[sortedUnits[i - 1].index]
        }

        return maxSum
    }

    private fun createSortedUnits(valuesToCompare: IntArray, valuesToSum: IntArray): Array<Unit> {
        val sortedUnits = Array<Unit>(valuesToCompare.size) { Unit(0, 0, 0) }
        for (i in sortedUnits.indices) {
            sortedUnits[i].index = i
            sortedUnits[i].toCompare = valuesToCompare[i]
            sortedUnits[i].toSum = valuesToSum[i]
        }
        sortedUnits.sortWith() { x, y -> x.toCompare - y.toCompare }

        return sortedUnits
    }
}
