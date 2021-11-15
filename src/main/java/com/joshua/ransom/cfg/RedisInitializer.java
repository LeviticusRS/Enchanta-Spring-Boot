package com.joshua.ransom.cfg;

import org.springframework.session.web.context.AbstractHttpSessionApplicationInitializer;

/**
 * Created by Joshua Ransom on 2/1/2020.
 */
public class RedisInitializer extends AbstractHttpSessionApplicationInitializer {

	public RedisInitializer() {
		super(RedisConfig.class);
	}

}