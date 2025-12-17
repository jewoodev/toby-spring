package tobyspring.hellospring.adapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.util.stream.Collectors;

public class SimpleApiExecuter implements ApiExecuter {
    @Override
    public String execute(URI uri) {
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) uri.toURL().openConnection();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String response;
        try (var br = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
            response = br.lines().collect(Collectors.joining());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return response;
    }
}
