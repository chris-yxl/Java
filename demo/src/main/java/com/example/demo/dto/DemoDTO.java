package com.example.demo.dto;

/**
 * @author  chris
 * @className DemoDTO
 * @date
 * @description:sql中#{}的入参
 */
public class DemoDTO {
    private String stuName;

    public String getStuName() {
        return stuName;
    }

    public void setStuName(String stuName) {
        this.stuName = stuName;
    }

    @Override
    public String toString() {
        return "DemoDTO{" +
                "stuName='" + stuName + '\'' +
                '}';
    }
}
