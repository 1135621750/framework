package com.libertad.demo.common.core.configurer;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.MultipartConfigElement;
/**
 * 文件上传限制配置
 * @author Bai
 *
 */
@Configuration
public class MultipartConfigurer {
    @Value("${config.uploadPath}")
    public String uploadPath;
    @Value("${config.uploadMaxFileSize}")
    private String uploadMaxFileSize;
    @Value("${config.uploadMaxRequestSize}")
    private String uploadMaxRequestSize;
    @Bean
    public MultipartConfigElement multipartConfigElement(){
        MultipartConfigFactory factory=new MultipartConfigFactory();
        factory.setMaxFileSize(uploadMaxFileSize);
        factory.setMaxRequestSize(uploadMaxRequestSize);
        return factory.createMultipartConfig();
    }
}
