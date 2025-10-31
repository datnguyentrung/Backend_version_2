package com.dat.backend_version_2.controller.upload;

import com.dat.backend_version_2.dto.upload.BytescaleUploadRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

@RestController
@RequestMapping("/api/v1/bytescale-upload")
@RequiredArgsConstructor
public class BytescaleUploadController {

    @Value("${bytescale.account-id}")
    private String bytescaleAccountId;

    @Value("${bytescale.api-key}")
    private String bytescaleApiKey;

    /**
     * Tạo signed URL trên Bytescale để upload file
     * @param bytescaleUploadRequest đối tượng chứa thông tin folder và tên file
     * @return ResponseEntity chứa signed URL hoặc thông báo lỗi
     * @throws IOException khi có lỗi I/O trong quá trình gửi request
     * @throws InterruptedException khi request bị gián đoạn
     */
    @PostMapping("/signed-url")
    public ResponseEntity<String> createBytescaleSignedUrl(
            @RequestBody BytescaleUploadRequest bytescaleUploadRequest)
            throws IOException, InterruptedException {

        String folderPath = getBytescaleFolderPath(bytescaleUploadRequest.getFolderName());

        if (folderPath.isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid folderName: " + bytescaleUploadRequest.getFolderName());
        }

        String requestBody = String.format(
                "{\"expires_in\":3600,\"max_size_bytes\":10485760,\"path\":\"%s%s\"}",
                folderPath,
                bytescaleUploadRequest.getFileName()
        );

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("https://api.bytescale.com/v1/accounts/" + bytescaleAccountId + "/uploads/signed-url"))
                .header("Authorization", "Bearer " + bytescaleApiKey)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return ResponseEntity.ok(response.body());
        }
    }

    /**
     * Chuyển đổi tên folder thành đường dẫn tương ứng trong Bytescale
     * @param folderName tên folder (ví dụ: "coach-attendance", "student-attendance")
     * @return đường dẫn đầy đủ trong Bytescale, trả về chuỗi rỗng nếu folder không hợp lệ
     */
    private String getBytescaleFolderPath(String folderName) {
        return switch (folderName) {
            case "coach-attendance" -> "attendance/coach-attendance/";
            case "student-attendance" -> "attendance/student-attendance/";
            default -> "";
        };
    }
}
