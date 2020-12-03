package ua.edu.ucu.tries;

import ua.edu.ucu.collections.Queue;

public class RWayTrie implements Trie {
    private static final int R = 26;
    private Node root = new Node();
    private int size = 0;

    private static class Node {
        private int val = -1;
        private Node[] next = new Node[R];

        private int numChildren() {
            int counter = 0;
            for (Node node : next) {
                if (node != null) {
                    counter++;
                }
            }
            return counter;
        }
    }

    public int get(String term) {
        Node x = get(root, term, 0);
        if (x == null) return -1;  // The length will be non-negative, so
                                   // -1 is convenient for signaling a failure
        return x.val;
    }

    private Node get(Node x, String term, int d) {
        if (x == null) return null;
        if (d == term.length()) return x;
        char c = term.charAt(d);
        return get(x.next[c-'a'], term, d+1);
    }

    @Override
    public void add(Tuple t) {
        root = add(root, t.term, t.weight, 0);
    }

    /* weight is the length of the string (term) */
    private Node add(Node x, String term, int weight, int d) {
        if (x == null) x = new Node();
        if (d == weight) {
            if (x.val == -1) {
                size++;
            }
            x.val = weight;
            return x;
        }
        char c = term.charAt(d);
        x.next[c-'a'] = add(x.next[c-'a'], term, weight, d+1);
        return x;
    }

    @Override
    public boolean contains(String word) {
        return get(word) != -1;
    }

    /* Delete the value corresponding to the word */
    @Override
    public boolean delete(String word) {
        Node curNode = root;
        Node lastNode = root;  // The last node which has a non-negative value
                               // or > 1 children
        int i = 0;
        char lastC = 0;
        while (curNode != null && i != word.length()-1) {
            if (curNode.val != -1 || curNode.numChildren() > 1) {
                lastNode = curNode;
                lastC = word.charAt(i);
            }
            char c = word.charAt(i++);
            curNode = curNode.next[c-'a'];
        }
        if (curNode != null) {
            if (curNode.numChildren() != 0) {
                curNode.val = -1;
            } else {
                lastNode.next[lastC-'a'] = null;
            }
            size--;
            return true;
        }
        return false;
    }

    @Override
    public Iterable<String> words() {
        return wordsWithPrefix("");
    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Queue q = new Queue();
        collect(get(root, s, 0), s, q);
        return q;
    }

    private void collect(Node x, String s, Queue q) {
        if (x == null) return;
        if (x.val != -1) q.enqueue(s);
        for (char i = 0; i < R; i++) {
            collect(x.next[i], s + (char) (i+'a'), q);
        }
    }

    @Override
    public int size() {
        return size;
    }

}
