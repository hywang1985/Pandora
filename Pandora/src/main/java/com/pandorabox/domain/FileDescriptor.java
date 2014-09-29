package com.pandorabox.domain;

import java.io.Serializable;

public interface FileDescriptor extends Descriptor, Serializable {

    public int getFileId();

    public void setFileId(int fileId);

    /**
     * 是否被选择为当前文章的播放音乐
     */
    public boolean isSelected();

    public void setSelected(boolean selected);

    /**
     * 文件的URL
     */
    public String getUrl();

    public void setUrl(String url);
}
