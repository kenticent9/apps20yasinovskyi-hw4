
package ua.edu.ucu.autocomplete;

import static org.hamcrest.Matchers.containsInAnyOrder;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import ua.edu.ucu.tries.RWayTrie;

/**
 *
 * @author Andrii_Rodionov
 */
public class PrefixMatchesITTest {

    private PrefixMatches pm;
    private PrefixMatches pm2;

    @Before
    public void init() {
        pm = new PrefixMatches(new RWayTrie());
        pm.load("abc", "abce", "abcd", "abcde", "abcdef");
        pm2 = new PrefixMatches(new RWayTrie());
        pm2.load("she", "sells", "sea", "shells", "the", "sea",
                "shore");

    }

    @Test
    public void testWordsWithPrefix_String() {
        String pref = "ab";

        Iterable<String> result = pm.wordsWithPrefix(pref);
        for (String res : result) {
            System.out.println(res);
        }
        String[] expResult = {"abc", "abce", "abcd", "abcde", "abcdef"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix_String_and_K() {
        String pref = "abc";
        int k = 3;

        Iterable<String> result = pm.wordsWithPrefix(pref, k);

        String[] expResult = {"abc", "abce", "abcd", "abcde"};

        assertThat(result, containsInAnyOrder(expResult));
    }

    @Test
    public void testWordsWithPrefix() {
        assertThat(pm2.wordsWithPrefix("sh"), containsInAnyOrder("she",
                "shells", "shore"));
    }

    @Test
    public void testSize() {
        assertEquals(6, pm2.size());
        pm2.delete("the");
        assertEquals(5, pm2.size());
        pm2.delete("sea");
        assertEquals(4, pm2.size());
        pm2.delete("she");
        assertEquals(3, pm2.size());
        pm2.delete("dinosaur");
        assertEquals(3, pm2.size());
    }

}
