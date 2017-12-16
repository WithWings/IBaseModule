package com.withwings.basewidgets.image.newest.bean;

import java.io.Serializable;

/**
 * 指针解析出的数据信息类
 */
@SuppressWarnings("all")
public class NewestMessage implements Serializable {

    // 名称
    private String name;
    // 存放路径
    private String path;
    // 文件大小
    private Long size;
    // 宽度
    private Integer width;
    // 高度
    private Integer height;
    // 图片格式
    private String mimeType;
    // 创建时间：这里只存储到秒，还没有精确到毫秒值
    private Long addTime;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }

    public Integer getWidth() {
        return width;
    }

    public void setWidth(Integer width) {
        this.width = width;
    }

    public Integer getHeight() {
        return height;
    }

    public void setHeight(Integer height) {
        this.height = height;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public Long getAddTime() {
        return addTime;
    }

    public void setAddTime(Long addTime) {
        this.addTime = addTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof NewestMessage))
            return false;

        NewestMessage that = (NewestMessage) o;

        if (path != null ? !path.equals(that.path) : that.path != null)
            return false;
        return addTime != null ? addTime.equals(that.addTime) : that.addTime == null;

    }

    @Override
    public int hashCode() {
        int result = path != null ? path.hashCode() : 0;
        result = 31 * result + (addTime != null ? addTime.hashCode() : 0);
        return result;
    }
}
