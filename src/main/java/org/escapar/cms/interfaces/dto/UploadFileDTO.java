package org.escapar.cms.interfaces.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UploadFileDTO {
    private String fileName;
    private String fileDownloadUri;
    private String fileType;
    private long size;

    public UploadFileDTO(String fileName, String fileDownloadUri, String fileType, long size) {
        this.fileName = fileName;
        this.fileDownloadUri = fileDownloadUri;
        this.fileType = fileType;
        this.size = size;
    }

}
