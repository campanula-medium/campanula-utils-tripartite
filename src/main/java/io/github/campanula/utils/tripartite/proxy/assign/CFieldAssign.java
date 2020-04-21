package io.github.campanula.utils.tripartite.proxy.assign;

import java.lang.annotation.*;

/**
 * 用于在方法上面可以回填类的字段数据
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface CFieldAssign {

    /**
     * 要回调的字段名
     */
    String[] fieldName();
}
