package snippets;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class KnowCurrentBranch {

    public static void main(String[] args) {
        try {
            Process process = Runtime.getRuntime().exec("git rev-parse --abbrev-ref HEAD");
            process.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            System.out.println(reader.readLine());
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
