package eu.ffs.controller;


import eu.ffs.repository.CentralRepositoryService;
import eu.ffs.repository.MintosAccountEntryRepository;
import eu.ffs.repository.entity.MintosAccountEntry;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.excel.poi.PoiItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class MintosDataController extends BaseDataController {

    @Autowired
    MintosAccountEntryRepository repository;

    @Autowired
    private CentralRepositoryService centralRepositoryService;

    @RequestMapping("/findAll/mintos")
    public Iterable<MintosAccountEntry> getAll() {
        return repository.findAll();
    }


    @PostMapping("/uploadMintos")
    public ModelAndView handleMintosFileUpload(@RequestParam("file") MultipartFile file) throws Exception {

        PoiItemReader<MintosAccountEntry> reader = new PoiItemReader<>();

        byte[] bytes = file.getBytes();
        ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);

        reader.setResource(byteArrayResource);
        reader.setRowMapper(genericRowMapper());
        reader.setLinesToSkip(1);

        reader.open(new ExecutionContext());

        for (MintosAccountEntry read = reader.read(); read != null; read = reader.read()) {
            centralRepositoryService.saveAccountEntry(read);
        }

        return new ModelAndView("redirect:/");
    }

    @RequestMapping("/getMintosReport")
    public Report getReport() {
        return getReportFromRepo(this.repository);
    }

    @Override
    public Class getTargetType() {
        return MintosAccountEntry.class;
    }
}
