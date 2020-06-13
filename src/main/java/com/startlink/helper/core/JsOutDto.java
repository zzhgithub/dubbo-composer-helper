package com.startlink.helper.core;

/**
 * js文件输出类型
 */
public class JsOutDto {

    /**
     * 文件存放地址
     */
    private String path;

    /**
     * 文件内容
     */
    private String text;

    public String getPath() {

        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
