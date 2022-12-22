//package com.lab.controller;
//
//import com.baomidou.mybatisplus.core.toolkit.IdWorker;
//import com.lab.bean.ImageFontText;
//import com.lab.bean.ImageWatermark;
//import com.lab.util.ImageUtil;
//import com.sun.javafx.collections.MappingChange;
//import lombok.extern.java.Log;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.util.ResourceUtils;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.imageio.ImageIO;
//import javax.servlet.http.HttpServletResponse;
//import java.awt.image.BufferedImage;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.InputStream;
//import java.io.OutputStream;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Date;
//import java.util.List;
//
///**
// * @program: DataBoard
// * @className: MeetingGuestCardController
// * @description:
// * @author:
// * @create: 2022-12-21 15:40
// * @Version 1.0
// **/
//
//@RestController
//@RequestMapping("/meetingGuestCard")
//public class MeetingGuestCardController {
//
//    @Value("${config.ldir}")
//    String ldir;
//
//    @Value("${config.wdir}")
//    String wdir;
//
//    @Autowired
//    private MeetingApplyService meetingApplyService;
//
//    @Autowired
//    private IdWorker idWorker;
//
//    @Autowired
//    private MeetingGuestCardTemplateService meetingGuestCardTemplateService;
//
//
//    @PostMapping("/generateGuestCard")
////    @Log("绘制电子嘉宾证")
//    public void generateGuestCard(@RequestBody MappingChange.Map<String, String> formData, HttpServletResponse response) {
//        String templateId = formData.get("templateId");
//        String ids = formData.get("meetingApplyId");
//
//        List<String> meetingApplyIds = Arrays.asList(ids.split(","));
//        List<MeetingApply> meetingApplys = meetingApplyService.getByKeys(meetingApplyIds);
//
//        String uuid = UUID.randomUUID().toString().replaceAll("-", "");
//        String datePath = new SimpleDateFormat("yyyy/MM/dd").format(new Date()) + "/" + uuid + "/";
//        String zipPath = SysMeetingUtil.getAnnexFilePath() + "generateGuestCard/" + datePath;
//        File file = new File(zipPath);
//        if (!file.exists()) {
//            file.mkdirs();
//        }
//
//        // 上传的位置
//        String path = System.getProperty("os.name").toLowerCase().indexOf("win") >= 0 ? wdir : ldir;
//
//        MeetingGuestCardTemplate cardTemplate = meetingGuestCardTemplateService.findById(templateId);
//        // 底图
//        String baseImgUrl = cardTemplate.getBaseMapUrl();
//        try {
//
//            for (int i = 0; i < meetingApplys.size(); i++) {
//                MeetingApply meetingApply = meetingApplys.get(i);
//
//                String name = null;
//                String unitName = null;
//                String postName = null;
//
//                String photoUrl = path;
//
//                if ("zh".equals(meetingApply.getApplyWay())) {
//                    name = meetingApply.getName();
//                    unitName = meetingApply.getUnitName();
//                    postName = meetingApply.getPositionName();
//                    photoUrl = photoUrl + meetingApply.getPhotoUrl();
//                } else {
//                    name = meetingApply.getNameEn();
//                    unitName = meetingApply.getUnitNameEn();
//                    postName = meetingApply.getPositionNameEn();
//                    photoUrl = photoUrl + meetingApply.getPhotoUrl();
//                }
//
//                List<ImageWatermark> images = new ArrayList<>();
//                List<ImageFontText> texts = new ArrayList<>();
//
//                // 查询模板详情
//                List<MeetingGuestCardTemplate> templates = meetingGuestCardTemplateService.findByTemplateId(templateId);
//                for (MeetingGuestCardTemplate template : templates) {
//                    String dict = template.getElementTypeDict();
//                    if ("1".equals(dict)) {
//                        ImageFontText imageFontText = new ImageFontText(name, StringUtils.isNotBlank(template.getFontSize()) ? Integer.parseInt(template.getFontSize()) : 30,
//                                StringUtils.isNotBlank(template.getFontColor()) ? template.getFontColor() : "#333333",
//                                StringUtils.isNotBlank(template.getFontType()) ? template.getFontType() : "宋体",
//                                template.getStartX(), template.getStartY());
//                        texts.add(imageFontText);
//                    }
//                    if ("2".equals(dict)) {
//                        ImageFontText imageFontText = new ImageFontText(unitName, StringUtils.isNotBlank(template.getFontSize()) ? Integer.parseInt(template.getFontSize()) : 30,
//                                StringUtils.isNotBlank(template.getFontColor()) ? template.getFontColor() : "#333333",
//                                StringUtils.isNotBlank(template.getFontType()) ? template.getFontType() : "宋体",
//                                template.getStartX(), template.getStartY());
//                        texts.add(imageFontText);
//                    }
//                    if ("3".equals(dict)) {
//                        ImageFontText imageFontText = new ImageFontText(postName, StringUtils.isNotBlank(template.getFontSize()) ? Integer.parseInt(template.getFontSize()) : 30,
//                                StringUtils.isNotBlank(template.getFontColor()) ? template.getFontColor() : "#333333",
//                                StringUtils.isNotBlank(template.getFontType()) ? template.getFontType() : "宋体",
//                                template.getStartX(), template.getStartY());
//                        texts.add(imageFontText);
//                    }
//                    if ("100".equals(dict)) {
//                        Integer startX = template.getStartX();
//                        Integer startY = template.getStartY();
//                        Integer endX = template.getEndX();
//                        Integer endY = template.getEndY();
//                        List<Integer> imagePoints = Arrays.asList(startX, startY, endX, endY);
//                        ImageWatermark image = new ImageWatermark(photoUrl, imagePoints);
//                        images.add(image);
//                    }
//                }
//
//                try {
//                    BufferedImage bufferedImage = ImageUtil.watermarkImgBase64(baseImgUrl, images, texts);
//                    OutputStream os = new FileOutputStream(zipPath + String.valueOf(i + 1) + name + ".jpg");
//                    ImageIO.write(bufferedImage, "jpg", os);
//
//                    //更新meetingApply的状态
//                    MeetingApply apply = new MeetingApply();
//                    apply.setId(meetingApply.getId());
//                    apply.setCreateCardDate(new Date());
//                    apply.setIsCreateCard("Yes");
//                    apply.setCardUrl("generateGuestCard/" + datePath + String.valueOf(i + 1) + name + ".jpg");
//                    meetingApplyService.update(apply);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//            }
//
//            String zipFile = CompressUtil.zipFile(new File(zipPath), "zip");
//            response.setContentType("APPLICATION/OCTET-STREAM");
//            String fileName = "guestCard" + uuid + ".zip";
//            response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
//
//            //ZipOutputStream out = new ZipOutputStream(response.getOutputStream());
//            OutputStream out = response.getOutputStream();
//            File ftp = ResourceUtils.getFile(zipFile);
//            InputStream in = new FileInputStream(ftp);
//
//            // 循环取出流中的数据
//            byte[] b = new byte[100];
//            int len;
//            while ((len = in.read(b)) != -1) {
//                out.write(b, 0, len);
//            }
//            in.close();
//            out.close();
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//}
