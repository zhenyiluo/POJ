package Problem2104;

import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Scanner;

/**
 * Created by Zhenyi Luo on 15-5-14.
 */
public class Main {
    public static class P_Tree{
        int[] arr;
        int[] sorted;
        int[][] data;
        int[][] sum;
        public void build(int c, int L, int R){
            int mid = (L + R) >> 1;
            int lsame = mid - L + 1;
            int lp = L;
            int rp = mid + 1;
            for(int i = L; i < mid; i++){
                if(sorted[i] < sorted[mid]){
                    lsame --;
                }
            }

            for(int i = L; i <= R; i++){
                if(i == L ){
                    sum[c][i] = 0;
                }else{
                    sum[c][i] = sum[c][i-1];
                }

                if(data[c][i] < sorted[mid]){
                    data[c+1][lp++] = data[c][i];
                    sum[c][i]++;
                }else if( data[c][i] > sorted[mid]){
                    data[c+1][rp++] = data[c][i];
                }else{
                    if(lsame != 0){
                        lsame --;
                        sum[c][i]++;
                        data[c+1][lp++] = sorted[mid];
                    }else{
                        data[c+1][rp++] = sorted[mid];
                    }
                }
            }

            if(L == R) return;
            build(c+1, L, mid);
            build(c+1, mid+1, R);
        }

        public int query(int c, int L, int R, int ql, int qr, int k){
            if(L == R){
                return data[c][L];
            }
            int s;
            int ss;
            int mid = (L + R) >> 1;
            if(L == ql){
                s = 0;
                ss = sum[c][qr];
            }else{
                s = sum[c][ql -1];
                ss = sum[c][qr] - s;
            }
            if(k <= ss){
                return query(c+1,L, mid, L+s, L +s+ss-1, k);
            }else{
                return query(c+1, mid+1, R, mid+1+ql-s-L, mid+1+qr-s-ss-L, k -ss);
            }
        }
    }

    public static void solve(PrintWriter pw, Scanner sc){
        int n = sc.nextInt();
        int m = sc.nextInt();
        P_Tree tree = new P_Tree();
        tree.arr = new int[n+1];
        tree.sorted = new int[n+1];
        tree.data = new int[20][n+1];
        tree.sum = new int[20][n+1];
        for(int i = 1; i <= n; i++){
            tree.arr[i] = sc.nextInt();
            tree.sorted[i] = tree.data[0][i] = tree.arr[i];
        }
        Arrays.sort(tree.sorted, 1, n+1);
        tree.build(0, 1, n);
        for(int i = 1; i <= m; i++){
            int l = sc.nextInt();
            int r = sc.nextInt();
            int k = sc.nextInt();
            pw.println(tree.query(0, 1, n, l, r, k));
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
