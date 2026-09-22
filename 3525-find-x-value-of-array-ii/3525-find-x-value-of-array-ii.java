class Solution {
    class Node {
        long[] c=new long[5];
        int p=1;
    }

    int n,k;
    Node[] t;

    Node merge(Node a,Node b) {
        Node r=new Node();
        r.p=a.p*b.p%k;
        for(int i=0;i<k;i++) {
            r.c[i]+=a.c[i];
            r.c[a.p*i%k]+=b.c[i];
        }
        return r;
    }

    void build(int p,int l,int r,int[] a) {
        if(l==r) {
            int x=a[l]%k;
            t[p].p=x;
            t[p].c[x]=1;
            return;
        }
        int m=(l+r)/2;
        build(p*2,l,m,a);
        build(p*2+1,m+1,r,a);
        t[p]=merge(t[p*2],t[p*2+1]);
    }

    void update(int p,int l,int r,int i,int v) {
        if(l==r) {
            t[p]=new Node();
            t[p].p=v%k;
            t[p].c[t[p].p]=1;
            return;
        }
        int m=(l+r)/2;
        if(i<=m) update(p*2,l,m,i,v);
        else update(p*2+1,m+1,r,i,v);
        t[p]=merge(t[p*2],t[p*2+1]);
    }

    Node query(int p,int l,int r,int ql,int qr) {
        if(ql<=l&&r<=qr) return t[p];
        int m=(l+r)/2;
        if(qr<=m) return query(p*2,l,m,ql,qr);
        if(ql>m) return query(p*2+1,m+1,r,ql,qr);
        return merge(query(p*2,l,m,ql,qr),
                     query(p*2+1,m+1,r,ql,qr));
    }

    public int[] resultArray(int[] nums,int k,int[][] queries) {
        this.k=k;
        n=nums.length;
        t=new Node[4*n];
        for(int i=0;i<4*n;i++) t[i]=new Node();
        build(1,0,n-1,nums);

        int[] ans=new int[queries.length];
        for(int i=0;i<queries.length;i++) {
            int[] q=queries[i];
            update(1,0,n-1,q[0],q[1]);
            ans[i]=(int)query(1,0,n-1,q[2],n-1).c[q[3]];
        }
        return ans;
    }
}