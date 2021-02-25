package org.gorany.community.controller;

import lombok.extern.log4j.Log4j2;
import org.apache.coyote.Response;
import org.gorany.community.dto.UploadResultDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@Log4j2
public class UploadController {

    @Value("${org.gorany.upload.path}")
    private String uploadPath;

    @PostMapping(value = "/upload")
    public ResponseEntity<List<UploadResultDTO>> uploadFile(MultipartFile[] uploadFiles){

        List<UploadResultDTO> list = new ArrayList<>();

        for(MultipartFile file : uploadFiles){

            String originalName = file.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);

            log.info("File Name : " + fileName);

            //파일의 원래 이름을 구했으면
            //uuid, path, file로 구현하여 저장
            //년/월/일 디렉토리 구조 만들기

            String folderPath = makeFolder();

            String uuid = UUID.randomUUID().toString();
            //uploadPath + folderPath + uuid + "_" + filename

            String savefileName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(savefileName);

            try {
                boolean isImage = Files.probeContentType(savePath).startsWith("image");
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(savePath.toFile()));

                list.add(new UploadResultDTO(fileName, uuid, folderPath, isImage));
            } catch (IOException e) {
                e.printStackTrace();
            }

            /*try {
                file.transferTo(savePath);
            } catch (IOException e) {
                e.printStackTrace();
            }*/
        }
        return new ResponseEntity<>(list, HttpStatus.OK);
    }
    public String makeFolder(){

        String str = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy\\MM\\dd"));
        String folderPath = str.replace("\\", File.separator);

        File uploadFolder = new File(uploadPath, folderPath);

        if(!uploadFolder.exists())
            uploadFolder.mkdirs();

        return folderPath;
    }
}