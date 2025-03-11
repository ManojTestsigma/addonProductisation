package scripts.addonProductisation;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
        import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class ReadCellValue {

//    private WebDriver driver;
//
//    public ExcelCellReader(WebDriver driver) {
//        this.driver = driver;
//    }

    public String readCellValue(String filePath, String rowIndex, String columnIdentifier) {
        try {
            File file = new File(filePath);
            if (!file.exists()) {
                throw new IOException("File could not be found. Please check if the file is available at the specified path.");
            }

            FileInputStream inputStream = new FileInputStream(file);

            Workbook workbook;
            if (filePath.endsWith(".xlsx")) {
                workbook = new XSSFWorkbook(inputStream);
            } else if (filePath.endsWith(".xls")) {
                workbook = new HSSFWorkbook(inputStream);
            } else {
                throw new IllegalArgumentException("Unsupported file format. Only .xls and .xlsx are allowed.");
            }

            Sheet sheet = workbook.getSheetAt(0); // Read the first sheet

            int rowNum = Integer.parseInt(rowIndex);
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                throw new IllegalArgumentException("Row index out of bounds.");
            }

            Cell cell;
            if (columnIdentifier.matches("\\d+")) {
                // If column is given as an index
                int colNum = Integer.parseInt(columnIdentifier);
                cell = row.getCell(colNum);
            } else {
                // If column is given as a name (e.g., "A", "B")
                int colNum = columnNameToIndex(columnIdentifier);
                cell = row.getCell(colNum);
            }

            if (cell == null) {
                throw new IllegalArgumentException("Cell is empty or does not exist.");
            }

            return getCellValue(cell);

        } catch (Exception e) {
            System.err.println("Operation could not be completed due to this exception: " + e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private int columnNameToIndex(String columnName) {
        int index = 0;
        for (int i = 0; i < columnName.length(); i++) {
            index = index * 26 + (columnName.charAt(i) - 'A' + 1);
        }
        return index - 1; // Zero-based index
    }

    private String getCellValue(Cell cell) {
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString();
                } else {
                    return String.valueOf(cell.getNumericCellValue());
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula();
            case BLANK:
                return "";
            default:
                return "N/A";
        }
    }

    public static void main(String[] args) {

        ReadCellValue reader = new ReadCellValue();

        // replace with your file directory
        String filePath = "/Users/manojkumar.darshankar/Documents/suggestion sheet.xlsx";
        String rowIndex = "1"; // Row index (zero-based)
        String columnIdentifier = "B"; // Column index (e.g., "1" or "A")

        String cellValue = reader.readCellValue(filePath, rowIndex, columnIdentifier);

        if (cellValue != null) {
            System.out.println("Successfully read the file. The data stored is: " + cellValue);
        }
    }
}