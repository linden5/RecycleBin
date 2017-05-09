package me.hash;

import java.util.LinkedList;
import java.util.List;

public class SeparateChainingHashTable<AnyType> {
    public SeparateChainingHashTable() {
        this(DEFAULT_TABLE_SIZE);
    }

    public SeparateChainingHashTable(int size) {
        theLists = new LinkedList[ nextPrime(size) ];
        for (int i = 0; i < theLists.length; i++)
            theLists[i] = new LinkedList<AnyType>();
    }

    public void insert(AnyType x) {
        List<AnyType> whichList = theLists[myhash(x)];
        if (!whichList.contains(x)) {
            whichList.add(x);
            if ( ++currentSize < theLists.length)
                rehash();
        }
    }

    public void remove(AnyType x) {
        List<AnyType> whichList = theLists[ myhash(x) ];
        if (whichList.contains(x)) {
            whichList.remove(x);
            currentSize--;
        }
    }

    public boolean contains(AnyType x) {
        List<AnyType> whichList = theLists[myhash(x)];
        return whichList.contains(x);
    }

    public void makeEmpty() {
        for (int i = 0; i < theLists.length; i++)
            theLists[i].clear();
        currentSize = 0;
    }

    private static final int DEFAULT_TABLE_SIZE = 101;

    private List<AnyType>[] theLists;
    private int currentSize;

    private void rehash() {
        List<AnyType>[] oldLists = theLists;
        theLists = new List[nextPrime(2 * theLists.length)];
        for (int j = 0; j < theLists.length; j++)
            theLists[j] = new LinkedList<AnyType>();

        currentSize = 0;
        for ( int i = 0; i < oldLists.length; i++ )
            for ( AnyType item : oldLists[i] )
                insert(item);
    }

    private int myhash(AnyType x) {
        int hashVal = x.hashCode();

        hashVal %= theLists.length;
        if (hashVal < 0) {
            hashVal += theLists.length;
        }

        return hashVal;
    }

    static int nextPrime( int n ) {
        while(true) {
            if ( n % 2 == 0 ) {
                n++;
            }
            if (!isPrime(n)) {
                n++;
            } else {
                return n;
            }
        }
    }

    private static int[] primeList = {2, 3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37,
            41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97
    };

    static boolean isPrime(int n) {
        if (n < 2) {
            return false;
        }

        int listNum = primeList.length;
        for (int i = 0; i < listNum; i++) {
            if (primeList[i] == n) continue;
            if ( 1 != montgomery(primeList[i], n - 1, n)) return false;
        }
        return true;
    }

    static int montgomery(int n, int p, int m) {
        int k = 1;
        n %= m;
        while (p != 1) {
            if ( 0 != (p & 1)) k = (k * n) % m;
            n = (n * n) % m;
            p >>= 1;
        }

        return (n * k) % m;
    }
}