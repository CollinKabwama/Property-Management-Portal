package com.propertymanagement.portal.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@RestController
@RequestMapping("/api/files")
public class FileUploadController {

   // private final Path rootLocation = Paths.get("src/main/resources");
    //private final Path rootLocation = Paths.get("/media/collin/New Volume/Moved folders/Graduate school/WAA/PropertyManagementPortal-Front/mini-property-management-frontend-ug/src/assets/images");
    private final Path rootLocation = Paths.get("../../mini-property-management-frontend-ug/src/assets/images");

    //private final String rootLocation = "src/main/resources";
    @PreAuthorize("hasAuthority('OWNER')")
    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            // Ensure the upload directory exists
            Files.createDirectories(rootLocation);

            // Copy the file to the target location (Replacing existing file with the same name)
            Path targetLocation = rootLocation.resolve(file.getOriginalFilename());
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return ResponseEntity.ok("File uploaded successfully: " + file.getOriginalFilename());
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Could not upload the file: " + file.getOriginalFilename());
        }
    }
}

