package io.github.campanula.utils.tripartite.proxy.assign;

/**
 * @see CFieldAssignHandle 实例化的参数
 * @param <T> 当前字段要是的类型
 */
public abstract class CFieldAssignMethod<T> {

    /**
     * 要赋值的字段名
     */
    protected String fieldName;

    public CFieldAssignMethod(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * 赋值方法
     * @return 返回要赋值字段的数据
     */
    public abstract T getFieldValue();

    public String getFieldName() {
        return fieldName;
    }
}
