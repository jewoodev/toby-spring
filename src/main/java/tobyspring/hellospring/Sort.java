package tobyspring.hellospring;

import java.util.List;

public class Sort {
    public List<String> lengthSort(List<String> args) {
        args.sort((o1, o2) -> o2.length() - o1.length());

        return args;
    }
}
