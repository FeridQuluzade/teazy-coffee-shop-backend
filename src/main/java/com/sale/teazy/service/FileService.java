package com.sale.teazy.service;

import org.springframework.web.multipart.MultipartFile;

public interface FileService {
    byte[] getFile(String fileName, String folder);

    String uploadImage(MultipartFile multipartFile, String folder);

    void deleteFile(String fileName, String folder);

    String uploadVideo(MultipartFile multipartFile, String folder);
}
