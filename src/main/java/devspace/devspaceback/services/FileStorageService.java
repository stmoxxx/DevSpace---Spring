package devspace.devspaceback.services;

import devspace.devspaceback.repositories.post.PostMediaRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileStorageService {


    @Value("${application.file.upload.images-output-path}")
    private String fileUploadPath;

    final PostMediaRepository postMediaRepository;

    public String saveFile(@NonNull MultipartFile sourceFile, @NonNull Long userId) {
        final String fileUploadSubPath = "user" + File.separator + userId;
        return uploadFile(sourceFile, fileUploadSubPath);
    }

    private String uploadFile(@NonNull MultipartFile sourceFile,@NonNull String fileUploadSubPath) {
        final String finalUploadPath = fileUploadPath + File.separator + fileUploadSubPath;
        File targetFolder = new File(finalUploadPath);
        if(!targetFolder.exists()){
            boolean folderCreated = targetFolder.mkdirs();
            if(!folderCreated){
                log.warn("Failed to create a target folder");
                return null;
            }
        }
        final String fileExtension = getFileExtension(sourceFile.getOriginalFilename());
        String targetFilePath = finalUploadPath + File.separator + System.currentTimeMillis() + "." +  fileExtension;
        Path targetPath = Paths.get(targetFilePath);
        try{
            Files.write(targetPath, sourceFile.getBytes());
            log.info("File is saved to " + targetFilePath );
            return targetFilePath;
        }  catch (IOException e){
            log.error("File wasn't saved", e);
        }
        return null;
    }

    private String getFileExtension(String originalFileName) {
        if (originalFileName == null || originalFileName.isEmpty()) {
            return "";
        }
        int lastDotIndex = originalFileName.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return originalFileName.substring(lastDotIndex + 1).toLowerCase();
    }

}
