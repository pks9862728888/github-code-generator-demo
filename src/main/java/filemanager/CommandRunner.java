package filemanager;

import filemanager.exceptions.ProcessRunnerException;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public interface CommandRunner {

    static void runCommand(@NonNull String command, boolean printLogs) throws ProcessRunnerException {
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

            if (printLogs) {
                System.out.println("Running command: " + command);
                try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    System.out.println(br.readLine());
                }
                System.out.println("Status: Success!");
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProcessRunnerException(String.format("Exception occurred while running command: %s Exception: %s",
                    command, e.getMessage()));
        }
    }
}
