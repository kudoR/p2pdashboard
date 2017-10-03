package eu.ffs.job.watcher;

import eu.ffs.job.importData.AbstractImportJobConfig;
import eu.ffs.job.importData.MintosImportJobConfig;
import eu.ffs.job.importData.TwinoImportJobConfig;
import eu.ffs.job.importData.ViventorImportJobConfig;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

import static eu.ffs.Constants.*;
import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

@Service
public class FileWatcher {

    @Autowired
    TwinoImportJobConfig twinoImportJobConfig;

    @Autowired
    ViventorImportJobConfig viventorImportJobConfig;

    @Autowired
    MintosImportJobConfig mintosImportJobConfig;

    @Scheduled(fixedRate = 5000)
    public void perform() {
        File sourceDir = new File("/etc/shared/");
        File failedDir = new File(sourceDir.getPath() + "/failed");
        failedDir.mkdirs();
        File completedDir = new File(sourceDir.getPath() + "/completed");
        completedDir.mkdirs();
        System.out.println("watching directory for exports: " + sourceDir.getPath());
        this.initJobImport(sourceDir, failedDir, completedDir, twinoImportJobConfig, TWINO_EXPORT_FILENAME_PATTERN);
        this.initJobImport(sourceDir, failedDir, completedDir, viventorImportJobConfig, VIVENTOR_EXPORT_FILENAME_PATTERN);
        this.initJobImport(sourceDir, failedDir, completedDir, mintosImportJobConfig, MINTOS_EXPORT_FILENAME_PATTERN);
    }

    private void initJobImport(File f, File failedDir, File completedDir, AbstractImportJobConfig importJob, String fileNamePattern) {

        FilenameFilter textFilter = (dir, name) -> name.toLowerCase().startsWith(fileNamePattern);

        File[] files = f.listFiles(textFilter);
        if (files != null && files.length > 0) {
            System.out.println("Matching files: " + files);
            Stream.of(files).forEach(file -> {
                try {
                    JobExecution jobExecution = importJob.perform(file.getPath());
                    moveFile(file.toPath(), failedDir.toPath(), completedDir.toPath(), jobExecution.getExitStatus());
                } catch (JobParametersInvalidException e) {
                    e.printStackTrace();
                } catch (JobExecutionAlreadyRunningException e) {
                    e.printStackTrace();
                } catch (JobRestartException e) {
                    e.printStackTrace();
                } catch (JobInstanceAlreadyCompleteException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private void moveFile(Path path, Path failedDir, Path completedDir, ExitStatus exitStatus) throws IOException {
        if (exitStatus.equals(ExitStatus.COMPLETED)) {
            // move to processed
            System.out.println("Successfully processed file: " + path.getFileName());
            Files.move(path, completedDir.resolve(path.getFileName()), REPLACE_EXISTING);
        }
        if (exitStatus.equals(ExitStatus.FAILED)) {
            // move to failed
            System.out.println("Failed processed file: " + path.getFileName());
            Files.move(path, failedDir.resolve(path.getFileName()), REPLACE_EXISTING);
        }
    }

}
