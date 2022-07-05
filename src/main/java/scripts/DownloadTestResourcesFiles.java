package scripts;

import filemanager.CommandRunner;
import filemanager.exceptions.ProcessRunnerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;

@SpringBootApplication
@ComponentScan(basePackages = "filemanager.*")
public class DownloadTestResourcesFiles implements CommandLineRunner {

    @Value("${mvn.command}")
    private String mavenCommand;

    private String CLEAN_TEMP_RESOURCES_DIR = " clean -PcleanTempTestResources";

    @PostConstruct
    void init() {
        this.CLEAN_TEMP_RESOURCES_DIR = mavenCommand + CLEAN_TEMP_RESOURCES_DIR;
    }

    public static void main(String[] args) {
        SpringApplication.run(DownloadTestResourcesFiles.class, args);
    }

    @Override
    public void run(String... args) {
        // Clean temp dir
        cleanTempDirElseFail();
    }

    private void cleanTempDirElseFail() {
        try {
            CommandRunner.runCommand(CLEAN_TEMP_RESOURCES_DIR, true);
        } catch (ProcessRunnerException e) {
            System.out.println(e.getMessage());
            System.out.println("Exiting");
            System.exit(-1);
        }
    }
}
