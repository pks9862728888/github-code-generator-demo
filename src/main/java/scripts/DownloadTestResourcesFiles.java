package scripts;

import filemanager.features.DownloadTestResourcesFeature;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "filemanager.*")
public class DownloadTestResourcesFiles implements CommandLineRunner {

    @Autowired
    private DownloadTestResourcesFeature feature;

    public static void main(String[] args) {
        SpringApplication.run(DownloadTestResourcesFiles.class, args);
    }

    @Override
    public void run(String... args) {
        // Clean temp dir
        feature.cleanTempResourcesDirElseFail();

        // Clone resources git repo
        feature.cloneResourcesGitRepoElseFail();

        // Move downloaded test-resources to our repo
        feature.moveTestResourcesToCurrentRepo();

        // Exit when complete
        System.out.println("Downloaded test resources file into current repo successfully!");
        System.exit(0);
    }
}
