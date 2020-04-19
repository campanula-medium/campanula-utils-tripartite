package io.github.campanula.utils.tripartite.proxy.assign;

import java.lang.annotation.*;

@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CFieldAssign {

    String[] fieldName();
}
