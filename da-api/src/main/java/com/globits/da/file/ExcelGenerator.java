package com.globits.da.file;


import com.globits.da.dto.EmployeeDTO;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class ExcelGenerator {
    private List<EmployeeDTO> listEmployee;
    private XSSFWorkbook workbook;
    private XSSFSheet sheet;

    public ExcelGenerator(List<EmployeeDTO> listEmployee) {
        this.listEmployee = listEmployee;
        workbook = new XSSFWorkbook();
    }

    private void writeHeader() throws FileNotFoundException {
        sheet = workbook.createSheet("list-employee-database");
        Row row = sheet.createRow(0);
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        //font.setBold(true);
        font.setColor(XSSFFont.SS_SUB);
        font.setFamily(10);
        font.setFontHeight(14);
        style.setFont(font);
        createCell(row, 0, "ID", style);
        createCell(row, 1, "Code", style);
        createCell(row, 2, "Name", style);
        createCell(row, 3, "Email", style);
        createCell(row, 4, "Phone", style);
        createCell(row, 5, "Age", style);

    }

    private void createCell(Row row, int columnCount, Object cellValue, CellStyle style) {
        sheet.autoSizeColumn(columnCount);
        Cell cell = row.createCell(columnCount);
        if (cellValue instanceof Integer) {
            cell.setCellValue((Integer) cellValue);
        } else if (cellValue instanceof Long) {
            cell.setCellValue((Long) cellValue);
        } else if (cellValue instanceof String) {
            cell.setCellValue((String) cellValue);
        } else {
            cell.setCellValue((Boolean) cellValue);
        }
        cell.setCellStyle(style);
    }

    private void write() {
        int rowCount = 1;
        CellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setColor(XSSFFont.DEFAULT_FONT_COLOR);
        font.setFontHeight(12);
        style.setFont(font);

        Collections.sort(listEmployee, new Comparator<EmployeeDTO>() {
            @Override
            public int compare(EmployeeDTO o1, EmployeeDTO o2) {
                return o1.getName().substring(o1.getName().lastIndexOf(' '))
                        .compareTo(o2.getName().substring(o2.getName().lastIndexOf(' ')));
            }
        });
        for (EmployeeDTO record : listEmployee) {
            Row row = sheet.createRow(rowCount++);
            int columnCount = 0;
            createCell(row, columnCount++, record.getId(), style);
            createCell(row, columnCount++, record.getCode(), style);
            createCell(row, columnCount++, record.getName(), style);
            createCell(row, columnCount++, record.getEmail(), style);
            createCell(row, columnCount++, record.getPhone(), style);
            createCell(row, columnCount++, record.getAge(), style);

        }
        //ghi vào file trong lap top
        try (OutputStream os = Files.newOutputStream(Paths.get("E:\\BAI_TAP_LUYEN_TAP\\Thuc Tap Java OcenTech\\Bai Tap Lv 2\\test.xlsx"))) {
            workbook.write(os);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportExcelFile(HttpServletResponse response) throws IOException {
        writeHeader();
        write();
        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();

    }

//    public  void readFileExcel() throws IOException {
//       // File file = new File(String.valueOf(new FileInputStream("E:\\BAI_TAP_LUYEN_TAP\\Thuc Tap Java OcenTech\\Bai Tap Lv 2\\test.xlsx")));
//      FileInputStream fileInputStream = new FileInputStream("E:\\BAI_TAP_LUYEN_TAP\\Thuc Tap Java OcenTech\\Bai Tap Lv 2\\test.xlsx");
//      XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream);
//      XSSFSheet sheet = workbook.getSheetAt(0);
//      FormulaEvaluator formulaEvaluator = workbook.getCreationHelper().createFormulaEvaluator();
//
//      for(Row  row : sheet){
//          for (Cell cell : row){
//              switch (formulaEvaluator.evaluate(cell).getCellTypeEnum()){
//                  case cell.:
//
//              }
//          }


    //  }
    //   }
}
