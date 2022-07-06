package com.demo.githubcodegeneratordemo.filemanager.features;

import com.demo.githubcodegeneratordemo.filemanager.CommandRunner;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;

@Component
@Slf4j
public class DownloadTestResourcesFeature {

    @Value("${command.mvn}")
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

    private String CLEAN_TEMP_RESOURCES_DIR_CMD = "%s clean -PcleanTempTestResourcesAndTempClonedRepoDir";
    private String CLEAN_TEST_RESOURCES_DIR_CMD = "%s clean -PcleanTempClonedRepoDir";
    private String CLONE_RESOURCES_GIT_REPO_CMD = "git clone --branch=%s %s %s";

    @PostConstruct
    void init() {
        this.CLEAN_TEMP_RESOURCES_DIR_CMD = String.format(CLEAN_TEMP_RESOURCES_DIR_CMD, mavenCommand);
        this.CLEAN_TEST_RESOURCES_DIR_CMD = String.format(CLEAN_TEST_RESOURCES_DIR_CMD, mavenCommand);
        this.CLONE_RESOURCES_GIT_REPO_CMD = String.format(CLONE_RESOURCES_GIT_REPO_CMD,
                branchName, resourcesGitRepoUrl, resourcesRepoTmpCloneDir);
    }

    public void cleanAllTempResourcesDirElseFail() {
        log.debug("Running clean temp resources dir...");
        CommandRunner.runCommandElseFail(CLEAN_TEMP_RESOURCES_DIR_CMD);
    }

    public void cloneResourcesGitRepoElseFail() {
        log.debug("Running clone resources git repo...");
        CommandRunner.runCommandElseFail(CLONE_RESOURCES_GIT_REPO_CMD);
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

    public void cleanClonedTestResourcesRepo() {
        log.debug("Clean cloned test-resources repo...");
        CommandRunner.runCommandElseFail(CLEAN_TEST_RESOURCES_DIR_CMD);
    }
}
