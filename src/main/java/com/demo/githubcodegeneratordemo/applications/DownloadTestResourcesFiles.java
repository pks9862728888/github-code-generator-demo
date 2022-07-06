package com.demo.githubcodegeneratordemo.applications;

import com.demo.githubcodegeneratordemo.filemanager.features.DownloadTestResourcesFeature;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.demo.githubcodegeneratordemo.filemanager.*")
public class DownloadTestResourcesFiles {

    public static void main(String[] args) {
        ConfigurableApplicationContext context = SpringApplication.run(DownloadTestResourcesFiles.class, args);
        DownloadTestResourcesFeature feature = context.getBean(DownloadTestResourcesFeature.class);

        // Clean temp dir
        feature.cleanAllTempResourcesDirElseFail();

        // Clone resources git repo
        feature.cloneResourcesGitRepoElseFail();

        // Move downloaded test-resources to our repo
        feature.moveTestResourcesToCurrentRepo();

        // Clean test-resources repo
        feature.cleanClonedTestResourcesRepo();

        // Exit when complete
        System.out.println("Downloaded test resources files into main repo successfully!");
        context.close();
    }
}
