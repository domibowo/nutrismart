package com.enigma.nutrismartbe.util;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

public interface FileUtility {

    public String store(MultipartFile file, String destination) throws IOException;
    public Resource read(String file) throws MalformedURLException;
}
