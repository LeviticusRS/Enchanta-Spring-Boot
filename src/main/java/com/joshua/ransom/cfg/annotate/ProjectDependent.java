package com.joshua.ransom.cfg.annotate;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Joshua Ransom on 3/3/2020.
 *
 * Will make the controller method require an authenticated session, and puts the
 * account in the "account" key of the model.
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ProjectDependent {
}
