package filemanager.features;

import filemanager.CommandRunner;
import filemanager.exceptions.ProcessRunnerException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.nio.file.StandardCopyOption;

@Component
@Slf4j
public class DownloadTestResourcesFeature {

    @Value("${mvn.command}")
    private String mavenCommand;

    @Value("${test.resources.repo.branch}")
    private String branchName;

    @Value("${test.resources.repo.url}")
    private String resourcesGitRepoUrl;

    @Value("${test.resources.repo.temp-clone-dir}")
    private String resourcesRepoTmpCloneDir;

    @Value("${test.resources.files.source-dir}")
    private String testResourcesFilesSourceDir;

    @Value("${test.resources.files.destination-dir}")
    private String testResourcesFilesDestinationDir;

    private String CLEAN_TEMP_RESOURCES_DIR_CMD = "%s clean -PcleanTempTestResources";
    private String CLONE_RESOURCES_GIT_REPO_CMD = "git clone --branch=%s %s %s";

    @PostConstruct
    void init() {
        this.CLEAN_TEMP_RESOURCES_DIR_CMD = String.format(CLEAN_TEMP_RESOURCES_DIR_CMD, mavenCommand);
        this.CLONE_RESOURCES_GIT_REPO_CMD = String.format(CLONE_RESOURCES_GIT_REPO_CMD,
                branchName, resourcesGitRepoUrl, resourcesRepoTmpCloneDir);
    }

    public void cleanTempResourcesDirElseFail() {
        try {
            log.debug("Running clean temp resources dir...");
            CommandRunner.runCommand(CLEAN_TEMP_RESOURCES_DIR_CMD, true);
        } catch (ProcessRunnerException e) {
            System.out.println(e.getMessage());
            System.out.println("Exiting");
            System.exit(-1);
        }
    }

    public void cloneResourcesGitRepoElseFail() {
        try {
            log.debug("Running clone resources git repo...");
            CommandRunner.runCommand(CLONE_RESOURCES_GIT_REPO_CMD, true);
        } catch (ProcessRunnerException e) {
            System.out.println(e.getMessage());
            System.out.println("Exiting");
            System.exit(-1);
        }
    }

    public void moveTestResourcesToCurrentRepo() {
        try {
            log.debug("Moving test resources to current repo...");
            FileUtils.copyDirectory(new File(testResourcesFilesSourceDir), new File(testResourcesFilesDestinationDir));
            log.debug("Test resources moving status: Success!");
        } catch (IOException e) {
            System.out.println("Exception occurred while moving test resources to current repo. Exception: " + e.getMessage());
            System.out.println("Exiting");
            System.exit(-1);
        }
    }
}
