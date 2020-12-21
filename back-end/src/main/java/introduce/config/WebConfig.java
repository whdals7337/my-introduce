package introduce.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        /*registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST");*/

        registry.addMapping("/**")
                .allowedOrigins("http://ec2-13-125-104-210.ap-northeast-2.compute.amazonaws.com");
    }
}
