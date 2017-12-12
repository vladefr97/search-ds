import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import ru.mail.polis.ChainHashTable;

/**
 * Created by Nechaev Mikhail
 * Since 12/12/2017.
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestHashTable extends AbstractSetTest {

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
            check(validSet, testSet, value, TransformOperation.ADD);
        }
    }

    @Test
    public void test02() {
        String value = "b";
        for (int i = 0; i < 20; i++) {
            value = value + "a";
            check(validSet, testSet, value, TransformOperation.ADD);
        }
        for (int i = 20; i >= 0; i--) {
            value = value.substring(0, value.length() - 1);
            check(validSet, testSet, value, TransformOperation.REMOVE);
        }
    }

    @Test
    public void test03() {
        for (int i = 0; i < 1000; i++) {
            check(validSet, testSet, gen(), TransformOperation.ADD);
        }
        for (int i = 0; i < 1000; i++) {
            check(validSet, testSet, gen(), TransformOperation.REMOVE);
        }
    }

    private String gen() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 10; i++) {
            sb.append((char) RANDOM.nextInt(26) + 'a');
        }
        return sb.toString();
    }

    private void check(Set<String> validSet, Set<String> testSet, String value, TransformOperation transformOperation) {
        checkSizeAndContains(validSet, testSet, value);
        checkTransformOperation(validSet, testSet, value, transformOperation);
        checkSizeAndContains(validSet, testSet, value);
        checkTransformOperation(validSet, testSet, value, transformOperation);
        checkSizeAndContains(validSet, testSet, value);
    }

}
