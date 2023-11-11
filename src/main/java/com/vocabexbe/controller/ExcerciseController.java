package com.vocabexbe.controller;

import com.vocabexbe.entity.Excercise;
import com.vocabexbe.repository.ExcerciseRepository;
import org.apache.poi.ss.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("exercise")
public class ExcerciseController {

    @Autowired
    private ExcerciseRepository excerciseRepository;

    @GetMapping
    @CrossOrigin
    private ResponseEntity<?> getExcercise(){
        return ResponseEntity.ok(excerciseRepository.findAll());
    }

    @PostMapping
    @CrossOrigin
    private ResponseEntity<?> postExcercise(@RequestParam("file") MultipartFile file) throws IOException {
        excerciseRepository.deleteAll();
        List<Excercise> excerciseList = new ArrayList<>();
        try (InputStream inputStream = file.getInputStream()) {
            Workbook workbook = WorkbookFactory.create(inputStream);
            Sheet sheet = workbook.getSheetAt(0); // Lấy sheet đầu tiên

            for (Row row : sheet) {
                Excercise excercise= Excercise.builder()
                        .ipa(String.valueOf(row.getCell(1, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                        .word(String.valueOf(row.getCell(0, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                        .wordVi(String.valueOf(row.getCell(2, Row.MissingCellPolicy.CREATE_NULL_AS_BLANK)))
                        .build();
                if (isRowEmpty(row)) {
                    break;
                }
                excerciseList.add(excercise);
            }
            excerciseRepository.saveAll(excerciseList);
        } catch (Exception e) {

        }

        return ResponseEntity.ok("success");
    }

    private boolean isRowEmpty(Row row) {
        for (Cell cell : row) {
            if (cell.getCellType() != CellType.BLANK) {
                return false;
            }
        }
        return true;
    }
}
