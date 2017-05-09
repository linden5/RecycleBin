package me.tree;

import java.util.*;

public class WordCount {
    public static void printHighChangeables(Map<String, List<String>> adjWords, int minWords) {
        for (  Map.Entry<String, List<String>> entry : adjWords.entrySet() ) {
            List<String> words = entry.getValue();

            if (words.size() >= minWords) {
                System.out.print( entry.getKey() + "(" );
                System.out.print( words.size() + "):" );
                for ( String w : words ) {
                    System.out.print(" " + w);
                }
                System.out.println();
            }
        }
    }

    // Returns true if word1 and word2 are the same length
    // and differ in only one character.
    private static boolean oneCharOff( String word1, String word2 ) {
        if ( word1.length() != word2.length() ) return false;

        int diffs = 0;

        for (int i = 0; i < word1.length(); i++) {
            if ( word1.charAt(i) != word2.charAt(i) )
                if (++diffs > 1) return false;
        }

        return diffs == 1;
    }

    // Computes a map in which the keys are words and values are lists of words
    // that differ in only one character from the corresponding key.
    // Uses a quadratic algorithm (with appropriate Map).
    public static Map<String, List<String>> computedAdjacentWords( List<String> theWords) {
        Map<String, List<String>> adjWords = new TreeMap<String, List<String>>();

        String[] words = new String[ theWords.size() ];

        theWords.toArray( words );
        for (int i = 0; i < words.length; i++) {
            for (int j = i + 1; j < words.length; j++) 
                if( oneCharOff( words[i], words[j] ) ) {
                    update( adjWords, words[i], words[j]);
                    update( adjWords, words[j], words[i]);
                }
        }

        return adjWords;
    }

    private static <KeyType> void update( Map<KeyType, List<String>> m, KeyType key, String value ) {
        List<String> lst = m.get( key );
        if ( lst == null ) {
            lst = new ArrayList<String>();
            m.put( key, lst );
        }

        lst.add( value );
    }

    // Computes a map in which the keys are words and values are Lists of 
    // words that differ in only one character from the corresponding key.
    // Uses a quadratic algorithm (with appropriate Map), but speeds things by
    // maintaining an additional map that groups words by their length.
    public static Map<String, List<String>> computedAdjacentWords2( List<String> theWords) {
        Map<String, List<String>> adjWords = new TreeMap<String, List<String>>();
        Map<Integer, List<String>> wordsByLength = new TreeMap<Integer, List<String>>();

        // Group the words by their length
        for ( String w : theWords )
            update( wordsByLength, w.length(), w );

        // Work on each group separately
        for ( List<String> groupsWords : wordsByLength.values() ) {
            String[] words = new String[ groupsWords.size() ];

            groupsWords.toArray( words );
            for ( int i = 0; i < words.length; i++ )
                for ( int j = i + 1; j < words.length; j++ )
                    if (oneCharOff( words[i], words[j] )) {
                        update( adjWords, words[i], words[j] );
                        update( adjWords, words[j], words[i] );
                    }
        }

        return adjWords;
    }

    // Computes a map in which the keys are words and values are Lists of words
    // that differ in only one character from the corresponding key.
    // Uses an efficient algorithm that is O(N log N) with a TreeMap.
    public static Map<String, List<String>> computedAdjacentWords3( List<String> words) {
        Map<String, List<String>> adjWords = new TreeMap<String, List<String>>();
        Map<Integer, List<String>> wordsByLength = new TreeMap<Integer, List<String>>();

        // Group the words by their length
        for (String w : words)
            update( wordsByLength, w.length(), w );

        // Work on each group separately
        for ( Map.Entry<Integer, List<String>> entry : wordsByLength.entrySet() ) {
            List<String> groupsWords = entry.getValue();
            int groupNum = entry.getKey();

            // Work on each position in each group
            for (int i = 0; i < groupNum; i++) {
                // Remove one character in specified position, computing
                // representative. Words with same representative are
                // adjacent, so first populate a map...
                Map<String, List<String>> repToWord = new TreeMap<String, List<String>>();

                for (String str : groupsWords) {
                    String rep = str.substring( 0, i ) + str.substring( i + 1 );
                    update( repToWord, rep, str );
                }

                // and then look for map values with more than one string
                for ( List<String> wordClique : repToWord.values() ) {
                    if ( wordClique.size() >= 2 )
                        for ( String s1 : wordClique )
                            for ( String s2 : wordClique )
                                if ( s1 != s2 )
                                    update( adjWords, s1, s2 );
                }
            }
        }

        return adjWords;
    }
}