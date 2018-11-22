package io.left.core.util.lib.gson;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * To exclude a particular field from a data class to be serialized by Gson.
 * Great help from here: https://stackoverflow.com/a/27986860/1259540
 * We can move it to framework if we decide to use Gson for serialization
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Exclude {
}