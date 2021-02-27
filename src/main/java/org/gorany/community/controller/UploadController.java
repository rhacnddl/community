package org.gorany.community.controller;

import lombok.extern.log4j.Log4j2;
import net.coobird.thumbnailator.Thumbnailator;
import org.apache.coyote.Response;
import org.gorany.community.dto.UploadDTO;
import org.gorany.community.dto.UploadResultDTO;
import org.hibernate.result.Output;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
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

/*    @GetMapping(value = "/download")
    public void download(String fileName, HttpServletResponse response, HttpServletRequest request){

        try {
            String originFileName = URLDecoder.decode(fileName, "UTF-8");
            String onlyFileName = originFileName.substring(originFileName.lastIndexOf("_") + 1);

            log.info("@UploadController, download " + originFileName);
            log.info("@UploadController, onlyFileName: " + onlyFileName);

            File file = new File(uploadPath, originFileName);

            if(file.exists()) {
                String agent = request.getHeader("User-Agent");
                log.info(agent);

                //브라우저별 한글파일 명 처리
                if(agent.contains("Trident"))//Internet Explore
                    onlyFileName = URLEncoder.encode(onlyFileName, "UTF-8").replaceAll("\\+", " ");
                else if(agent.contains("Edge")) //Micro Edge
                    onlyFileName = URLEncoder.encode(onlyFileName, "UTF-8");
                else //Chrome
                    onlyFileName = new String(onlyFileName.getBytes("UTF-8"), "ISO-8859-1");
                //브라우저별 한글파일 명 처리

                response.setHeader("Content-Type", "application/octet-stream");
                response.setHeader("Content-Disposition", "attachment; filename=" + onlyFileName);

                InputStream is = new FileInputStream(file);
                OutputStream os = response.getOutputStream();

                int length;
                byte[] buffer = new byte[1024];

                while( (length = is.read(buffer)) != -1){
                    os.write(buffer, 0, length);
                }

                os.flush();
                os.close();
                is.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }*/

    @GetMapping(value = "/download", produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<Resource> download(String fileName, @RequestHeader("User-Agent") String agent){
        ResponseEntity<Resource> result = null;

        try {
            String originFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("@UploadController, download " + originFileName);

            Resource file = new FileSystemResource(uploadPath + File.separator + originFileName);

            if(!file.exists()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

            //브라우저별 한글파일 명 처리
            String onlyFileName = originFileName.substring(originFileName.lastIndexOf("_") + 1);

            if(agent.contains("Trident"))//Internet Explore
                onlyFileName = URLEncoder.encode(onlyFileName, "UTF-8").replaceAll("\\+", " ");
            else if(agent.contains("Edge")) //Micro Edge
                onlyFileName = URLEncoder.encode(onlyFileName, "UTF-8");
            else //Chrome
                onlyFileName = new String(onlyFileName.getBytes("UTF-8"), "ISO-8859-1");
            //브라우저별 한글파일 명 처리

            HttpHeaders header = new HttpHeaders();
            header.add("Content-Disposition", "attachment; filename=" + onlyFileName);

            result = new ResponseEntity<>(file, header, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    @PostMapping(value = "/removeFile")
    public ResponseEntity<Boolean> removeFile(String fileName){

        try {
            String targetFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("target Name : " + targetFileName);

            File targetFile = new File(uploadPath + File.separator + targetFileName);
            File thumbnail = new File(targetFile.getParent(), "s_" + targetFile.getName());

            boolean result = targetFile.delete();
            if(thumbnail.exists()) result &= thumbnail.delete();

            return new ResponseEntity<>(result, HttpStatus.OK);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return new ResponseEntity<>(false, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping(value = "/display")
    public ResponseEntity<byte[]> display(String fileName){
        ResponseEntity<byte[]> result = null;

        try {
            String srcFileName = URLDecoder.decode(fileName, "UTF-8");
            log.info("srcFileName : " + srcFileName);

            File file = new File(uploadPath + File.separator + srcFileName);
            log.info("File : " + file);

            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-Type", Files.probeContentType(file.toPath()));

            result = new ResponseEntity<>(FileCopyUtils.copyToByteArray(file), headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<List<UploadDTO>> uploadFile(MultipartFile[] uploadFiles){

        List<UploadDTO> list = new ArrayList<>();

        for(MultipartFile file : uploadFiles){

            //파일의 원래 이름을 구하기
            String originalName = file.getOriginalFilename();
            String fileName = originalName.substring(originalName.lastIndexOf("\\") + 1);
            log.info("File Name : " + fileName);

            //년/월/일 디렉토리 구조 만들기
            String folderPath = makeFolder();

            String uuid = UUID.randomUUID().toString();

            //fileName : uploadPath + folderPath + uuid + "_" + filename
            String savefileName = uploadPath + File.separator + folderPath + File.separator + uuid + "_" + fileName;

            Path savePath = Paths.get(savefileName);

            try {
                boolean isImage = Files.probeContentType(savePath).startsWith("image");
                FileCopyUtils.copy(file.getInputStream(), new FileOutputStream(savePath.toFile()));

                //썸네일 생성(Thumbnailator가 자동으로 생성)
                if(isImage) {
                    File thumnail = new File(uploadPath + File.separator + folderPath + File.separator + "s_" + uuid + "_" + fileName);
                    Thumbnailator.createThumbnail(savePath.toFile(), thumnail, 100, 100);
                }

                list.add(new UploadDTO(fileName, uuid, folderPath, isImage));
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