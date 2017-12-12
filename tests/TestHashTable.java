import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ru.mail.polis.BalancedSortedSet;
import ru.mail.polis.ChainHashTable;
import ru.mail.polis.NotBalancedTreeException;
import ru.mail.polis.OpenHashTable;

/**
 * Created by Nechaev Mikhail
 * Since 12/12/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestHashTable {

    private static final Random RANDOM = new Random();

    private Set<String> validSet;
    private Set<String> testSet;

    @Before //Запускается перед запуском каждого теста
    public void createSortedSets() {
        validSet = new HashSet<>();
        testSet = new ChainHashTable<>();
    }

    @Test
    public void test01() {
        String value = "b";
        for (int i = 0; i < 5; i++) {
            value = value + "a";
            check(validSet, testSet, value, true);
        }
    }

    @Test
    public void test02() {
        String value = "b";
        for (int i = 0; i < 20; i++) {
            value = value + "a";
            check(validSet, testSet, value, true);
        }
        for (int i = 20; i >= 0; i--) {
            value = value.substring(0, value.length() - 1);
            check(validSet, testSet, value, false);
        }
    }

    @Test
    public void test03() {
        for (int i = 0; i < 1000; i++) {
            check(validSet, testSet, gen(), true);
        }
        for (int i = 0; i < 1000; i++) {
            check(validSet, testSet, gen(), false);
        }
    }

    private String gen() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append((char) RANDOM.nextInt(26) + 'a');
        }
        return sb.toString();
    }

    private void check(Set<String> validSet, Set<String> testSet, char c, boolean add) {
        check(validSet, testSet, "" + c, add);
    }

    private void check(Set<String> validSet, Set<String> testSet, String value, boolean add) {
        checkSizeAndContains(validSet, testSet, value);
        checkTransformOperation(validSet, testSet, value, add);
        checkSizeAndContains(validSet, testSet, value);
        checkTransformOperation(validSet, testSet, value, add);
        checkSizeAndContains(validSet, testSet, value);
    }

    private <E> void checkTransformOperation(Set<E> validSet, Set<E> testSet, E value, boolean add) {
        if (add) {
            Assert.assertTrue("add", validSet.add(value) == testSet.add(value));
        } else {
            Assert.assertTrue("remove", validSet.remove(value) == testSet.remove(value));
        }
    }

    private <E> void checkSizeAndContains(Set<E> validSet, Set<E> testSet, E value) {
        Assert.assertTrue("size", validSet.size() == testSet.size());
        Assert.assertTrue("contains", validSet.contains(value) == testSet.contains(value));
    }
}
