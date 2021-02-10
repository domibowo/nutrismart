package com.enigma.nutrismartbe.util.impl;

import com.enigma.nutrismartbe.util.FileUtility;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@Component
public class FileUtilityImp implements FileUtility {

    private final Path storageLocation = Paths.get("/service/uploads").toAbsolutePath().normalize();

    @Override
    public String store(MultipartFile file, String destination) throws IOException {
        Path target = storageLocation.resolve(destination);
        Files.copy(file.getInputStream(), target, StandardCopyOption.REPLACE_EXISTING);
        return destination;
    }

    @Override
    public Resource read(String file) throws MalformedURLException {
        String exceptionMessage = String.format("File %s not found", file);
        Path fileName = storageLocation.resolve(file).normalize();
        Resource resource = new UrlResource(fileName.toUri());

        if(!resource.exists()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, exceptionMessage);
        return resource;
    }
}
