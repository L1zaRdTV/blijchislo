package com.example.blijchislo;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ClosestNumberFinder {
    private final List<NumberEntry> sortedEntries;

    public ClosestNumberFinder(int[] numbers) {
        sortedEntries = createSortedUniqueEntries(numbers);
    }

    public int findClosest(int target) {
        if (sortedEntries.isEmpty()) {
            throw new IllegalStateException("Array must contain at least one number");
        }

        int insertionIndex = findInsertionIndex(target);
        NumberEntry bestEntry = null;

        if (insertionIndex < sortedEntries.size()) {
            bestEntry = chooseBetterEntry(bestEntry, sortedEntries.get(insertionIndex), target);
        }

        int previousIndex = insertionIndex - 1;
        if (previousIndex >= 0) {
            bestEntry = chooseBetterEntry(bestEntry, sortedEntries.get(previousIndex), target);
        }

        return bestEntry.value;
    }

    private List<NumberEntry> createSortedUniqueEntries(int[] numbers) {
        Map<Integer, NumberEntry> earliestEntriesByValue = new LinkedHashMap<>();
        for (int index = 0; index < numbers.length; index++) {
            int value = numbers[index];
            if (!earliestEntriesByValue.containsKey(value)) {
                earliestEntriesByValue.put(value, new NumberEntry(value, index));
            }
        }

        List<NumberEntry> entries = new ArrayList<>(earliestEntriesByValue.values());
        entries.sort(Comparator.comparingInt(entry -> entry.value));
        return entries;
    }

    private int findInsertionIndex(int target) {
        int left = 0;
        int right = sortedEntries.size();

        while (left < right) {
            int middle = left + (right - left) / 2;
            if (sortedEntries.get(middle).value < target) {
                left = middle + 1;
            } else {
                right = middle;
            }
        }

        return left;
    }

    private NumberEntry chooseBetterEntry(NumberEntry currentBest, NumberEntry candidate, int target) {
        if (currentBest == null) {
            return candidate;
        }

        int currentDistance = Math.abs(currentBest.value - target);
        int candidateDistance = Math.abs(candidate.value - target);

        if (candidateDistance < currentDistance) {
            return candidate;
        }

        if (candidateDistance == currentDistance && candidate.originalIndex < currentBest.originalIndex) {
            return candidate;
        }

        return currentBest;
    }

    private static final class NumberEntry {
        private final int value;
        private final int originalIndex;

        private NumberEntry(int value, int originalIndex) {
            this.value = value;
            this.originalIndex = originalIndex;
        }
    }
}
