package it.applicazione.configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableCaching
public class CacheConfig implements WebMvcConfigurer {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
}
