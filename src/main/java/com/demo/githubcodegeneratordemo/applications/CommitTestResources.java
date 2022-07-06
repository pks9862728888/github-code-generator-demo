package com.demo.githubcodegeneratordemo.applications;

import com.demo.githubcodegeneratordemo.filemanager.features.CommitTestResourcesFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.demo.githubcodegeneratordemo.filemanager.*")
public class CommitTestResources {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(CommitTestResources.class, args);
        CommitTestResourcesFeature feature = context.getBean(CommitTestResourcesFeature.class);

        // Check current repo branch
        String newBranchName = feature.getCurrentRepoBranchElseFail();

        // Create and checkout new branch in test-resources-repo
        feature.cloneRepoCreateBranchInTestResourcesRepoAndCheckout(newBranchName);

        // Clean test-resources dir from test-resources repo
        feature.cleanClonedTempResourcesDirElseFail();

        // Move test-resources from our repo to test-resources-repo
        feature.copyTestResourcesFromCurrentRepoToTestFilesRepo();

        // Add all copied files to git
        feature.addTestResourcesToGit();

        // Exit when complete
        System.out.println("Test resources copied from main repo to test-resources repo. Please navigate to test-resources-repo and commit!");
        context.close();
    }
}
