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
import org.springframework.beans.factory.annotation.Value;
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
    private final String VIDEO_MEDIA_TYPE = "video";
    private final String IMAGE_MEDIA_TYPE = "image";

    private final MinioClient minioClient;
    private final FileUtil fileUtil;
    @Value("${minio.bucket}")
    private String bucketName;


    @Override
    @SneakyThrows
    public byte[] getFile(String fileName, String folder) {
        String objectName = folder + fileName;
        GetObjectArgs minioRequest = GetObjectArgs.builder().bucket(bucketName).object(objectName).build();
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
        String fileExtension = fileUtil.getFileExtensionIfAcceptable(multipartFile, IMAGE_MEDIA_TYPE);
        String fileName = fileUtil.generateUniqueName(fileExtension);
        String objectName = folder + fileName;

        BufferedImage image = ImageIO.read(multipartFile.getInputStream());
        int width = image.getWidth();
        int height = image.getHeight();
        if (width > 2560 && height > 1000) {
            width = width / 3;
            height = height / 3;
        }

        ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(resizeImage(image, width, height), fileExtension, arrayOutputStream);
        InputStream inputStream = new ByteArrayInputStream(arrayOutputStream.toByteArray());

        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                        inputStream, inputStream.available(), -1)
                .contentType(multipartFile.getContentType())
                .build());

        return fileName;
    }

    private BufferedImage resizeImage(BufferedImage originalImage, int targetWidth, int targetHeight) {
        Image resultingImage = originalImage.getScaledInstance(targetWidth, targetHeight, Image.SCALE_DEFAULT);
        BufferedImage outputImage = new BufferedImage(targetWidth, targetHeight, BufferedImage.TYPE_INT_RGB);
        outputImage.getGraphics().drawImage(resultingImage, 0, 0, null);
        return outputImage;
    }


    @Override
    @SneakyThrows
    public void deleteFile(String fileName, String folder) {
        String objectName = folder + fileName;
        minioClient.
                removeObject(RemoveObjectArgs.builder().bucket(bucketName)
                        .object(objectName).build());

    }

    @Override
    @SneakyThrows
    public String uploadVideo(MultipartFile multipartFile, String folder) {
        String fileExtension = fileUtil
                .getFileExtensionIfAcceptable(multipartFile, VIDEO_MEDIA_TYPE);
        String fileName = fileUtil.generateUniqueName(fileExtension);
        String objectName = folder + fileName;
        minioClient.putObject(PutObjectArgs.builder().bucket(bucketName).object(objectName).stream(
                        multipartFile.getInputStream(), multipartFile.getInputStream().available(), -1)
                .contentType(multipartFile.getContentType())
                .build());

        return fileName;
    }
}
