package com.example.blijchislo;

import org.junit.Assert;
import org.junit.Test;

public class ClosestNumberFinderTest {
    @Test
    public void findClosestReturnsExactMatch() {
        ClosestNumberFinder finder = new ClosestNumberFinder(new int[]{7, 42, 100});

        Assert.assertEquals(42, finder.findClosest(42));
    }

    @Test
    public void findClosestReturnsNearestValue() {
        ClosestNumberFinder finder = new ClosestNumberFinder(new int[]{10, 30, 90});

        Assert.assertEquals(30, finder.findClosest(36));
    }

    @Test
    public void findClosestKeepsOriginalOrderForEqualDistance() {
        ClosestNumberFinder finder = new ClosestNumberFinder(new int[]{21, 15});

        Assert.assertEquals(21, finder.findClosest(18));
    }

    @Test
    public void findClosestUsesEarliestDuplicateForTieDecision() {
        ClosestNumberFinder finder = new ClosestNumberFinder(new int[]{9, 11, 9});

        Assert.assertEquals(9, finder.findClosest(10));
    }
}
