package example.utils;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MapsTests {

    @Test
    public void shouldFindKeyForValue() {
        Pair<String, Integer> one = Pair.create("one", 1);
        Pair<String, Integer> two = Pair.create("two", 2);
        Pair<String, Integer> three = Pair.create("three", 3);

        Map<String, Integer> map = Maps.create(one, two, three);

        assertThat(Maps.findKeyForValue(map, 2), is("two"));
    }

    @Test
    public void shouldCreateListOfPairsFromMap() {
        Pair<String, Integer> one = Pair.create("one", 1);
        Pair<String, Integer> two = Pair.create("two", 2);
        Pair<String, Integer> three = Pair.create("three", 3);

        Map<String, Integer> map = Maps.createLinked(one, two, three);

        List<Pair<String, Integer>> list = Maps.asList(map);

        assertThat(list, is(Arrays.asList(one, two, three)));
    }
}
