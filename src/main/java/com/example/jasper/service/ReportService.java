package com.example.jasper.service;
import com.example.jasper.entity.UserEntity;
import com.example.jasper.repository.UserRepo;
import jakarta.servlet.http.HttpServletResponse;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.export.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@Service
public class ReportService {

    @Autowired
    UserRepo userRepo;

    public ResponseEntity<byte []> generatePdfReport() throws IOException, JRException {

        List<UserEntity> dataList = userRepo.findAll();

        JRBeanCollectionDataSource beenCollectionDataSource = new JRBeanCollectionDataSource(dataList);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/user.jrxml"));
        HashMap<String, Object> map = new HashMap<>();
        map.put("reportname", "user report");
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beenCollectionDataSource);

        byte[] data = JasperExportManager.exportReportToPdf(report);
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=users.pdf");

        return ResponseEntity.ok().headers(headers).contentType(MediaType.APPLICATION_PDF).body(data);
    }

    public void generateCsvReport(HttpServletResponse response) throws IOException, JRException {
        List<UserEntity> dataList = userRepo.findAll();

        JRBeanCollectionDataSource beenCollectionDataSource = new JRBeanCollectionDataSource(dataList);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/user.jrxml"));
        HashMap<String, Object> map = new HashMap<>();
        map.put("reportname", "user report");
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beenCollectionDataSource);

        // your data source, report compilation and filling code...

        JRCsvExporter exporter = new JRCsvExporter();
        SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(report));
        // With the following line
        exporter.setExporterOutput(new SimpleWriterExporterOutput(response.getWriter()));
        // Headers
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition","attachment; filename="+"Export.csv");
        exporter.exportReport();
    }

    public ResponseEntity<byte []> generateCsvReportByte() throws IOException, JRException {
        List<UserEntity> dataList = userRepo.findAll();

        JRBeanCollectionDataSource beenCollectionDataSource = new JRBeanCollectionDataSource(dataList);
        JasperReport compileReport = JasperCompileManager.compileReport(new FileInputStream("src/main/resources/user.jrxml"));
        HashMap<String, Object> map = new HashMap<>();
        map.put("reportname", "user report");
        JasperPrint report = JasperFillManager.fillReport(compileReport, map, beenCollectionDataSource);

        // your data source, report compilation and filling code...

        JRCsvExporter exporter = new JRCsvExporter();
        SimpleCsvExporterConfiguration configuration = new SimpleCsvExporterConfiguration();
        exporter.setConfiguration(configuration);
        exporter.setExporterInput(new SimpleExporterInput(report));
        // Export the report to a ByteArrayOutputStream
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        SimpleWriterExporterOutput exporterOutput  = new SimpleWriterExporterOutput(outputStream);
        // With the following line
        exporter.setExporterOutput(exporterOutput );
        // Headers
        exporter.exportReport();

        // Get the CSV data as a byte array
        byte[] bytes = outputStream.toByteArray();

        // Create a ResponseEntity object and set its content type and length
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.TEXT_PLAIN);
        headers.setContentLength(bytes.length);

        // Set the file name in the response headers
        headers.set("Content-Disposition", "attachment; filename=output.csv");

        // Return the ResponseEntity object
        return ResponseEntity.ok().headers(headers).body(bytes);
    }

}
