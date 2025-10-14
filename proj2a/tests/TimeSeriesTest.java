import ngrams.TimeSeries;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

/**
 * Unit Tests for the TimeSeries class.
 *
 * @author Josh Hug
 */
public class TimeSeriesTest {
    @Test
    public void testFromSpec() {
        TimeSeries catPopulation = new TimeSeries();
        catPopulation.put(1991, 0.0);
        catPopulation.put(1992, 100.0);
        catPopulation.put(1994, 200.0);

        TimeSeries dogPopulation = new TimeSeries();
        dogPopulation.put(1994, 400.0);
        dogPopulation.put(1995, 500.0);

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);
        // expected: 1991: 0,
        //           1992: 100
        //           1994: 600
        //           1995: 500

        List<Integer> expectedYears = new ArrayList<>
                (Arrays.asList(1991, 1992, 1994, 1995));

        assertThat(totalPopulation.years()).isEqualTo(expectedYears);

        List<Double> expectedTotal = new ArrayList<>
                (Arrays.asList(0.0, 100.0, 600.0, 500.0));

        for (int i = 0; i < expectedTotal.size(); i += 1) {
            assertThat(totalPopulation.data().get(i)).isWithin(1E-10).of(expectedTotal.get(i));
        }
    }

    @Test
    public void testEmptyBasic() {
        TimeSeries catPopulation = new TimeSeries();
        TimeSeries dogPopulation = new TimeSeries();

        assertThat(catPopulation.years()).isEmpty();
        assertThat(catPopulation.data()).isEmpty();

        TimeSeries totalPopulation = catPopulation.plus(dogPopulation);

        assertThat(totalPopulation.years()).isEmpty();
        assertThat(totalPopulation.data()).isEmpty();
    }

    @Test
    public void testDividedByNormal() {
        // 1. Arrange (准备阶段)
        TimeSeries ts1 = new TimeSeries(); // 分子
        ts1.put(2000, 10.0);
        ts1.put(2001, 20.0);
        ts1.put(2002, 50.0);

        TimeSeries ts2 = new TimeSeries(); // 分母
        ts2.put(2000, 2.0);
        ts2.put(2001, 5.0);
        ts2.put(2002, 10.0);
        ts2.put(2003, 100.0); // 这个多余的年份应该被忽略

        // 2. Act (执行阶段)
        TimeSeries result = ts1.dividedBy(ts2);

        // 3. Assert (断言/验证阶段)
        // 验证结果的大小
        assertThat(result.size()).isEqualTo(3);

        // 验证计算结果，使用 isWithin() 来比较 double
        assertThat(result.get(2000)).isWithin(1E-10).of(5.0); // 10.0 / 2.0
        assertThat(result.get(2001)).isWithin(1E-10).of(4.0); // 20.0 / 5.0
        assertThat(result.get(2002)).isWithin(1E-10).of(5.0); // 50.0 / 10.0

        // 验证多余的年份 2003 没有出现在结果中
        assertThat(result.containsKey(2003)).isFalse();
    }

    @Test
    public void testDividedByMissingYearInDivisor() {
        // 1. Arrange
        TimeSeries ts1 = new TimeSeries(); // 分子
        ts1.put(2000, 10.0);
        ts1.put(2001, 20.0); // <-- 2001 年在分子中有，但在分母中没有

        TimeSeries ts2 = new TimeSeries(); // 分母
        ts2.put(2000, 2.0);

        // 2. Act & 3. Assert
        // JUnit 5 的 assertThrows 写法
        assertThrows(IllegalArgumentException.class, () -> {
            ts1.dividedBy(ts2);
        });

        // 如果你用的是 JUnit 4 (CS61B 早期 skeleton 可能用)，语法是这样的：
        // @Test(expected = IllegalArgumentException.class)
        // public void testDividedByMissingYearInDivisor() {
        //     ... (Arrange) ...
        //     ts1.dividedBy(ts2); // 如果这行代码抛出了指定的异常，测试就通过
        // }
    }

    @Test
    public void testDividedByOnEmpty() {
        TimeSeries ts1 = new TimeSeries(); // 空的分子
        TimeSeries ts2 = new TimeSeries();
        ts2.put(2000, 10.0);

        TimeSeries result = ts1.dividedBy(ts2);

        // 结果应该是一个空的 TimeSeries
        assertThat(result.isEmpty()).isTrue();
        assertThat(result.size()).isEqualTo(0);
    }
} 