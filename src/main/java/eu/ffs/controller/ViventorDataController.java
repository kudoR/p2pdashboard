package eu.ffs.controller;


import eu.ffs.job.importData.AccountEntryItemReaderFactory;
import eu.ffs.repository.CentralRepositoryService;
import eu.ffs.repository.ViventorAccountEntryRepository;
import eu.ffs.repository.entity.ViventorAccountEntry;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.batch.item.excel.poi.ViventorAccountEntryItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ViventorDataController extends BaseDataController {

    @Autowired
    ViventorAccountEntryRepository repository;

    @Autowired
    private CentralRepositoryService centralRepositoryService;

    @RequestMapping("/findAll/viventor")
    public Iterable<ViventorAccountEntry> getAll() {
        return repository.findAll();
    }


    @PostMapping("/uploadViventor")
    public ModelAndView handleViventorFileUpload(@RequestParam("file") MultipartFile file) throws Exception {

        byte[] bytes = file.getBytes();
        ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);

        AccountEntryItemReaderFactory factory = new AccountEntryItemReaderFactory()
                .withAccountEntryItemReader(new ViventorAccountEntryItemReader())
                .withResource(byteArrayResource)
                .withTargetType(ViventorAccountEntry.class);

        ItemReader<ViventorAccountEntry> reader = factory.build();

        for (ViventorAccountEntry read = reader.read(); read != null; read = reader.read()) {
            centralRepositoryService.saveAccountEntry(read);
        }

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/getViventorReport")
    public Report getReport() {
        return getReportFromRepo(this.repository);
    }

    @Override
    public Class getTargetType() {
        return ViventorAccountEntry.class;
    }
}
