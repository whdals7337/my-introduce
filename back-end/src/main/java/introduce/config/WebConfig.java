package introduce.config;

import introduce.interceptor.CertificationInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${file.upload-dir}")
    private String fileUploadPath;

    @Autowired
    CertificationInterceptor certificationInterceptor;

    // 로그인 여부 확인 인터셉터 //
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(certificationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/login/**")
                .excludePathPatterns("/js/**")
                .excludePathPatterns("/css/**")
                .excludePathPatterns("/images/**");

    }

    // resource mapping //
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**").addResourceLocations("file:///"+fileUploadPath);
    }

    // Cors 설정 //
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("http://ec2-13-125-104-210.ap-northeast-2.compute.amazonaws.com")
                .allowedMethods("GET");
    }
}
