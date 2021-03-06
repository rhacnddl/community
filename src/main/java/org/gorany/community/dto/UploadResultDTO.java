package org.gorany.community.dto;

import lombok.*;

import java.io.IOException;
import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class UploadResultDTO implements Serializable {

    private String fileName;
    private String uuid;
    private String folderPath;

    private boolean image;

    private int bno;

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
    public String getAttachURL() throws IOException{
        return URLEncoder.encode("attach.png", "UTF-8");
    }
}
