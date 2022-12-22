package com.lab.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @program: DataBoard
 * @className: ImageFontText
 * @description:
 * @author:
 * @create: 2022-12-21 15:34
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageFontText {
    private String text;
    private Integer textSize = 50;
    private String textColor = "#ff0000";
    private String textFont = "宋体";
    private Integer startX;
    private Integer startY;
}
