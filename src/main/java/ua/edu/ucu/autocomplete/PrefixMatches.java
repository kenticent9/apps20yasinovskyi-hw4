package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.*;

/**
 *
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        if (strings.length == 1) {
            strings = strings[0].split(" ");
        }
        for (String string : strings) {
            if (string.length() > 2) {
                trie.add(new Tuple(string, string.length()));
            }
        }
        return trie.size();
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) {
        return trie.wordsWithPrefix(pref);
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) {
        List<String> result = new ArrayList<>();
        Set<Integer> seen = new HashSet<>();
        for (String word : wordsWithPrefix(pref)) {
            if (!seen.contains(word.length()) && k-- > 0) {
                seen.add(word.length());
                result.add(word);
            } else if (seen.contains(word.length())) {
                result.add(word);
            }
        }
        return result;
    }

    public int size() {
        return trie.size();
    }
}
