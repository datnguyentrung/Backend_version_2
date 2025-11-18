package com.dat.backend_version_2.dto.upload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BytescaleAuthResponse {
    private String apiKey;
    private String folderPath;
}
