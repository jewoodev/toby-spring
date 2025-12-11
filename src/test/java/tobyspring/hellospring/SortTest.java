package tobyspring.hellospring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class SortTest {
    private Sort sort;

    @BeforeEach
    void setUp() {
        sort = new Sort();
        System.out.println(this);
    }

    @Test
    void sort() {
        List<String> list = sort.lengthSort(Arrays.asList("aa", "b"));

        assertThat(list).isEqualTo(List.of("aa", "b"));
    }


    @Test
    void sort3Element() {
        List<String> list = sort.lengthSort(Arrays.asList("aa", "ccc", "b"));

        assertThat(list).isEqualTo(List.of("ccc", "aa", "b"));
    }

    @Test
    void sortAlreadySorted() {
        List<String> list = sort.lengthSort(Arrays.asList("b", "aa", "ccc"));

        assertThat(list).isEqualTo(List.of("ccc", "aa", "b"));
    }
}