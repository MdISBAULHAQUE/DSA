class Solution {
    public int minimumDeletions(int[] nums) {
        int n=nums.length;
        int min=0,max=0;

        for(int i=1;i<n;i++) {
            if(nums[i]<nums[min]) min=i;
            if(nums[i]>nums[max]) max=i;
        }

        int left=Math.min(min,max);
        int right=Math.max(min,max);

        int ans=Math.min(
            right+1,
            n-left
        );

        ans=Math.min(
            ans,
            left+1+n-right
        );

        return ans;
    }
}