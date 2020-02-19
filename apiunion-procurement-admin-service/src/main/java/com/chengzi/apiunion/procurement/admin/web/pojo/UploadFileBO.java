/**
 * 
 */
package com.chengzi.apiunion.procurement.admin.web.pojo;

import com.chengzi.common.data.beans.JsonPojo;

/**
 * @author Kolor
 */
public class UploadFileBO extends JsonPojo {
    private String url;
    private String fileName;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

}
