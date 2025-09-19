package com.longtoast.bilbil_api;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Controller
public class FileUploadController {

    // ⚠️ 서버 환경에 맞게 이 경로를 반드시 수정하세요!
    private static final String UPLOAD_DIR = "/opt/app/uploads/";
    private final Path uploadPath = Paths.get(UPLOAD_DIR);

    // 1. 파일 업로드 및 목록을 보여주는 핸들러 (showUploadForm 수정)
    @GetMapping("/")
    public String showUploadForm(Model model) throws IOException {
        // 업로드된 파일 목록을 가져와서 모델에 추가합니다.
        try (Stream<Path> paths = Files.list(uploadPath)) {
            List<String> fileNames = paths
                    .filter(Files::isRegularFile)
                    .map(Path::getFileName)
                    .map(Path::toString)
                    .collect(Collectors.toList());
            model.addAttribute("files", fileNames);
        } catch (IOException e) {
            // 디렉토리가 없거나 접근 불가능할 경우 빈 목록을 보냅니다.
            model.addAttribute("message", "파일 저장소에 접근할 수 없습니다. 경로/권한을 확인하세요: " + UPLOAD_DIR);
        }

        return "uploadForm";
    }

    // 2. 파일 업로드를 처리하는 핸들러 (기존과 동일)
    @PostMapping("/upload")
    public String handleFileUpload(@RequestParam("file") MultipartFile file,
                                   RedirectAttributes redirectAttributes) {

        if (file.isEmpty()) {
            redirectAttributes.addFlashAttribute("message", "업로드할 파일을 선택해주세요.");
            return "redirect:/";
        }

        try {
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // 파일명 충돌 방지를 위해 UUID 사용
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String uniqueFileName = UUID.randomUUID().toString() + fileExtension;
            Path filePath = uploadPath.resolve(uniqueFileName);

            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            redirectAttributes.addFlashAttribute("message",
                    "✅ 파일 " + originalFilename + "이 성공적으로 업로드되었습니다. (저장된 이름: " + uniqueFileName + ")");

        } catch (IOException e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("message",
                    "❌ 파일 업로드에 실패했습니다. 경로/권한을 확인하세요: " + UPLOAD_DIR);
        }

        return "redirect:/";
    }

    // 3. 파일 다운로드를 처리하는 핸들러 (새로 추가)
    @GetMapping("/download/{filename:.+}") // 파일명에 '.'을 포함할 수 있도록 패턴 지정
    public ResponseEntity<Resource> downloadFile(@PathVariable String filename) {
        try {
            // 저장된 파일 경로에서 파일을 Resource 객체로 로드합니다.
            Path file = uploadPath.resolve(filename);
            Resource resource = new UrlResource(file.toUri());

            if (resource.exists() || resource.isReadable()) {
                // 한글 파일명을 위한 인코딩
                String encodedFilename = UriUtils.encode(filename, StandardCharsets.UTF_8);

                return ResponseEntity.ok()
                        .header(HttpHeaders.CONTENT_DISPOSITION,
                                "attachment; filename=\"" + encodedFilename + "\"") // 다운로드 지시
                        .body(resource);
            } else {
                // 파일을 찾을 수 없을 때
                throw new RuntimeException("파일을 찾을 수 없거나 읽을 수 없습니다: " + filename);
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException("잘못된 파일 경로입니다.", e);
        }
    }
}