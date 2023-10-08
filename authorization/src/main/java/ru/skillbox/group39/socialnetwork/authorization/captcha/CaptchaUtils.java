package ru.skillbox.group39.socialnetwork.authorization.captcha;

import cn.apiclub.captcha.Captcha;
import cn.apiclub.captcha.backgrounds.GradiatedBackgroundProducer;
import cn.apiclub.captcha.noise.CurvedLineNoiseProducer;
import cn.apiclub.captcha.text.producer.DefaultTextProducer;
import cn.apiclub.captcha.text.renderer.DefaultWordRenderer;
import lombok.extern.slf4j.Slf4j;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.util.Base64;

/**
 * @author Artem Lebedev | 08/09/2023 - 00:29
 */

@Slf4j
public class CaptchaUtils{

	//Creating Captcha Object
	public static Captcha createCaptcha(Integer width, Integer height) {

		return new Captcha.Builder(width, height)
				.addBackground(new GradiatedBackgroundProducer())
				.addText(new DefaultTextProducer(), new DefaultWordRenderer())
				.addNoise(new CurvedLineNoiseProducer())
				.build();
	}

	//Converting to binary Base64 String
	public static String encodeCaptcha(Captcha captcha) {
		String image = null;
		try {
			ByteArrayOutputStream bos= new ByteArrayOutputStream();
			ImageIO.write(captcha.getImage(),"png", bos);
			byte[] byteArray= Base64.getEncoder().encode(bos.toByteArray());
			image = new String(byteArray);
		} catch (Exception e) {
			log.error(" ! Exception during encoding image to Base64");
		}
		return image;
	}

}
