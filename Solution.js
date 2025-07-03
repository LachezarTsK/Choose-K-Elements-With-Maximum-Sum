
// const {PriorityQueue} = require('@datastructures-js/priority-queue');
/*
 PriorityQueue is internally included in the solution file on leetcode.
 When running the code on leetcode it should stay commented out. 
 It is mentioned here just for information about the external library 
 that is applied for this data structure.
 */


/**
 * @param {number[]} valuesToCompare
 * @param {number[]} valuesToSum
 * @param {number} maxNumberOfValuesToSum
 * @return {number[]}
 */
var findMaxSum = function (valuesToCompare, valuesToSum, maxNumberOfValuesToSum) {
    const sortedUnits = createSortedUnits(valuesToCompare, valuesToSum);
    const minHeap = new PriorityQueue((x, y) => x - y);

    let currentMaxSum = 0;
    const maxSum = new Array(sortedUnits.length);
    maxSum[sortedUnits[0].index] = currentMaxSum;

    for (let i = 1; i < sortedUnits.length; ++i) {
        minHeap.enqueue(sortedUnits[i - 1].toSum);
        currentMaxSum += sortedUnits[i - 1].toSum;

        if (minHeap.size() > maxNumberOfValuesToSum) {
            currentMaxSum -= minHeap.dequeue();
        }

        if (sortedUnits[i - 1].toCompare < sortedUnits[i].toCompare) {
            maxSum[sortedUnits[i].index] = currentMaxSum;
            continue;
        }
        maxSum[sortedUnits[i].index] = maxSum[sortedUnits[i - 1].index];
    }

    return maxSum;
};

/**
 * @param {number} index
 * @param {number} toCompare
 * @param {number} toSum
 */
function Unit(index, toCompare, toSum) {
    this.index = index;
    this.toCompare = toCompare;
    this.toSum = toSum;
}

/**
 * @param {number[]} valuesToCompare
 * @param {number[]} valuesToSum
 * @return {Unit[]}
 */
function createSortedUnits(valuesToCompare, valuesToSum) {
    const sortedUnits = new Array(valuesToCompare.length);
    for (let i = 0; i < sortedUnits.length; ++i) {
        sortedUnits[i] = new Unit(i, valuesToCompare[i], valuesToSum[i]);
    }
    sortedUnits.sort((x, y) => x.toCompare - y.toCompare);

    return sortedUnits;
}
