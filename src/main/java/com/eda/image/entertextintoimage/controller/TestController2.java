package com.eda.image.entertextintoimage.controller;

import com.eda.image.entertextintoimage.dto.request.RequestTestDto;
import com.madgag.gif.fmsware.AnimatedGifEncoder;
import com.madgag.gif.fmsware.GifDecoder;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import javax.imageio.ImageIO;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class TestController2 {


  @RequestMapping(value = "/enter-text2", method = { RequestMethod.POST})
  ResponseEntity<Resource> EnterText(@ModelAttribute RequestTestDto dto) throws IOException {

    MultipartFile picture = dto.getPicture();


    InputStream inputStream = picture.getInputStream();

    BufferedImage[] frames = GifDecoder.read(new BufferedInputStream(inputStream));

    // Create an encoder to write the modified GIF
    AnimatedGifEncoder encoder = new AnimatedGifEncoder();
    encoder.start(new FileOutputStream("path/to/your/output.gif"));

    // Iterate through frames and add text
    for (BufferedImage frame : frames) {
      BufferedImage frameWithText = addTextToFrame(frame, "Hello, Custom Text!");
      encoder.addFrame(frameWithText);
    }

    encoder.finish();

    return null;
  }

  private BufferedImage addTextToFrame(BufferedImage frame, String text) {
    Graphics2D graphics = frame.createGraphics();
    graphics.setColor(Color.WHITE);
    graphics.setFont(new Font("Arial", Font.BOLD, 30));
    graphics.drawString(text, 50, 50);
    graphics.dispose();
    return frame;
  }
}
