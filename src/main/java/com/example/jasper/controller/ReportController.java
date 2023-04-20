package com.example.jasper.controller;

import com.example.jasper.service.ReportService;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.JRException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.IOException;

@RestController
@RequestMapping("/api/v1/report")
public class ReportController {
    @Autowired
    ReportService reportService;

    @GetMapping("/csv")
    public void generateReport(HttpServletResponse response, @RequestParam(name = "type") String type) throws JRException, IOException {
        reportService.generateCsvReport(response);
    }

    @GetMapping("/csv/bytes")
    public ResponseEntity<byte []> generateReportByteCsv( @RequestParam(name = "type") String type) throws JRException, IOException {
        return reportService.generateCsvReportByte();
    }

    @GetMapping("/pdf")
    public ResponseEntity<byte []> generateReport( @RequestParam(name = "type") String type) throws JRException, IOException {
        return reportService.generatePdfReport();
    }
}
