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
            return nexts[pos].contains(data);
        }

        public NonLeafNode<T> split(T data) {
            if (!isFull()) return null;
            return null;
        }

        public boolean insert(T data) {
            if ( size < order / 2 ) {
                return false;
            };
            
            int compareResult;
            int insertPos = 0;
            while (insertPos < size) {
                compareResult = data.compareTo( elements[ insertPos ] );
                if ( compareResult == 0 ) {
                    return false;
                }
                if ( compareResult < 0) {
                    break;
                }
                insertPos++;
            }

            if (!nexts[insertPos].insert(data)) {
                if (!isFull()) {
                    for (int i = size; i > insertPos; i--) {
                        elements[i] = elements[i - 1];
                        nexts[i + 1] = nexts[i];
                    }
                    nexts[insertPos + 1] = nexts[insertPos];
//                    nexts[insertPos] = nexts[insertPos + 1].split(data);
//                    elements[insertPos] = nexts[insertPos + 1].getMin();
                    return true;
                } else {
                    return false;
                }
            }
            return false;
        }

        public boolean remove(T data) {
            // TODO
            if ( isEmpty() ) return false;

            int compareResult = 0;
            int removePos = 0;
            while (removePos < size) {
                compareResult = data.compareTo( elements[ removePos ] );
                if ( compareResult <= 0 ) {
                    break;
                }
                removePos++;
            }

            if (compareResult == 0) {
//                if ( nexts[removePos + 1].length );
            }
            if (compareResult < 0) {}
            // if removePos == size, data was not found here
            if (removePos < size) {
                for (int i = removePos; i < size; i++) {
                    elements[i] = elements[i + 1];
                }
                size--;
                return true;
            }

            return false;
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

        public T getMin() {
            return elements[0];
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

            for (int i = size; i > insertPos; i--) {
                elements[i] = elements[i - 1];
            }

            elements[ insertPos ] = data;
            size++;
            return true;
        }

        public LeafNode<T> split(T data) {
            if (!isFull()) return null;
            T[] temp = (T[])new Object[size + 1];
            T[] newElem = (T[])new Object[leafSize];

            int compareResult;
            int i = 0;
            while (i < leafSize) {
                compareResult = data.compareTo( elements[ i ] );
                if ( compareResult <= 0 ) {
                    temp[i] = elements[i];
                } else if ( compareResult > 0 ) {
                    temp[i] = data;
                }
                i++;
            }

            int half = temp.length / 2;
            size = 0;
            int newSize = 0;
            for (i = 0; i < temp.length ; i++) {
                if (i < half) {
                    newElem[i] = temp[i];
                    newSize++;
                } else {
                    elements[i - half] = temp[i];
                    size++;
                }
            }

            LeafNode n = new LeafNode();

            for (i = 0; i < newSize; i++) {
                n.insert(newElem[i]);
            }
            return n;
        }

        public boolean remove(T data) {
            if ( isEmpty() ) return false;

            int compareResult;
            int removePos = 0;
            while (removePos < size) {
                compareResult = data.compareTo( elements[ removePos ] );
                if ( compareResult == 0 ) {
                    break;
                }
                removePos++;
            }

            // if removePos == size, data was not found here
            if (removePos < size) {
                for (int i = removePos; i < size; i++) {
                    elements[i] = elements[i + 1];
                }
                size--;
                return true;
            }

            return false;
        }
    } 
}