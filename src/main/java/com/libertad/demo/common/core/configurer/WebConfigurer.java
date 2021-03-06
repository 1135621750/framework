package com.libertad.demo.common.core.configurer;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter4;
import com.libertad.demo.common.Interceptor.UserInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * web配置
 * @author Bai
 */
@Configuration
public class WebConfigurer extends WebMvcConfigurationSupport {

    @Value("${spring.profiles.active}")
    private String env;//当前激活的配置文件
	
    /**
     * 消息转换器(对前端友好，不需要判断null值)
     * 修改自定义消息转换器
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        FastJsonHttpMessageConverter4 converter = new FastJsonHttpMessageConverter4();
        converter.setSupportedMediaTypes(getSupportedMediaTypes());
        FastJsonConfig config = new FastJsonConfig();
        config.setSerializerFeatures(
            // Number null -> 0
            SerializerFeature.WriteNullNumberAsZero,
            //List字段如果为null,输出为[],而非null
            SerializerFeature.WriteNullListAsEmpty,
            //字符类型字段如果为null,输出为"",而非null
            SerializerFeature.WriteNullStringAsEmpty,
            //Boolean字段如果为null,输出为false,而非null
//            SerializerFeature.WriteNullBooleanAsFalse,
            //是否输出值为null的字段,默认为false
//            SerializerFeature.WriteMapNullValue,
            //禁止循环引用,消除对同一对象循环引用的问题，默认为false（如果不配置有可能会进入死循环）
            SerializerFeature.DisableCircularReferenceDetect
        );
        converter.setFastJsonConfig(config);
        converter.setDefaultCharset(Charset.forName("UTF-8"));
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON_UTF8));
        converters.add(converter);
    }

    private List<MediaType> getSupportedMediaTypes() {
        List<MediaType> supportedMediaTypes = new ArrayList<>();
        supportedMediaTypes.add(MediaType.APPLICATION_JSON);
        supportedMediaTypes.add(MediaType.APPLICATION_JSON_UTF8);
        supportedMediaTypes.add(MediaType.APPLICATION_ATOM_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_FORM_URLENCODED);
        supportedMediaTypes.add(MediaType.APPLICATION_OCTET_STREAM);
        supportedMediaTypes.add(MediaType.APPLICATION_PDF);
        supportedMediaTypes.add(MediaType.APPLICATION_RSS_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XHTML_XML);
        supportedMediaTypes.add(MediaType.APPLICATION_XML);
        supportedMediaTypes.add(MediaType.IMAGE_GIF);
        supportedMediaTypes.add(MediaType.IMAGE_JPEG);
        supportedMediaTypes.add(MediaType.IMAGE_PNG);
        supportedMediaTypes.add(MediaType.TEXT_EVENT_STREAM);
        supportedMediaTypes.add(MediaType.TEXT_HTML);
        supportedMediaTypes.add(MediaType.TEXT_MARKDOWN);
        supportedMediaTypes.add(MediaType.TEXT_PLAIN);
        supportedMediaTypes.add(MediaType.TEXT_XML);
        return supportedMediaTypes;
    }

    //解决跨域问题
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //registry.addMapping("/**");
    }

    /**
     * Swagger2 文档
     * 继承WebMvcConfigurationSupport之后，静态文件映射会出现问题，需要重新指定静态资源
     */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    if(env.equals("dev") || env.equals("test")){
            registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
            registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        }
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        registry.addResourceHandler("/templates/**").addResourceLocations("classpath:/templates/");

		super.addResourceHandlers(registry);
	}
	
	/**
	 * 解决拦截器不能注入redis问题
	 * @return
	 */
	@Bean
    public UserInterceptor getUserInterceptor(){
    	return new UserInterceptor();
    }

	/**
    * 添加拦截器  请求头拦截
    */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 这里添加的是拦截的路径 /**为全部拦截,如拦截/userInfo/regist/*
        registry.addInterceptor(getUserInterceptor()).addPathPatterns("/**")
                //不拦截请求
                .excludePathPatterns(
                        "/swagger-resources/**",
                        "/v2/api-docs/**");
    }
   
   

   

}
