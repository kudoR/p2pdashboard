package org.springframework.batch.item.excel.poi;

import org.springframework.batch.item.excel.Sheet;

/**
 * customized because Viventor adds a header to its export so the reader fails
 */
public class ViventorAccountEntryItemReader extends AccountEntryItemReader {
    @Override
    protected Sheet getSheet(final int sheet) {
        org.apache.poi.ss.usermodel.Sheet sheetAt = this.workbook.getSheetAt(sheet);
        sheetAt.shiftRows(8, Math.max(16, sheetAt.getLastRowNum()), -8);

        return new PoiSheet(sheetAt);
    }
}
