package com.joshua.ransom.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisClientConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.session.data.redis.config.ConfigureRedisAction;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Objects;

/**
 * Created by Joshua Ransom on 2/1/2020.
 */
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 18000000)
@ControllerAdvice
@EnableAsync
@EnableScheduling
public class RedisConfig {

	public RedisConfig(Environment env) {
		this.env = env;
	}

	@Bean
	ConfigureRedisAction configureRedisAction() {
		return ConfigureRedisAction.NO_OP;
	}

	@Bean
	public JedisConnectionFactory lettuceConnectionFactory() {

		JedisClientConfiguration clientConfig = JedisClientConfiguration.builder()
				/*.useSsl()*//*.and()*/
				.build();
		RedisStandaloneConfiguration connectionInfo = new RedisStandaloneConfiguration(Objects.requireNonNull(env.getProperty("spring.redis.host")), Integer.parseInt(Objects.requireNonNull(env.getProperty("spring.redis.port"))));
		/*connectionInfo.setPassword(Objects.requireNonNull(env.getProperty("spring.redis.password")))*/;
		return new JedisConnectionFactory(connectionInfo, clientConfig);
	}

	final Environment env;

	@ModelAttribute("baseurl")
	public String baseUrl(){
		return env.getProperty("baseurl");
	}

	@ModelAttribute("asseturl")
	public String assetUrl(){
		return env.getProperty("asseturl");
	}

}