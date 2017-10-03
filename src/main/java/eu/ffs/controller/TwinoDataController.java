package eu.ffs.controller;

import eu.ffs.repository.TwinoAccountEntryRepository;
import eu.ffs.repository.entity.TwinoAccountEntry;
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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@RestController
public class TwinoDataController extends BaseDataController<TwinoAccountEntry> {

    @Autowired
    TwinoAccountEntryRepository repository;

    @RequestMapping("/getAll")
    public Iterable<TwinoAccountEntry> getAll() {
        return repository.findAll();
    }

    @RequestMapping("/getByType")
    public List<TwinoAccountEntry> getByType(@RequestParam("type") String type) {
        return repository.findByPkType(type);
    }

    @RequestMapping("/getByDescription")
    public List<TwinoAccountEntry> getByDescription(@RequestParam("description") String description) {
        return repository.findByPkDescription(description);
    }


    @RequestMapping("/getAggregatedAmountByDescription")
    public AggregatedAmountResponse getAggregatedAmountByDescription(@RequestParam("description") String description) {
        AggregatedAmountResponse response = new AggregatedAmountResponse();

        List<TwinoAccountEntry> byDescription = this.getByDescription(description);

        // sum
        BigDecimal sum = BigDecimal.ZERO;
        Long minBookingDate = Long.MAX_VALUE;
        Long maxBookingDate = Long.MIN_VALUE;

        for (TwinoAccountEntry entry : byDescription) {
            sum = sum.add(entry.getAmount());
            minBookingDate = Math.min(minBookingDate, Long.parseLong(entry.getBookingDate()));
            maxBookingDate = Math.max(maxBookingDate, Long.parseLong(entry.getBookingDate()));
        }

        response.setAggregatedAmount(String.valueOf(sum));
        response.setMinBookingDate(new Date(minBookingDate).toLocaleString());
        response.setMaxBookingDate(new Date(maxBookingDate).toLocaleString());

        return response;
    }

    @RequestMapping("/getAggregatedAmountByType")
    public AggregatedAmountResponse getAggregatedAmountByType(@RequestParam("type") String type) {
        AggregatedAmountResponse response = new AggregatedAmountResponse();
        List<TwinoAccountEntry> byType = this.getByType(type);

        // sum
        BigDecimal sum = BigDecimal.ZERO;
        for (TwinoAccountEntry entry : byType) {
            sum = sum.add(entry.getAmount());
        }
        response.setAggregatedAmount(String.valueOf(sum));
        return response;
    }

    @RequestMapping("/getTwinoReport")
    public Report getReport() {
        return getReportFromRepo(this.repository);
    }



    @PostMapping("/uploadTwino")
    public ModelAndView handleTwinoFileUpload(@RequestParam("file") MultipartFile file) throws Exception {

        PoiItemReader<TwinoAccountEntry> reader = new PoiItemReader<>();

        byte[] bytes = file.getBytes();
        ByteArrayResource byteArrayResource = new ByteArrayResource(bytes);

        reader.setResource(byteArrayResource);
        reader.setRowMapper(genericRowMapper());
        reader.setLinesToSkip(1);

        ExecutionContext ctx = new ExecutionContext();

        reader.open(ctx);


        for (TwinoAccountEntry read = reader.read(); read != null; read = reader.read()) {
            repository.save(read);
        }

        return new ModelAndView("redirect:/");
    }


    @Override
    public Class getTargetType() {
        return TwinoAccountEntry.class;
    }
}