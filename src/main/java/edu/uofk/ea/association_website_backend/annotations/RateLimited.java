package edu.uofk.ea.association_website_backend.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RateLimited {
    String key() default "general";
    int capacity() default 10; // Max requests allowed in the burst
    int refillTokens() default 10; // How many tokens to add back
    int refillDuration() default 60; // Time in seconds to refill
    boolean exponentialBackoff() default false; // If true, wait time increases on repeated violations
}