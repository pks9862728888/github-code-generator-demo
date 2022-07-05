package scripts;

import filemanager.features.CommitTestResourcesFeature;
import filemanager.features.DownloadTestResourcesFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "filemanager.*")
public class CommitTestResources implements CommandLineRunner {

    @Autowired
    private CommitTestResourcesFeature feature;

    public static void main(String[] args) {
        SpringApplication.run(CommitTestResources.class, args);
    }

    @Override
    public void run(String... args) {
        // Check current repo branch
        String newBranchName = feature.getCurrentRepoBranchElseFail();

        // Create and checkout new branch in test-resources-repo
//        feature.createBranchInTestResourcesRepo();

        // Clone test-resources-repo
//        feature.cloneResourcesGitRepoElseFail();

        // Clean test-resources dir from test-resources repo
//        feature.cleanClonedTempResourcesDirElseFail();

        // Move test-resources from our repo to test-resources-repo
//        feature.moveTestResourcesFromCurrentRepoToTestFilesRepo();

        // Exit when complete
//        System.out.println("Test resources copied from current repo to test-resources repo. Please navigate to test-resources-repo and raise pull request!");
        System.exit(0);
    }
}
