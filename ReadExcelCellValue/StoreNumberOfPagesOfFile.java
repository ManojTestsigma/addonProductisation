package scripts.addonProductisation;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.pdfbox.pdmodel.PDDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class StoreNumberOfPagesOfFile {

    private String filePath;
    private int numberOfPagesOrSheets;

    public StoreNumberOfPagesOfFile(String filePath) {
        this.filePath = filePath;
    }

    public void execute() {
        try {
            if (filePath.endsWith(".xlsx")) {
                try (FileInputStream fis = new FileInputStream(new File(filePath))) {
                    Workbook workbook = new XSSFWorkbook(fis);
                    numberOfPagesOrSheets = workbook.getNumberOfSheets();
                }
            } else if (filePath.endsWith(".xls")) {
                try (FileInputStream fis = new FileInputStream(new File(filePath))) {
                    Workbook workbook = new HSSFWorkbook(fis);
                    numberOfPagesOrSheets = workbook.getNumberOfSheets();
                }
            } else if (filePath.endsWith(".pdf")) {
                System.out.println("uncomment this code to use pdfbox");
//                try (PDDocument document = PDDocument.load(new File(filePath))) {
//                    numberOfPagesOrSheets = document.getNumberOfPages();
//                }
            } else if (filePath.endsWith(".csv")) {
                // For CSV, we can consider it as a single sheet
                numberOfPagesOrSheets = 1;
            } else {
                throw new IllegalArgumentException("Unsupported file format. Only .xls, .xlsx, .pdf, and .csv are allowed.");
            }
            System.out.println("Successfully processed the file. The number of pages/sheets is: " + numberOfPagesOrSheets);
        } catch (IOException e) {
            System.err.println("File could not be found. Please check if the file is available at the testdata.");
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Operation could not be completed due to this exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public int getNumberOfPagesOrSheets() {
        return numberOfPagesOrSheets;
    }

    public static void main(String[] args) {
        // replace with your file directory
        String filePath = "/Users/manojkumar.darshankar/Documents/suggestion sheet.xlsx";
        StoreNumberOfPagesOfFile storeNumberOfPagesOfFile = new StoreNumberOfPagesOfFile(filePath);
        storeNumberOfPagesOfFile.execute();
    }
}

