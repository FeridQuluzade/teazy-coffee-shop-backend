package com.sale.teazy.util;

import com.sale.teazy.exception.ExtensionNotAcceptableException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.util.UUID;

@Service
public class FileUtil {
    @Value("${file.upload.acceptableVideoExtensions}")
    private String[] acceptableVideoExtensions;

    @Value("${file.upload.acceptableImageExtensions}")
    private String[] acceptableImageExtensions;

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
        String extension = multipartFile.getContentType().split("/")[1];
        if (isExtensionAcceptable(extension, mediaType)) {
            return extension;
        } else {
            throw new ExtensionNotAcceptableException(extension);
        }
    }

    public String generateUniqueName(String extension) {
       UUID random = UUID.randomUUID();
        return random+ "." + extension;
    }
}
