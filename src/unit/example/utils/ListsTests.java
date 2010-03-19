package example.utils;

import org.junit.Test;

import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.hamcrest.Matchers.startsWith;
import static org.junit.Assert.assertThat;

public class ListsTests {

    @Test
    public void shouldCreateListWithExpectedContents() {
        List<String> list = Lists.create("one", "two");
        assertThat(list.size(), is(2));
        assertThat(list.get(0), is("one"));
        assertThat(list.get(1), is("two"));
    }

    @Test
    public void shouldReturnNullForEmptyList() {
        assertThat(Lists.first(Collections.emptyList()), nullValue());
    }

    @Test
    public void shouldReturnDefaultValueForEmptyList() {
        List<String> list = Collections.emptyList();
        assertThat(Lists.first(list, "foo"), is("foo"));
    }

    @Test
    public void shouldCorrectlyDetermineThatAListIsEmpty() {
        List<String> list = Collections.emptyList();
        assertThat(Lists.isEmpty(list), is(true));
        assertThat(Lists.isNotEmpty(list), is(false));
    }

    @Test
    public void shouldNotThrowExceptionsWhenAskedIfNullListIsEmpty() {
        assertThat(Lists.isEmpty(null), is(true));
        assertThat(Lists.isNotEmpty(null), is(false));
    }

    @Test
    public void shouldPassEachItemIntoClosure() {
        List<StringBuilder> list = Lists.create(new StringBuilder(), new StringBuilder());
        Lists.each(list, new Closure<StringBuilder>() {
            public void execute(StringBuilder item) {
                item.append("test");
            }
        });
        assertThat(list.get(0).toString(), is("test"));
        assertThat(list.get(1).toString(), is("test"));
    }

    @Test
    public void shouldCorrectlyEvaluateWhetherListContainsItem() {
        List<String> list = Lists.create("one", "two");
        assertThat(Lists.contains(list, is("two")), is(true));
        assertThat(Lists.contains(list, is("three")), is(false));
    }

    @Test
    public void shouldCountMatchesCorrectly() {
        List<String> list = Lists.create("one", "two");
        assertThat(Lists.count(list, is("one")), is(1));
        assertThat(Lists.count(list, anyOf(is("one"), is("two"))), is(2));
    }

    @Test
    public void shouldFindFirstMatchingItem() {
        List<String> list = Lists.create("b1", "b2");
        assertThat(Lists.find(list, startsWith("b")), is("b1"));
    }

    @Test
    public void shouldSelectAllMatchingItems() {
        List<String> list = Lists.create("b1", "b2", "test");
        assertThat(Lists.select(list, startsWith("b")), is(Lists.create("b1", "b2")));
    }

    @Test
    public void shouldRejectAllMatchingItems() {
        List<String> list = Lists.create("b1", "b2", "test");
        assertThat(Lists.reject(list, startsWith("b")), is(Lists.create("test")));
    }

    @Test
    public void shouldMapEachItemThroughFunction() {
        List<String> list = Lists.create("1", "2");
        List<Integer> result = Lists.map(list, new Function<String, Integer>() {
            public Integer execute(String item) {
                return Integer.parseInt(item);
            }
        });
        assertThat(result, is(Lists.create(1, 2)));
    }

    @Test
    public void shouldPassAllItemsThroughReducer() {
        List<Integer> list = Lists.create(1, 2);
        int result = Lists.reduce(list, 3, new Reducer<Integer, Integer>() {
            public Integer reduce(Integer item, Integer previous) {
                return item + previous;
            }
        });
        assertThat(result, is(6));
    }
}
