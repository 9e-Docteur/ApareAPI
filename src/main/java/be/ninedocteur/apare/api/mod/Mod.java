package be.ninedocteur.apare.api.mod;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Mod {
    Class<? extends ApareMod> value();
    ModSide modSide() default ModSide.BOTH;
}

