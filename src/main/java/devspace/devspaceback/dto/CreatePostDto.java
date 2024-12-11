package devspace.devspaceback.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class CreatePostDto {

    private String isPrivate;
    private String text;
    private List<MultipartFile> images = new ArrayList<>();

}
