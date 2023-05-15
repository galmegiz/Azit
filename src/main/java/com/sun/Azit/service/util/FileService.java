package com.sun.Azit.service.util;

import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.rmi.server.ExportException;
import java.util.UUID;

@Service
@Log
@Slf4j
public class FileService {

    public String uploadFile(String uploadPath, String originalFileName, byte[] fileData) {
        UUID uuid = UUID.randomUUID();
        String extension = originalFileName.substring(originalFileName.lastIndexOf("."));
        String savedFileName = uuid.toString() + extension;
        String fileUploadFullUrl = uploadPath + "/" + savedFileName;
        try(FileOutputStream fos = new FileOutputStream(fileUploadFullUrl);){
            fos.write(fileData);
        }catch (IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 생성에 실패했습니다.", e);
        }
        /*
        try{

            fos.write(fileData);
            fos.close();
        }catch(IOException e){
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "이미지 생성에 실패했습니다.", e);
        }
*/
        return savedFileName;
    }

    public void deleteFile(String filePath) throws Exception{
        File deleteFile = new File(filePath);

        if (deleteFile.exists()) {
            deleteFile.delete();
            log.info("파일을 삭제했습니다.");
        } else{
            log.info("파일이 존재하지 않습니다.");
        }
    }
}
