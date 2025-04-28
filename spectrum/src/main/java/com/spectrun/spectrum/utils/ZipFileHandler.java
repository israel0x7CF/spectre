package com.spectrun.spectrum.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

public class ZipFileHandler {

    public static String unzipFile(MultipartFile zipFile,String moduledir) throws IOException {
        Path outputPath = Paths.get(moduledir);
        if (!Files.exists(outputPath)) {
            Files.createDirectories(outputPath);
        }
        try(ZipInputStream zis = new ZipInputStream(zipFile.getInputStream())){
            ZipEntry entry;
            while((entry = zis.getNextEntry()) != null){
                Path filePath =outputPath.resolve(entry.getName());
                if(entry.isDirectory()){
                    Files.createDirectories(filePath);

                }
                else{
                    Files.createDirectories(filePath.getParent());
                    try (FileOutputStream fos = new FileOutputStream(filePath.toFile())) {
                        byte[] buffer = new byte[1024];
                        int length;
                        while ((length = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, length);
                        }
                    }
                }
                zis.closeEntry();
            }

        }
        return  outputPath.toString();
    }
}
