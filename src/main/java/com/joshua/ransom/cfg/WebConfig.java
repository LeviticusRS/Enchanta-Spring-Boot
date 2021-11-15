package com.joshua.ransom.cfg;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.resource.PathResourceResolver;
import org.springframework.web.servlet.resource.ResourceResolverChain;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Joshua Ransom on 6/19/2020.
 */
@Configuration
@EnableWebMvc
@EnableAsync
public class WebConfig extends WebMvcConfigurerAdapter {

	/**
	 * The classpath resource of the static folder under resources/static. Only used for this specific homepage site.
	 */
	private static ClassPathResource STATIC_CLASSPATH_RESOURCE = new ClassPathResource("static/");

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry
				.addResourceHandler("/**")
				.addResourceLocations("file:static/")
				.addResourceLocations("file:static-cdn/")
				.addResourceLocations("classpath:static/")
				.setCachePeriod(3600)
				.resourceChain(true)
				.addResolver(new PathResourceResolver() {
					@Override
					protected Resource resolveResourceInternal(HttpServletRequest request, String requestPath, List<? extends Resource> locations, ResourceResolverChain chain) {
						//System.out.println(requestPath);
						//System.out.println(request.getServerName());
						//System.out.println(locations);
						//System.out.println(locations.getClass());
						//for (Object o : locations) {
						//	System.out.println(o.getClass() + ": " + o);
						//}
						return super.resolveResourceInternal(request, requestPath, locations, chain);
					}
				});
	}

}
