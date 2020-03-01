package com.processor.fileprocessor.controller;

import com.processor.fileprocessor.constants.FileType;
import com.processor.fileprocessor.dto.ProcessedDetails;
import com.processor.fileprocessor.service.FileProcessorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/processFile")
public class FileProcessorController {
    @Autowired
    private FileProcessorService fileProcessor;

    @RequestMapping(value = "/CSVFile", method = RequestMethod.POST)
    public ResponseEntity<ProcessedDetails> processCSVFile(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileProcessor.processAndReturnDetails(file, FileType.CSV), HttpStatus.OK);
    }

    @RequestMapping(value = "/XMLFile", method = RequestMethod.POST)
    public ResponseEntity<ProcessedDetails> processXMLFile(@RequestParam("file") MultipartFile file) throws IOException {
        return new ResponseEntity<>(fileProcessor.processAndReturnDetails(file, FileType.XML), HttpStatus.OK);
    }

    //TODO
    // test coverage with unitTests testing,
    // proper error handling, @ExceptionHandler
    // proper logging,
    // try to refactor looping of transactionList, i.e. loop runs for validation and summing and population after import, try to accomplish them at once and see performance
    // Store error message of Violation in Constants
    // Consider integrationTests testing in a scenario that multiple requests hit the same end point


}
