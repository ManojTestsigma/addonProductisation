package scripts.addonProductisation;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;

public class WriteIntoCell {

    public void writeCellValue(String filePath, String rowIndex, String columnIdentifier, String value) {
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
            } else if (filePath.endsWith(".csv")) {
                writeCSVCellValue(filePath, rowIndex, columnIdentifier, value);
                return;
            } else {
                throw new IllegalArgumentException("Unsupported file format. Only .xls, .xlsx, and .csv are allowed.");
            }

            Sheet sheet = workbook.getSheetAt(0); // Write to the first sheet

            int rowNum = Integer.parseInt(rowIndex);
            Row row = sheet.getRow(rowNum);
            if (row == null) {
                row = sheet.createRow(rowNum);
            }

            Cell cell;
            if (columnIdentifier.matches("\\d+")) {
                // If column is given as an index
                int colNum = Integer.parseInt(columnIdentifier);
                cell = row.getCell(colNum);
                if (cell == null) {
                    cell = row.createCell(colNum);
                }
            } else {
                // If column is given as a name (e.g., "A", "B")
                int colNum = columnNameToIndex(columnIdentifier);
                cell = row.getCell(colNum);
                if (cell == null) {
                    cell = row.createCell(colNum);
                }
            }

            cell.setCellValue(value);

            inputStream.close();

            FileOutputStream outputStream = new FileOutputStream(file);
            workbook.write(outputStream);
            workbook.close();
            outputStream.close();

            System.out.println("Successfully wrote the value to the file.");

        } catch (Exception e) {
            System.err.println("Operation could not be completed due to this exception: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void writeCSVCellValue(String filePath, String rowIndex, String columnIdentifier, String value) throws IOException {
        File file = new File(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(file));
        StringBuilder content = new StringBuilder();
        String line;
        int rowNum = Integer.parseInt(rowIndex);
        int colNum = columnIdentifier.matches("\\d+") ? Integer.parseInt(columnIdentifier) : columnNameToIndex(columnIdentifier);

        int currentRow = 0;
        while ((line = reader.readLine()) != null) {
            if (currentRow == rowNum) {
                String[] cells = line.split(",");
                if (colNum >= cells.length) {
                    String[] newCells = new String[colNum + 1];
                    System.arraycopy(cells, 0, newCells, 0, cells.length);
                    cells = newCells;
                }
                cells[colNum] = value;
                line = String.join(",", cells);
            }
            content.append(line).append("\n");
            currentRow++;
        }
        reader.close();

        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write(content.toString());
        writer.close();

        System.out.println("Successfully wrote the value to the CSV file.");
    }

    private int columnNameToIndex(String columnName) {
        int index = 0;
        for (int i = 0; i < columnName.length(); i++) {
            index = index * 26 + (columnName.charAt(i) - 'A' + 1);
        }
        return index - 1; // Zero-based index
    }

    public static void main(String[] args) {
        WriteIntoCell writer = new WriteIntoCell();

        // replace with your file directory
        String filePath = "/Users/manojkumar.darshankar/Documents/suggestion sheet.xlsx";
        String rowIndex = "1"; // Row index (zero-based)
        String columnIdentifier = "B"; // Column index (e.g., "1" or "A")
        String value = "New Value";

        writer.writeCellValue(filePath, rowIndex, columnIdentifier, value);
    }
}