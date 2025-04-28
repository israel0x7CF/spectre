package com.spectrun.spectrum.services.Implementations;
import com.spectrun.spectrum.config.Storage.StorageProperties;
import com.spectrun.spectrum.services.StorageService;
import com.spectrun.spectrum.utils.ZipFileHandler;
import com.spectrun.spectrum.utils.exceptions.StorageException;
import com.spectrun.spectrum.utils.exceptions.StorageFileNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.logging.Logger;
import java.util.stream.Stream;

@Service
public class Storage implements  StorageService {
    private Path fileLocation;
    Logger logger = Logger.getLogger(Storage.class.getName());

    @Autowired
    public Storage(StorageProperties storageProperties ){
        if(storageProperties.getLocation().trim().length() == 0){
            throw new StorageException("Upload Location Cannot Empty");
        }
        this.fileLocation = Paths.get(storageProperties.getLocation());

    }

    @Override
    public void init() {
        try {
            Files.createDirectories(fileLocation);
        }
        catch (IOException e) {
            throw new StorageException("Could not initialize storage", e);
        }
    }

    @Override
    public String store(MultipartFile file) {
        String filePath;
        try{
            filePath = ZipFileHandler.unzipFile(file,fileLocation.toString());
        }catch (Exception e){
            throw new StorageException("Failed to store file.", e);

        }
    return  filePath;
    }

    @Override
    public Stream<Path> loadAll() {
        try {
            return Files.walk(this.fileLocation, 1)
                    .filter(path -> !path.equals(this.fileLocation))
                    .map(this.fileLocation::relativize);
        }
        catch (IOException e) {
            throw new StorageException("Failed to read stored files", e);
        }
    }

    @Override
    public Path load(String filename) {
        return  this.fileLocation.resolve(filename);
    }

    @Override
    public Resource loadAsResource(String filename) {
        try {
            Path file = load(filename);
            Resource resource = new UrlResource(file.toUri());
            if (resource.exists() || resource.isReadable()) {
                return resource;
            }
            else {
                throw new StorageFileNotFoundException(
                        "Could not read file: " + filename);

            }
        }
        catch (MalformedURLException e) {
            throw new StorageFileNotFoundException("Could not read file: " + filename, e);
        }
    }

    @Override
    public void deleteAll() {
        FileSystemUtils.deleteRecursively(this.fileLocation.toFile());
    }
}
