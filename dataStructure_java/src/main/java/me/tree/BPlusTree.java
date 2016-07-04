package me.tree;

// Order 5, with 5-element leaf node 
public class BPlusTree<T extends Comparable<? super T>> {
    private static final int leafSize = 5;
    private static final int order = 5;

    private static abstract class Node<T> {
        int size;
        T[] elements;
        public abstract boolean isFull();
        public abstract boolean isEmpty();
        public abstract boolean isMinimal();
        public abstract boolean contains(T data);
        public abstract boolean insert(T data);
        public abstract boolean remove(T data);
    }

    private static class NonLeafNode<T extends Comparable<? super T>> extends Node<T> {
        Node<T>[] nexts;

        public NonLeafNode() {
            elements = (T[])new Object[ order - 1 ];
            nexts = (LeafNode<T>[])new Object[ order ];
            size = 0;
        }

        public boolean isFull() {
            return size == order - 1;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isRootMin() {
            return size == 1;
        }

        public boolean isMinimal() {
            return size == (order - 1) / 2;
        }

        public boolean contains(T data) {
            int pos = -1;
            for (int i = 0; i < size; i++) {
                if ( data.compareTo( elements[i] ) < 0 ) {
                    pos = i;
                    break;
                }
            }

            if ( pos == -1)
                pos = size + 1;
            return contains(data, nexts[pos]);
        }

        private boolean contains(T data, Node<T> list) {
            // TODO
            return false;
        }

        public boolean insert(T data) {
            // TODO
            if ( isFull() ) return false;
            if ( isEmpty() ) {
                elements[size] = data;
                return true;
            }

            int compareResult;
            int insertPos = 0;
            while (insertPos < size) {
                compareResult = data.compareTo( elements[ insertPos ] );
                if ( compareResult < 0 ) {
                    break;
                } else if ( compareResult == 0 ) {
                    return false;
                }
                insertPos++;
            }

            for (int i = size - 1; i > insertPos; i--) {
                elements[i] = elements[i - 1];
            }

            elements[ insertPos ] = data;
            size++;
            return true;
        }

        public boolean remove(T data) {
            // TODO
            if ( isMinimal() || isEmpty() ) return false;

            int compareResult;
            int removePos = 0;
            while (removePos < size) {
                compareResult = data.compareTo( elements[ removePos ] );
                if ( compareResult == 0 ) {
                    break;
                } else if ( compareResult == 0 ) {
                    return false;
                }
                removePos++;
            }

            for (int i = removePos; i < size - 1; i++) {
                elements[i] = elements[i + 1];
            }

            size--;
            return true;
        }
    }

    private static class LeafNode<T extends Comparable<? super T>> extends Node<T>{
        LeafNode<T> nextLeaf = null;

        public LeafNode() {
            elements = (T[])new Object[ leafSize ];
            size = 0;
        }

        public boolean isFull() {
            return size == leafSize;
        }

        public boolean isEmpty() {
            return size == 0;
        }

        public boolean isMinimal() {
            return size == (leafSize + 1) / 2;
        }

        public boolean contains(T data) {
            for (int i = 0; i < size; i++) {
                if ( data.compareTo( elements[i] ) == 0 )
                    return true;
            }
            return false;
        }

        public boolean insert(T data) {
            if ( isFull() ) return false;
            if ( isEmpty() ) {
                elements[size] = data;
                return true;
            }

            int compareResult;
            int insertPos = 0;
            while (insertPos < size) {
                compareResult = data.compareTo( elements[ insertPos ] );
                if ( compareResult < 0 ) {
                    break;
                } else if ( compareResult == 0 ) {
                    return false;
                }
                insertPos++;
            }

            for (int i = size - 1; i > insertPos; i--) {
                elements[i] = elements[i - 1];
            }

            elements[ insertPos ] = data;
            size++;
            return true;
        }

        public boolean remove(T data) {
            if ( isMinimal() || isEmpty() ) return false;

            int compareResult;
            int removePos = 0;
            while (removePos < size) {
                compareResult = data.compareTo( elements[ removePos ] );
                if ( compareResult == 0 ) {
                    break;
                } else if ( compareResult == 0 ) {
                    return false;
                }
                removePos++;
            }

            for (int i = removePos; i < size - 1; i++) {
                elements[i] = elements[i + 1];
            }

            size--;
            return true;
        }
    } 
}