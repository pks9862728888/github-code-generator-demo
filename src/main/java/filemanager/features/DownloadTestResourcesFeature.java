package filemanager.features;

import filemanager.CommandRunner;
import filemanager.exceptions.ProcessRunnerException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class DownloadTestResourcesFeature {

    @Value("${mvn.command}")
    private String mavenCommand;

    private String CLEAN_TEMP_RESOURCES_DIR = " clean -PcleanTempTestResources";

    @PostConstruct
    void init() {
        this.CLEAN_TEMP_RESOURCES_DIR = mavenCommand + CLEAN_TEMP_RESOURCES_DIR;
    }

    public void cleanTempDirElseFail() {
        try {
            CommandRunner.runCommand(CLEAN_TEMP_RESOURCES_DIR, true);
        } catch (ProcessRunnerException e) {
            System.out.println(e.getMessage());
            System.out.println("Exiting");
            System.exit(-1);
        }
    }

}
