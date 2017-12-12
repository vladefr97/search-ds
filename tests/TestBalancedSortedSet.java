import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import org.junit.Assert;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.junit.runners.Parameterized;

import ru.mail.polis.AVLTree;
import ru.mail.polis.BalancedSortedSet;
import ru.mail.polis.NotBalancedTreeException;
import ru.mail.polis.RedBlackTree;

/**
 * Created by Nechaev Mikhail
 * Since 12/12/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@RunWith(value = Parameterized.class)
public class TestBalancedSortedSet {

    static class NamedComparator<E> {
        String name;
        Comparator<E> comparator;

        public NamedComparator(String name, Comparator<E> comparator) {
            this.name = name;
            this.comparator = comparator;
        }
    }

    private static final Random RANDOM = new Random();

    //todo: Закомментируйте или модифицируйте параметры если что-то ещё не реализовано и тестируйте

    private static final boolean ENABLED_REMOVE = false;

    private static final Class<?>[] testClasses = (Class<?>[]) new Class<?>[]{
            AVLTree.class,
            RedBlackTree.class,
    };

    @SuppressWarnings("unchecked")
    private static final NamedComparator<Integer>[] comparators = (NamedComparator<Integer>[]) new NamedComparator[]{
            new NamedComparator<Integer>("NULL", null),
            new NamedComparator<Integer>("REVERSE_ORDER", Comparator.reverseOrder()),
            new NamedComparator<Integer>("NATURAL_ORDER", Comparator.naturalOrder()),
            new NamedComparator<Integer>("EVEN_FIRST", Comparator.comparingInt((Integer v) -> v % 2).thenComparingInt(v -> v)),
            new NamedComparator<Integer>("ALL_EQUALS", (v1, v2) -> 0),
    };

    //todo: ^^^^^^^^^^^^^

    @Rule
    public TestRule watcher = new TestWatcher() {
        protected void starting(final Description description) {
            System.err.println("=== Running " + description.getMethodName());
        }
    };

    @Parameterized.Parameter()
    public Class<?> testClass;
    @Parameterized.Parameter(1)
    public Comparator<Integer> comparator;
    @Parameterized.Parameter(2)
    public String comparatorName;

    @Parameterized.Parameters(name = "{0} + {2}")
    public static Collection<Object[]> data() {
        Object[][] objects = new Object[testClasses.length * comparators.length][3];
        int index = 0;
        for (Class<?> testClazz : testClasses) {
            for (NamedComparator<Integer> namedComparator : comparators) {
                objects[index++] = new Object[]{
                        testClazz,
                        namedComparator.comparator,
                        namedComparator.name
                };
            }
        }
        return Arrays.asList(objects);
    }

    private SortedSet<Integer> validSortedSet;
    private BalancedSortedSet<Integer> testSortedSet;

    @Before //Запускается перед запуском каждого теста
    public void createSortedSets() {
        validSortedSet = create(TreeSet.class);
        testSortedSet = createTestSortedSet(testClass);
    }

    @Test
    public void test01_emptyAndNull() {
        checkFirstAndLast(validSortedSet, testSortedSet);
        checkSizeAndContains(validSortedSet, testSortedSet, 0);
    }

    @Test
    public void test02_add() {
        check(validSortedSet, testSortedSet, 5, true);
    }

    @Test
    public void test02_someAdd() {
        for (int value = 0; value < 10; value++) {
            check(validSortedSet, testSortedSet, value, true);
        }
    }

    @Test
    public void test03_AddWithRemove() {
        check(validSortedSet, testSortedSet, 5, true);
        check(validSortedSet, testSortedSet, 5, false);
    }

    @Test
    public void test04_someAddWithRemove() {
        for (int value = 0; value < 10; value++) {
            check(validSortedSet, testSortedSet, value, true);
        }
        for (int value = 0; value < 10; value++) {
            check(validSortedSet, testSortedSet, value, false);
        }
    }

    @Test
    public void test05_small() {
        for (int value = 0; value < 10; value++) {
            check(validSortedSet, testSortedSet, value, true);
        }
        for (int value = 10; value >= 0; value--) {
            check(validSortedSet, testSortedSet, value, false);
        }
        for (int value = 0; value >= -10; value--) {
            check(validSortedSet, testSortedSet, value, false);
        }
        for (int value = -10; value < 0; value++) {
            check(validSortedSet, testSortedSet, value, true);
        }
    }

    @Test
    public void test06_middle() {
        for (int value = 0; value < 15; value++) {
            check(validSortedSet, testSortedSet, value, true);
        }
        for (int value = 15; value < 30; value++) {
            check(validSortedSet, testSortedSet, value, true);
            if (value % 2 == 0) {
                check(validSortedSet, testSortedSet, value, false);
            }
        }
        for (int value = 100; value >= 0; value--) {
            check(validSortedSet, testSortedSet, value, false);
        }
    }

    @Test
    public void test07_bigRandom() {
        for (int i = 0; i < 1000; i++) {
            check(validSortedSet, testSortedSet, RANDOM.nextInt(1000), true);
        }
        for (int i = 0; i < 1000; i++) {
            check(validSortedSet, testSortedSet, RANDOM.nextInt(1000), false);
        }
    }

    private <E> void check(SortedSet<E> validSortedSet, BalancedSortedSet<E> testSortedSet, E value, boolean add) {
        checkFirstAndLast(validSortedSet, testSortedSet);
        checkTransformOperation(validSortedSet, testSortedSet, value, add);
        checkSizeAndContains(validSortedSet, testSortedSet, value);
        checkTransformOperation(validSortedSet, testSortedSet, value, add);
        checkSizeAndContains(validSortedSet, testSortedSet, value);
        checkFirstAndLast(validSortedSet, testSortedSet);
    }

    private <E> void checkTransformOperation(SortedSet<E> validSortedSet, BalancedSortedSet<E> testSortedSet, E value, boolean add) {
        if (add) {
            Assert.assertTrue("add", validSortedSet.add(value) == testSortedSet.add(value));
        } else if (ENABLED_REMOVE) {
            Assert.assertTrue("remove", validSortedSet.remove(value) == testSortedSet.remove(value));
        }
        try {
            testSortedSet.checkBalanced();
        } catch (NotBalancedTreeException e) {
            Assert.fail(e.getMessage());
        }
    }

    private <E> void checkFirstAndLast(SortedSet<E> validSortedSet, SortedSet<E> testSortedSet) {
        if (validSortedSet.isEmpty()) {
            try {
                testSortedSet.first();
                Assert.fail("NoSuchElementException - first");
            } catch (NoSuchElementException e) {
                /* empty */
            }
            try {
                testSortedSet.last();
                Assert.fail("NoSuchElementException - last");
            } catch (NoSuchElementException e) {
                /* empty */
            }
        } else {
            Assert.assertEquals("first", validSortedSet.first(), testSortedSet.first());
            Assert.assertEquals("last", validSortedSet.last(), testSortedSet.last());
        }
    }

    private <E> void checkSizeAndContains(SortedSet<E> validSortedSet, SortedSet<E> testSortedSet, E value) {
        Assert.assertTrue("size", validSortedSet.size() == testSortedSet.size());
        Assert.assertTrue("contains", validSortedSet.contains(value) == testSortedSet.contains(value));
    }

    @SuppressWarnings("unchecked")
    private SortedSet<Integer> create(Class<?> clazz) {
        try {
            return (SortedSet<Integer>) clazz.getConstructor(Comparator.class).newInstance(comparator);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new AssertionError(e);
        }
    }

    @SuppressWarnings("unchecked")
    private BalancedSortedSet<Integer> createTestSortedSet(Class<?> clazz) {
        try {
            return (BalancedSortedSet<Integer>) clazz.getConstructor(Comparator.class).newInstance(comparator);
        } catch (InstantiationException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            throw new AssertionError(e);
        }
    }
}
