package com.remember.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
  * @author remember
  * @date 2020/4/17 19:39
 * 实现关于Web的统一配置
 * 该配置类实现的功能：将Java的pojo转换成Http的输出流。
 * 实现依据：SpringBoot底层会通过HttpMessageConverts依靠jackson库将Java实体类输出为json格式。
 * 当有多个转换器的时候它会根据具体情况挑选最合适的转换器。
  */
@Configuration
public class WebConfiguration implements WebMvcConfigurer{
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.clear();
        converters.add(new MappingJackson2HttpMessageConverter());
    }
}
