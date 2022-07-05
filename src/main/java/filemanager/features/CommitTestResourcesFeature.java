package filemanager.features;

import filemanager.CommandRunner;
import filemanager.exceptions.ProcessRunnerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import static filemanager.features.DateTimeUtils.getCurrentZonedTimestamp;

@Component
@Slf4j
public class CommitTestResourcesFeature {

    @Value("${github.username}")
    private String githubUserName;

    @Value("${github.token}")
    private String githubPersonalAccessToken;

    private static final String GET_CURRENT_BRANCH_NAME_COMMAND = "git rev-parse --abbrev-ref HEAD";

    public String getCurrentRepoBranchElseFail() {
        String newBranchName = "";
        try {
            log.debug("Finding out current repo branch name...");
            newBranchName = CommandRunner.runCommandAndReturnOutput(GET_CURRENT_BRANCH_NAME_COMMAND);

            // Validate current branch name
            if (newBranchName.isEmpty()) {
                throw new ProcessRunnerException("Current repo branch name was not determined. Process returned null or blank output!");
            } else if (newBranchName.equals("master")) {
                newBranchName = githubUserName + "-commits-at-" + getCurrentZonedTimestamp("yyyyMMdd-hhmmss");
            }
            log.debug("New Branch name: " + newBranchName);
        } catch (ProcessRunnerException e) {
            e.printStackTrace();
            log.error(e.getMessage());
            log.error("Exiting...");
            System.exit(-1);
        }
        return newBranchName;
    }

    public void createBranchInTestResourcesRepo() {

    }

    public void cleanClonedTempResourcesDirElseFail() {

    }

    public void cloneResourcesGitRepoElseFail() {

    }

    public void moveTestResourcesFromCurrentRepoToTestFilesRepo() {
    }
}
