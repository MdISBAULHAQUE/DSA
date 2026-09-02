class Solution{
public boolean uniformArray(int[] nums1){
int min=nums1[0];
for(int x:nums1)min=Math.min(min,x);
for(int x:nums1)if(x%2!=min%2&&x<min)return false;
return true;
}
}