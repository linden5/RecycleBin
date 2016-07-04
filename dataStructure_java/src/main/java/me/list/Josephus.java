package me.list;

public class Josephus {
    private CircularLinkedList<Integer> players;
    private int M;

    public Josephus( int N, int M) {
        if ( N < 2)
            throw new RuntimeException("Player number must be greater than 1");

        this.M = M;

        players = new CircularLinkedList<Integer>();
        for (int i = 1; i < N + 1; i++) {
            players.add(i);
        }
    }

    public void startGame() {
        java.util.Iterator<Integer> it = players.iterator();

        int current;
        do {
            current = it.next();
            if ( players.size() == 1) {
                System.out.println("The winner is player " + current);
                break;
            }
            for ( int i = 0; i < M; i++) {
                current = it.next();
            }

            System.out.println("Player" + current + " is out.");
            it.remove();
        } while ( it.hasNext() );
    }
}