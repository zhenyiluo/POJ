package Problem2104PersistentSegmentTree;


import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Zhenyi Luo on 15-5-15.
 */
public class Main {
    public static class Node{
        int left;
        int right;
        int sum;
        public Node(int left, int right, int sum){
            this.left = left;
            this.right = right;
            this.sum = sum;
        }
    }
    static Node[] tree;
    static int c = 0;

    public static int newNode(int left, int right, int pos){
        tree[++c] = new Node(left, right, tree[pos].sum + 1);
        return c;
    }

    public static int buildTree(int l, int r){
        if(l == r){
            return newNode(0, 0, 0);
        }
        int mid = (l + r ) >> 1;
        return newNode(buildTree(1, mid), buildTree(mid+1, r), 0);
    }

    public static int insert(int p, int L, int R, int pos){
        if(L == R){
            return newNode(tree[pos].left, tree[pos].right, pos);
        }
        int mid = (L+ R) >> 1;
        if(p <= mid){
            return newNode(insert(p, L, mid, tree[pos].left), tree[pos].right, pos);
        }else{
            return newNode(tree[pos].left, insert(p, mid+1, R, tree[pos].right), pos);
        }
    }
    public static int query(int pa, int pb, int L, int R, int k){
        if( L == R) return L;
        int mid = (L + R) >> 1;
        int sum = tree[tree[pb].left].sum - tree[tree[pa].left].sum;
        if(k <= sum){
            return query(tree[pa].left, tree[pb].left, L, mid, k);
        }else{
            return query(tree[pa].right, tree[pb].right, mid+1, R, k-sum);
        }
    }

    public static class Pair implements Comparable<Pair>{
        int pos;
        int num;

        public Pair(int pos, int num){
            this.num = num;
            this.pos = pos;
        }
        @Override
        public int compareTo(Pair p){
            return this.num - p.num;
        }
    }

    public static void solve(PrintWriter pw, Scanner sc){
        int n = sc.nextInt();
        int m = sc.nextInt();
        Pair[] pairs = new Pair[100005];
        int[] rank  = new int[100005];
        int[] root = new int[100005];
        tree = new Node[2000005];
        for(int i = 1; i <= n; i++){
            pairs[i] = new Pair(i, sc.nextInt());
        }
        Arrays.sort(pairs, 1, n+1);
        for(int i = 1; i <= n; i++){
            rank[pairs[i].pos] = i;
        }
        tree[0] = new Node(0, 0, -1);
        root[0] = buildTree(1, n);
        for(int i = 1; i <= n; i++){
            root[i] = insert(rank[i], 1, n, root[i-1]);
        }
        for(int i =1 ; i<= m; i++){
            int l = sc.nextInt();
            int r = sc.nextInt();
            int k = sc.nextInt();
            pw.println(pairs[query(root[l-1], root[r], 1, n, k)].num);
        }
    }

    public static void main(String[] args){
        PrintWriter pw = new PrintWriter(System.out);
        Scanner sc = new Scanner(System.in);
        new Main().solve(pw, sc);
        pw.flush();
        pw.close();
        sc.close();
    }

}
