package eu.ffs.job.importData;


import eu.ffs.repository.entity.DashboardConfiguration;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.excel.mapping.BeanWrapperRowMapper;
import org.springframework.batch.item.excel.poi.AccountEntryItemReader;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import java.io.File;

public class AccountEntryItemReaderFactory {

    private AccountEntryItemReader reader;

    public AccountEntryItemReaderFactory() {
        this.reader = new AccountEntryItemReader();
        this.reader.setStrict(false);
        this.reader.setResource(new FileSystemResource(new File("")));
        this.reader.setLinesToSkip(1);
    }

    // warning: know how to use this!!!
    public AccountEntryItemReaderFactory withAccountEntryItemReader(AccountEntryItemReader accountEntryItemReader) {
        this.reader = accountEntryItemReader;
        // this is bad design, as it overwrites some other shit (in the ctor its ok, but here its bad. but im lazy)
        this.reader.setStrict(false);
        this.reader.setResource(new FileSystemResource(new File("")));
        this.reader.setLinesToSkip(1);
        return this;
    }

    public AccountEntryItemReaderFactory withLinesToSkip(int linesToSkip) {
        this.reader.setLinesToSkip(linesToSkip);
        this.reader.setSkippedRowsCallback(rs -> System.out.println("Skipping: " + StringUtils.arrayToCommaDelimitedString(rs.getCurrentRow())));
        return this;
    }

    public AccountEntryItemReaderFactory withInputPath(DashboardConfiguration inputPath) {
        if (inputPath != null) {
            File file = new File(inputPath.getStringValue());
            if (file.exists()) {
                this.reader.setResource(new FileSystemResource(file));
            }
        }
        return this;
    }

    public AccountEntryItemReaderFactory withResource(Resource resource) {
        this.reader.setResource(resource);
        return this;
    }

    public AccountEntryItemReaderFactory withInputPath(String inputPath) {
        if (inputPath != null) {
            File file = new File(inputPath);
            if (file.exists()) {
                this.reader.setResource(new FileSystemResource(file));
            }
        }
        return this;
    }

    public AccountEntryItemReaderFactory withTargetType(Class targetType) {
        BeanWrapperRowMapper beanWrapperRowMapper = new BeanWrapperRowMapper<>();
        beanWrapperRowMapper.setTargetType(targetType);
        this.reader.setRowMapper(beanWrapperRowMapper);
        return this;
    }

    public ItemReader build() {
        reader.open(new ExecutionContext());
        return reader;
    }
}
