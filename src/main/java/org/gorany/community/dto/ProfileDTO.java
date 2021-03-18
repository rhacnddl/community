package org.gorany.community.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
public class ProfileDTO {

    private String uuid;
    private String fileName;
    private String path;

    private String account;

    public String getImageURL(){
        try {
            return URLEncoder.encode(path + "/" + uuid + "_" + fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return null;
    }
    public String getThumbnailURL() throws IOException {

        return URLEncoder.encode(path + "/s_" + uuid + "_" + fileName, "UTF-8");
    }
}
