package com.demo.githubcodegeneratordemo.filemanager;

import com.demo.githubcodegeneratordemo.filemanager.exceptions.ProcessRunnerException;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.InputStreamReader;

@Component
@Slf4j
public class CommandRunner {

    static void runCommand(@NonNull String command) throws ProcessRunnerException {
        log.debug("Running command: {}", command);
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
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
                log.debug("Command runner Status: Success!");
            } else {
                throw new ProcessRunnerException("Command runner Status: Failed! Exit Code: " + process.exitValue());
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProcessRunnerException(String.format("Exception occurred while running command: %s Exception: %s",
                    command, e.getMessage()));
        }
    }

    public static void runCommandElseFail(@NonNull String command) {
        try {
            runCommand(command);
        } catch (ProcessRunnerException e) {
            log.error(e.getMessage());
            log.error("Exiting...");
            System.exit(-1);
        }
    }

    public static String runCommandAndReturnOutput(@NonNull String command) throws ProcessRunnerException {
        log.debug("Running command: {}", command);
        try {
            Process process = Runtime.getRuntime().exec(command);
            process.waitFor();
            // Success stream
            StringBuilder output = new StringBuilder();
            try (BufferedReader br = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                String line = br.readLine();
                if (line != null) {
                    output.append(line);
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
                log.debug("Command runner Status: Success!");
            } else {
                throw new ProcessRunnerException("Command runner Status: Failed! Exit Code: " + process.exitValue());
            }
            return output.toString();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ProcessRunnerException(String.format("Exception occurred while running command: %s Exception: %s",
                    command, e.getMessage()));
        }
    }

    public static String runCommandAndReturnOutputElseFail(@NonNull String command, boolean validateEmptyResult) {
        try {
            String result = runCommandAndReturnOutput(command);

            if (validateEmptyResult && result.trim().isEmpty()) {
                throw new ProcessRunnerException("Empty result was returned by command: " + command);
            }

            return result;
        } catch (ProcessRunnerException e) {
            log.error(e.getMessage());
            log.error("Exiting...");
            System.exit(-1);
        }
        return StringUtils.EMPTY;
    }
}
