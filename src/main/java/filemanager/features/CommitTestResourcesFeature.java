package filemanager.features;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommitTestResourcesFeature {

    @Autowired
    private DownloadTestResourcesFeature downloadTestResourcesFeature;

    public void createBranchInTestResourcesRepo() {

    }

    public void cleanClonedTempResourcesDirElseFail() {

    }

    public void cloneResourcesGitRepoElseFail() {

    }

    public void moveTestResourcesFromCurrentRepoToTestFilesRepo() {
    }
}
