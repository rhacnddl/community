package org.gorany.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class UploadDTO {

    private String fileName;
    private String uuid;
    private String folderPath;

    private boolean image;

    public String getImageURL(){
        try {
            return URLEncoder.encode(folderPath + "/" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getThumbnailURL() throws IOException {

        return URLEncoder.encode(folderPath + "/s_" + uuid + "_" + fileName, "UTF-8");
    }
}
