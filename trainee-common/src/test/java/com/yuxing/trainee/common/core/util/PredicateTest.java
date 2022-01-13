package com.yuxing.trainee.common.util;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

/**
 * @author yuxing
 * @since 2022/1/4
 */
public class PredicateTest {

    @Test
    public void test() {

        HashSet<String> objects = new HashSet<>();
        objects.add("1");
        objects.add("2");
        objects.add("3");
        objects.add("4");

        HashSet<String> objects1 = new HashSet<>();
        objects1.add("1");

        boolean test = new Predicate<Set<String>>() {

            private Set<String> set;

            @Override
            public boolean test(Set<String> target) {
                return this.set.containsAll(target);
            }

            Predicate<Set<String>> fillSet(Set<String> set) {
                this.set = set;
                return this;
            }
        }.fillSet(objects).test(objects1);

        Assert.assertTrue(test);
    }
}
