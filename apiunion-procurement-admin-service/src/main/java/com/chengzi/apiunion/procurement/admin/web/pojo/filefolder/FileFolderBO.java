package com.chengzi.apiunion.procurement.admin.web.pojo.filefolder;

import com.chengzi.apiunion.common.module.filefolder.pojo.FileFolderDO;
import com.chengzi.common.data.beans.JsonPojo;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ql
 * @date 2019/1/30
 * @description: 文件夹数据页面对象
 */
public class FileFolderBO extends JsonPojo {
    private long id;
    private long pid;
    private String folderName;
    private int isDeleted;
    private List<FileFolderBO> childFolders = new ArrayList<>();

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getPid() {
        return pid;
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    public String getFolderName() {
        return folderName;
    }

    public void setFolderName(String folderName) {
        this.folderName = folderName;
    }

    public int getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(int isDeleted) {
        this.isDeleted = isDeleted;
    }

    public List<FileFolderBO> getChildFolders() {
        return childFolders;
    }

    public void setChildFolders(List<FileFolderBO> childFolders) {
        this.childFolders = childFolders;
    }

    public static FileFolderBO convert(FileFolderDO folderDo) {
        FileFolderBO fileFolderBO = new FileFolderBO();
        fileFolderBO.setId(folderDo.getId());
        fileFolderBO.setPid(folderDo.getParentId());
        fileFolderBO.setFolderName(folderDo.getFolderName());
        fileFolderBO.setIsDeleted(folderDo.getIsDeleted());
        List<FileFolderDO> childFileFolders = folderDo.getChildFileFolders();
        for (FileFolderDO childFileFolder : childFileFolders) {
            FileFolderBO convert = convert(childFileFolder);
            if (convert == null){
                continue;
            }
            fileFolderBO.getChildFolders().add(convert);
        }
        return fileFolderBO;
    }
}
