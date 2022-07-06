package com.demo.githubcodegeneratordemo.filemanager.features;

import com.demo.githubcodegeneratordemo.filemanager.CommandRunner;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

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

    private static final String GET_CURRENT_BRANCH_NAME_CMD = "git rev-parse --abbrev-ref HEAD";
    private String CLEAN_TEST_RESOURCES_REPO_DIR_CMD = "%s clean -PcleanTempClonedRepoDir";
    private String CLONE_TEST_RESOURCES_REPO_MASTER_BRANCH_CMD = "git clone %s %s";
    private String CREATE_NEW_LOCAL_BRANCH_CMD = "git -C %s branch %s";
    private String CHECKOUT_NEW_LOCAL_BRANCH_CMD = "git -C %s checkout %s";

    @PostConstruct
    void init() {
        CLEAN_TEST_RESOURCES_REPO_DIR_CMD = String.format(CLEAN_TEST_RESOURCES_REPO_DIR_CMD, mvnCommand);
        CLONE_TEST_RESOURCES_REPO_MASTER_BRANCH_CMD = String.format(CLONE_TEST_RESOURCES_REPO_MASTER_BRANCH_CMD,
                testResourcesRepoUrl, testResourcesRepoCloneDir);
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

    public void createBranchInTestResourcesRepoAndCheckout(@NonNull String branchName) {
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

    }

    public void cloneResourcesGitRepoElseFail() {

    }

    public void moveTestResourcesFromCurrentRepoToTestFilesRepo() {
    }
}
