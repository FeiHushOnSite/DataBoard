package com.lab.bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: DataBoard
 * @className: ImageWatermark
 * @description:
 * @author:
 * @create: 2022-12-21 15:35
 * @Version 1.0
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ImageWatermark {

    /**
     * 图片地址
     */
    private String imageUrl;
    /**
     * 水印图片左上、右下标
     */
    private List<Integer> points;

}
