package com.eda.image.entertextintoimage.controller;

import com.eda.image.entertextintoimage.dto.request.RequestTestDto;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import javax.imageio.ImageIO;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class TestController {

  @RequestMapping(value = "/enter-text", method = { RequestMethod.POST})
  ResponseEntity<Resource> EnterText(@ModelAttribute RequestTestDto dto) throws IOException {

    BufferedImage originalImage = ImageIO.read(dto.getPicture().getInputStream());

    // 텍스트를 이미지에 삽입
    Graphics2D graphics = originalImage.createGraphics();
    graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    graphics.drawString("Hello, Custom Text!", 50, 50);
    graphics.dispose();


    return ResponseEntity.ok().body(new InputStreamResource(toInputStream(originalImage)));
  }

  // BufferedImage를 InputStream으로 변환하는 유틸리티 메서드
  private InputStream toInputStream(BufferedImage image) throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    ImageIO.write(image, "gif", os);
    return new ByteArrayInputStream(os.toByteArray());
  }

}
