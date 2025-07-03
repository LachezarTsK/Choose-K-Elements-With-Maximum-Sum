
#include <span>
#include <queue>
#include <ranges> 
#include <vector>
using namespace std;

class Solution {

    struct Unit {

        int index{};
        int toCompare{};
        int toSum{};

        Unit() = default;
        Unit(int index, int toCompare, int toSum) :index{ index }, toCompare{ toCompare }, toSum{ toSum } {}
    };

public:
    vector<long long> findMaxSum(const vector<int>& valuesToCompare, const vector<int>& valuesToSum, int maxNumberOfValuesToSum) const {
        vector<Unit> sortedUnits = createSortedUnits(valuesToCompare, valuesToSum);
        priority_queue<int, vector<int>, greater<int>> minHeap;

        long long currentMaxSum = 0;
        vector<long long> maxSum(sortedUnits.size());
        maxSum[sortedUnits[0].index] = currentMaxSum;

        for (int i = 1; i < sortedUnits.size(); ++i) {
            minHeap.push(sortedUnits[i - 1].toSum);
            currentMaxSum += sortedUnits[i - 1].toSum;

            if (minHeap.size() > maxNumberOfValuesToSum) {
                currentMaxSum -= minHeap.top();
                minHeap.pop();
            }

            if (sortedUnits[i - 1].toCompare < sortedUnits[i].toCompare) {
                maxSum[sortedUnits[i].index] = currentMaxSum;
                continue;
            }
            maxSum[sortedUnits[i].index] = maxSum[sortedUnits[i - 1].index];
        }

        return maxSum;
    }

private:
    vector<Unit> createSortedUnits(span<const int> valuesToCompare, span<const int> valuesToSum) const {
        vector<Unit> sortedUnits(valuesToCompare.size());
        for (int i = 0; i < sortedUnits.size(); ++i) {
            sortedUnits[i] = Unit(i, valuesToCompare[i], valuesToSum[i]);
        }
        ranges::sort(sortedUnits, [](const Unit& x, const Unit& y) {return x.toCompare < y.toCompare; });

        return sortedUnits;
    }
};
