package com.demo.githubcodegeneratordemo.filemanager.features;

import com.demo.githubcodegeneratordemo.filemanager.CommandRunner;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

import java.nio.file.Paths;

import static com.demo.githubcodegeneratordemo.filemanager.features.DateTimeUtils.getCurrentZonedTimestamp;

@Component
@Slf4j
public class CommitTestResourcesFeature {

    @Value("${test.resources.repo.url}")
    private String testResourcesRepoUrl;

    @Value("${test.resources.repo.temp-clone-dir}")
    private String testResourcesRepoCloneDir;

    @Value("${command.mvn}")
    private String mvnCommand;

    @Value("${command.copy}")
    private String copyCommand;

    private static final String GET_CURRENT_BRANCH_NAME_CMD = "git rev-parse --abbrev-ref HEAD";
    private String CLEAN_TEST_RESOURCES_REPO_DIR_CMD = "%s clean -PcleanTempClonedRepoDir";
    private String CLEAN_TEST_RESOURCES_REPO_TEST_DATA_DIR_CMD = "%s clean -PcleanTestResourcesTestDataDir";
    private String CLONE_TEST_RESOURCES_REPO_MASTER_BRANCH_CMD = "git clone %s %s";
    private static final String CREATE_NEW_LOCAL_BRANCH_CMD = "git -C %s branch %s";
    private static final String CHECKOUT_NEW_LOCAL_BRANCH_CMD = "git -C %s checkout %s";
    private static final String COPY_DIRECTORY_CMD = "%s %s %s";
    private String ADD_FILES_TO_GIT_CMD = "git -C %s add .";

    private static final String TEST_DATA_RESOURCES_DIRECTORY_MAIN_REPO = "src/test/resources/test-data/";
    private String TEST_DATA_RESOURCES_DIRECTORY_TEST_RESOURCES_REPO = "%s%s";

    @PostConstruct
    void init() {
        CLEAN_TEST_RESOURCES_REPO_DIR_CMD = String.format(CLEAN_TEST_RESOURCES_REPO_DIR_CMD, mvnCommand);
        CLEAN_TEST_RESOURCES_REPO_TEST_DATA_DIR_CMD = String.format(CLEAN_TEST_RESOURCES_REPO_TEST_DATA_DIR_CMD, mvnCommand);
        CLONE_TEST_RESOURCES_REPO_MASTER_BRANCH_CMD = String.format(CLONE_TEST_RESOURCES_REPO_MASTER_BRANCH_CMD,
                testResourcesRepoUrl, testResourcesRepoCloneDir);
        this.TEST_DATA_RESOURCES_DIRECTORY_TEST_RESOURCES_REPO = String.format(TEST_DATA_RESOURCES_DIRECTORY_TEST_RESOURCES_REPO,
                testResourcesRepoCloneDir, TEST_DATA_RESOURCES_DIRECTORY_MAIN_REPO);
        this.ADD_FILES_TO_GIT_CMD = String.format(ADD_FILES_TO_GIT_CMD, testResourcesRepoCloneDir);
    }

    public String getCurrentRepoBranchElseFail() {
        log.debug("Finding out current repo branch name...");
        String newBranchName = CommandRunner.runCommandAndReturnOutputElseFail(
                GET_CURRENT_BRANCH_NAME_CMD, true);

        // Validate current branch name
        if (newBranchName.equals("master")) {
            newBranchName = "commit-at-" + getCurrentZonedTimestamp("yyyyMMdd-hhmmss");
        }
        log.debug("New Branch name: {}", newBranchName);

        return newBranchName;
    }

    public void cloneRepoCreateBranchInTestResourcesRepoAndCheckout(@NonNull String branchName) {
        // Clean repo if already cloned
        log.debug("Cleaning test-resources repo dir...");
        CommandRunner.runCommandElseFail(CLEAN_TEST_RESOURCES_REPO_DIR_CMD);

        // Clone the repo
        log.debug("Cloning test-resources repo...");
        CommandRunner.runCommandElseFail(CLONE_TEST_RESOURCES_REPO_MASTER_BRANCH_CMD);

        // Create new branch
        log.debug("Creating branch: {}", branchName);
        CommandRunner.runCommandElseFail(String.format(CREATE_NEW_LOCAL_BRANCH_CMD,
                testResourcesRepoCloneDir, branchName));

        // Checkout to newly created branch
        log.debug("Checkout branch: {}", branchName);
        CommandRunner.runCommandElseFail(String.format(CHECKOUT_NEW_LOCAL_BRANCH_CMD,
                testResourcesRepoCloneDir, branchName));
    }

    public void cleanClonedTempResourcesDirElseFail() {
        log.debug("Clean test-data dir from test-resources repo...");
        CommandRunner.runCommandElseFail(CLEAN_TEST_RESOURCES_REPO_TEST_DATA_DIR_CMD);
    }

    public void copyTestResourcesFromCurrentRepoToTestFilesRepo() {
        String currentDir = System.getProperty("user.dir");

        // Copy files
        log.debug("Copy test-data dir from main-repo to test-resources repo...");
        CommandRunner.runCommandElseFail(String.format(COPY_DIRECTORY_CMD,
                copyCommand,
                Paths.get(currentDir, TEST_DATA_RESOURCES_DIRECTORY_MAIN_REPO),
                Paths.get(currentDir, TEST_DATA_RESOURCES_DIRECTORY_TEST_RESOURCES_REPO)));
    }

    public void addTestResourcesToGit() {
        log.debug("Add copied files to git...");
        CommandRunner.runCommandElseFail(ADD_FILES_TO_GIT_CMD);
    }
}
