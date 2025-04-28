package com.spectrun.spectrum.controllers.fileUpload;

import com.spectrun.spectrum.DTO.ModuleDto;
import com.spectrun.spectrum.services.Implementations.ModuleService;
import com.spectrun.spectrum.services.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/api/v1/module")
public class FileUpload {
private final StorageService storageService;
private final ModuleService moduleService;
@Autowired
    public FileUpload(StorageService storageService, ModuleService moduleService) {
        this.storageService = storageService;
        this.moduleService = moduleService;
    }


    @PostMapping("/new")
    ResponseEntity<?> uploadModule(@RequestParam("file") MultipartFile file
                                   ){
        String filePath = storageService.store(file);
        HashMap<String,String> filePathResponse = new HashMap<String,String>();
        filePathResponse.put("path",filePath);
        return new ResponseEntity<>(filePathResponse,HttpStatus.OK);
    }
    @PostMapping("/newModule")
    ResponseEntity<?> uploadModule(@RequestBody ModuleDto module){
        ModuleDto newModule = this.moduleService.addNewmodule(module);
        return new ResponseEntity<>(newModule,HttpStatus.OK);
    }

}
