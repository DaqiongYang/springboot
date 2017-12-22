package com.github.xuybin.springboot.configuration;

import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.servlet.KaptchaExtend;
import com.google.code.kaptcha.util.Config;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

/**
 * Created by Administrator on 2016/12/1.
 */
public class KaptchaExtendImpl extends KaptchaExtend {
    private Properties props = new Properties();
    private Producer kaptchaProducer = null;
    protected String sessionKeyValue = null;
    protected String sessionKeyDateValue = null;

    public void setVerify_code_char_string(String verify_code_char_string) {
        this.verify_code_char_string = verify_code_char_string;
    }

    private String verify_code_char_string="0123456789";

    public KaptchaExtendImpl() {
        ImageIO.setUseCache(false);
        this.props.put("kaptcha.border", "no");
        this.props.put("kaptcha.textproducer.font.color", "black");
        this.props.put("kaptcha.textproducer.char.space", "5");
        this.props.put("kaptcha.textproducer.char.string", verify_code_char_string);

        Config config = new Config(this.props);
        this.kaptchaProducer = config.getProducerImpl();
        this.sessionKeyValue = config.getSessionKey();
        this.sessionKeyDateValue = config.getSessionDate();
    }

    public void captcha(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Cache-Control", "no-store, no-cache");
        resp.setContentType("image/jpeg");
        String capText = this.kaptchaProducer.createText();
        req.getSession().setAttribute(this.sessionKeyValue, capText);
        req.getSession().setAttribute(this.sessionKeyDateValue, new Date());
        BufferedImage bi = this.kaptchaProducer.createImage(capText);
        ServletOutputStream out = resp.getOutputStream();
        ImageIO.write(bi, "jpg", out);
        req.getSession().setAttribute(this.sessionKeyValue, capText);
        req.getSession().setAttribute(this.sessionKeyDateValue, new Date());
    }

    public String getGeneratedKey(HttpServletRequest req) {
        HttpSession session = req.getSession();
        return (String)session.getAttribute("KAPTCHA_SESSION_KEY");
    }
}
