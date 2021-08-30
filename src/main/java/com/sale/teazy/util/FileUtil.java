package com.sale.teazy.util;

import com.sale.teazy.exception.ExtensionNotAcceptableException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.Objects;
import java.util.UUID;

@Service

public class FileUtil {
    public final String VIDEO_MEDIA_TYPE = "video";
    public final String IMAGE_MEDIA_TYPE = "image";
    private final String[] acceptableVideoExtensions;
    private final String[] acceptableImageExtensions;
    public final Long imageWidth;
    public final Long imageHeight;
    public final int divide;
    public final String bucketName;

    public FileUtil(@Value("${file.upload.acceptableVideoExtensions}") String[] acceptableVideoExtensions,
                    @Value("${file.upload.acceptableImageExtensions}") String[] acceptableImageExtensions,
                    @Value("${img.width}") Long imageWidth,
                    @Value("${img.height}") Long imageHeight,
                    @Value("${img.divide}") int divide,
                    @Value("${minio.bucket}") String bucketName) {
        this.acceptableVideoExtensions = acceptableVideoExtensions;
        this.acceptableImageExtensions = acceptableImageExtensions;
        this.imageWidth = imageWidth;
        this.imageHeight = imageHeight;
        this.divide = divide;
        this.bucketName = bucketName;
    }




    private boolean isExtensionAcceptable(String extension, String mediaType) {
        if (mediaType.equals("image")) {
            for (String s : acceptableImageExtensions) {
                if (s.equalsIgnoreCase(extension)) {
                    return true;
                }
            }
        } else if (mediaType.equals("video")) {
            for (String s : acceptableVideoExtensions) {
                if (s.equalsIgnoreCase(extension)) {
                    return true;
                }
            }
        }
        return false;
    }

    public String getFileExtensionIfAcceptable(@NotNull MultipartFile multipartFile, String mediaType) {
        String extension = Objects.requireNonNull(
                multipartFile.getContentType()).split("/")[1];
        if (isExtensionAcceptable(extension, mediaType)) {
            return extension;
        } else {
            throw new ExtensionNotAcceptableException(extension);
        }
    }

    public String generateUniqueName(String extension) {
        UUID random = UUID.randomUUID();
        return random + "." + extension;
    }
}
