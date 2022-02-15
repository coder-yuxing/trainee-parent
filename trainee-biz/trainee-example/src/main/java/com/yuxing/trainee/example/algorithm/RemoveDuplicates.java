package com.yuxing.trainee.example.algorithm;


/**
 * @author yuxing
 * @since 2022/2/15
 */
public class RemoveDuplicates {

    public static void main(String[] args) {
        int[] nums = {1, 1, 1, 2, 2, 3, 4, 4};
        int[] nums1 = {1, 2, 3, 3, 3, 4, 5, 5, 6, 6, 6, 6, 7};
        System.err.println(removeDuplicates(nums));
        System.err.println(removeDuplicates(nums1));
    }

    /**
     * 给你一个有序数组 nums ，请你 原地 删除重复出现的元素，使每个元素 最多出现两次 ，返回删除后数组的新长度。
     *
     * 不要使用额外的数组空间，你必须在 原地 修改输入数组 并在使用 O(1) 额外空间的条件下完成。
     *
     * @param nums 有序数组
     * @return 删除后数组新长度
     */
    public static int removeDuplicates(int[] nums) {
        int length = nums.length;
        // 每个元素最多出现两次，数组长度小于等于2必然满足要求
        if (length <= 2) {
            return length;
        }
        // 当前待检查元素的数组下标
        int fast = 2;
        // 上一个已检查元素的数组下标
        int slow = 2;
        while (fast < length) {
            if (nums[slow - 2] != nums[fast]) {
                nums[slow] = nums[fast];
                ++slow;
            }
            ++fast;
        }
        return slow;

    }

}
