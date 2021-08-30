package com.sale.teazy.service;

import com.sale.teazy.util.FileUtil;
import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import io.minio.RemoveObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.messages.ErrorResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;

import static net.logstash.logback.argument.StructuredArguments.kv;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileServiceImpl implements FileService {
    private final MinioClient minioClient;
    private final FileUtil fileUtil;

    @Override
    @SneakyThrows
    public byte[] getFile(String fileName, String folder) {
        String objectName = folder + fileName;
        GetObjectArgs minioRequest = GetObjectArgs.builder().bucket(fileUtil.bucketName).object(objectName).build();
        byte[] bytes = null;
        try {
            bytes = minioClient.getObject(minioRequest).readAllBytes();
        } catch (ErrorResponseException e) {
            ErrorResponse response = e.errorResponse();
            log.error("Minio error occurred with: {}, {}, {}",
                    kv("code", response.code()), kv("message", response.message()),
                    kv("objectName", response.objectName()));
        }
        return bytes;
    }

    @Override
    @SneakyThrows
    public String uploadImage(MultipartFile multipartFile, String folder) {
        String fileExtension = fileUtil.getFileExtensionIfAcceptable(multipartFile, fileUtil.IMAGE_MEDIA_TYPE);
        String fileName = fileUtil.generateUniqueName(fileExtension);
        String objectName = folder + fileName;

        BufferedImage image = ImageIO.read(multipartFile.getInputStream());
        int width = image.getWidth();
        int height = image.getHeight();
        if (width > fileUtil.imageWidth && height > fileUtil.imageWidth) {
            width = width / fileUtil.divide;
            height = height / fileUtil.divide;
        }

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(resizeImage(image, width, height), fileExtension, arrayOutputStream);
        InputStream inputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());


        log.info("Upload image started with :{},{}",
                kv("fileUtil.bucketName", fileUtil.bucketName),
                kv("objectName", objectName));

        minioClient.putObject(PutObjectArgs.builder().bucket(fileUtil.bucketName).object(objectName).stream(
                        inputStream, inputStream.available(), -1)
                .contentType(multipartFile.getContentType())
                .build());

        log.info("Upload image successfully completed  with :{},{}",
                kv("fileUtil.bucketName", fileUtil.bucketName),
                kv("objectName", objectName));
        return fileName;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        log.info("Resize image started with: {},{},{}"
                , kv("originalImage", originalImage)
                , kv("targetWidth", targetWidth)
                , kv("targetHeight", targetHeight));
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);

        log.info("Resize image successfully completed with: {},{},{}"
                , kv("originalImage", originalImage)
                , kv("targetWidth", targetWidth)
                , kv("targetHeight", targetHeight));
        return outputImage;
    }


    @Override
    @SneakyThrows
    public void deleteFile(String fileName, String folder) {
        String objectName = folder + fileName;
        log.info("Delete file starting with: {},{}"
                , kv("fileName", fileName)
                , kv("folder", folder));
        minioClient.
                removeObject(RemoveObjectArgs.builder().bucket(fileUtil.bucketName)
                        .object(objectName).build());

    }

    @Override
    @SneakyThrows
    public String uploadVideo(MultipartFile multipartFile, String folder) {
        log.info("Upload Image starting with : {},{}", kv("multipartFile", multipartFile), kv("folder", folder));
        String fileExtension = fileUtil
                .getFileExtensionIfAcceptable(multipartFile, fileUtil.VIDEO_MEDIA_TYPE);
        String fileName = fileUtil.generateUniqueName(fileExtension);
        String objectName = folder + fileName;
        minioClient.putObject(PutObjectArgs.builder().bucket(fileUtil.bucketName).object(objectName).stream(
                        multipartFile.getInputStream(), multipartFile.getInputStream().available(), -1)
                .contentType(multipartFile.getContentType())
                .build());
        log.info("Upload Image successfully  completed with : {},{}"
                , kv("multipartFile", multipartFile)
                , kv("folder", folder));
        return fileName;
    }
}
