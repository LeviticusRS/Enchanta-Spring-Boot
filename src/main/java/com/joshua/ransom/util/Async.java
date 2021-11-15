package com.joshua.ransom.util;

import org.springframework.web.context.request.async.DeferredResult;

/**
 * Created by Joshua Ransom on 7/12/2020.
 */
public class Async {

	public static <T> DeferredResult<T> deferred(T v) {
		DeferredResult<T> d = new DeferredResult<T>();
		d.setResult(v);
		return d;
	}

}
