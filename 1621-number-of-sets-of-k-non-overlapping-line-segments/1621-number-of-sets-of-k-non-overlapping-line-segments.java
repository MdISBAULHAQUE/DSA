class Solution {
    static final long MOD=1000000007;

    long pow(long a,long b) {
        long ans=1;
        while(b>0) {
            if(b%2==1) ans=ans*a%MOD;
            a=a*a%MOD;
            b/=2;
        }
        return ans;
    }

    public int numberOfSets(int n,int k) {
        long N=n+k-1;
        long R=2*k;
        long num=1,den=1;

        for(int i=1;i<=R;i++) {
            num=num*(N-R+i)%MOD;
            den=den*i%MOD;
        }

        return (int)(num*pow(den,MOD-2)%MOD);
    }
}