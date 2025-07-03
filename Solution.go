
package main
import (
    "container/heap"
    "slices"
)

func findMaxSum(valuesToCompare []int, valuesToSum []int, maxNumberOfValuesToSum int) []int64 {
var sortedUnits []Unit = createSortedUnits(valuesToCompare, valuesToSum)
    minHeap := make(PriorityQueue, 0)

    var currentMaxSum int64 = 0
    maxSum := make([]int64,len(sortedUnits))
    maxSum[sortedUnits[0].index] = currentMaxSum

    for i:=1; i < len(sortedUnits); i++ {
        heap.Push(&minHeap, sortedUnits[i - 1].toSum)
        currentMaxSum += int64(sortedUnits[i - 1].toSum)

        if (len(minHeap) > maxNumberOfValuesToSum) {
            currentMaxSum -= int64(heap.Pop(&minHeap).(int))
        }

        if (sortedUnits[i - 1].toCompare < sortedUnits[i].toCompare) {
            maxSum[sortedUnits[i].index] = currentMaxSum
            continue
        }
        maxSum[sortedUnits[i].index] = maxSum[sortedUnits[i - 1].index]
    }

    return maxSum
}

func createSortedUnits(valuesToCompare []int, valuesToSum []int) []Unit {
    sortedUnits := make([]Unit, len(valuesToCompare))
    for i := range sortedUnits {
        sortedUnits[i] = NewUnit(i, valuesToCompare[i], valuesToSum[i])
    }
    slices.SortFunc(sortedUnits, func(x Unit, y Unit) int { return x.toCompare - y.toCompare })

    return sortedUnits
}

type Unit struct {
    index     int
    toCompare int
    toSum     int
}

func NewUnit(index int, toCompare int, toSum int) Unit {
    unit := Unit{
        index:     index,
        toCompare: toCompare,
        toSum:     toSum,
    }
    return unit
}

type PriorityQueue []int

func (pq PriorityQueue) Len() int {
    return len(pq)
}

func (pq PriorityQueue) Less(first int, second int) bool {
    return pq[first] < pq[second]
}

func (pq PriorityQueue) Swap(first int, second int) {
    pq[first], pq[second] = pq[second], pq[first]
}

func (pq *PriorityQueue) Push(object any) {
    *pq = append(*pq, object.(int))
}

func (pq *PriorityQueue) Pop() any {
    item := (*pq)[(*pq).Len() - 1]
    *pq = (*pq)[0 : (*pq).Len() - 1]
    return item
}
