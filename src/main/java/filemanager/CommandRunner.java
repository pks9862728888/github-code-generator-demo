package filemanager;

import filemanager.exceptions.ProcessRunnerException;
import lombok.NonNull;

import java.io.BufferedReader;
import java.io.InputStreamReader;

public interface CommandRunner {

    static void runCommand(@NonNull String command, boolean printLogs) throws ProcessRunnerException {
        System.out.println("Running command: " + command);
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();

            if (printLogs) {
                // Success stream
                try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                    String line = br.readLine();
                    if (line != null) {
                        System.out.println(line);
                    }
                }

                // Error stream
                try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                    String line = br.readLine();
                    if (line != null) {
                        System.out.println(line);
                    }
                }

                if (process.exitValue() == 0) {
                    System.out.println("Command runner Status: Success!");
                } else {
                    throw new ProcessRunnerException("Command runner Status: Failed! Exit Code: " + process.exitValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProcessRunnerException(String.format("Exception occurred while running command: %s Exception: %s",
                    command, e.getMessage()));
        }
    }
}
