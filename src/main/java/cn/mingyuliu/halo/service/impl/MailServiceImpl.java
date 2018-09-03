package cn.mingyuliu.halo.service.impl;

import cn.mingyuliu.halo.config.OptionHolder;
import cn.mingyuliu.halo.model.enums.OptionEnum;
import cn.mingyuliu.halo.service.MailService;
import cn.mingyuliu.halo.utils.HaloUtils;
import freemarker.template.Template;
import io.github.biezhi.ome.OhMyEmail;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;

import javax.annotation.Resource;
import java.io.File;
import java.util.Map;

/**
 * <pre>
 *     邮件发送业务逻辑实现类
 * </pre>
 *
 * @author : RYAN0UP
 * @date : 2018/1/23
 */
@Service
public class MailServiceImpl implements MailService {

    @Resource
    private OptionHolder optionHolder;

    @Autowired
    private FreeMarkerConfigurer freeMarker;

    /**
     * 发送邮件
     *
     * @param to      to 接收者
     * @param subject subject 标题
     * @param content content 内容
     */
    @Override
    public void sendMail(String to, String subject, String content) {
        //配置邮件服务器
        HaloUtils.configMail(
                optionHolder.get(OptionEnum.MAIL_SMTP_HOST),
                optionHolder.get(OptionEnum.MAIL_SMTP_USERNAME),
                optionHolder.get(OptionEnum.MAIL_SMTP_PASSWORD));
        try {
            OhMyEmail.subject(subject)
                    .from(optionHolder.get(OptionEnum.MAIL_FROM_NAME))
                    .to(to)
                    .text(content)
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送模板邮件
     *
     * @param to           接收者
     * @param subject      主题
     * @param content      内容
     * @param templateName 模板路径
     */
    @Override
    public void sendTemplateMail(String to, String subject, Map<String, Object> content, String templateName) {
        //配置邮件服务器
        HaloUtils.configMail(
                optionHolder.get(OptionEnum.MAIL_SMTP_HOST),
                optionHolder.get(OptionEnum.MAIL_SMTP_USERNAME),
                optionHolder.get(OptionEnum.MAIL_SMTP_PASSWORD));
        try {
            Template template = freeMarker.getConfiguration().getTemplate(templateName);
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, content);
            OhMyEmail.subject(subject)
                    .from(optionHolder.get(OptionEnum.MAIL_FROM_NAME))
                    .to(to)
                    .html(text)
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 发送带有附件的邮件
     *
     * @param to           接收者
     * @param subject      主题
     * @param content      内容
     * @param templateName 模板路径
     * @param attachSrc    附件路径
     */
    @Override
    public void sendAttachMail(String to, String subject, Map<String, Object> content, String templateName, String attachSrc) {
        //配置邮件服务器
        HaloUtils.configMail(
                optionHolder.get(OptionEnum.MAIL_SMTP_HOST),
                optionHolder.get(OptionEnum.MAIL_SMTP_USERNAME),
                optionHolder.get(OptionEnum.MAIL_SMTP_PASSWORD));
        File file = new File(attachSrc);
        try {
            Template template = freeMarker.getConfiguration().getTemplate(templateName);
            String text = FreeMarkerTemplateUtils.processTemplateIntoString(template, content);
            OhMyEmail.subject(subject)
                    .from(optionHolder.get(OptionEnum.MAIL_FROM_NAME))
                    .to(to)
                    .html(text)
                    .attach(file, file.getName())
                    .send();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
