package io.github.campanula.utils.tripartite.proxy.assign;

import io.github.campanula.utils.exception.CampanulaRuntimeException;
import io.github.campanula.utils.method.CListUtil;
import io.github.campanula.utils.proxy.CAbstractBeforeProxyHandle;
import io.github.campanula.utils.proxy.param.CProxyBeforeParam;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 在掉用方法之前进行对代理对象字段赋值
 * 在方法上要加
 * @see CFieldAssign
 * @param <T> 要处理的对象类型
 */
public class CFieldAssignHandle<T> extends CAbstractBeforeProxyHandle<T> {

    private Map<String, CFieldAssignMethod<?>> value;

    /**
     * 实例方法 需要传入要赋值的字段以及赋值的方法 不能为空
     * @param getValueMethod 字段以及字段赋值的方法
     * @throws CampanulaRuntimeException 有空或者重发会抛出
     */
    public CFieldAssignHandle(final List<CFieldAssignMethod<?>> getValueMethod) {
        final Map<String, CFieldAssignMethod<?>> value = new HashMap<>();
        CListUtil.consumeThrows(() -> getValueMethod,
                list -> {
                    for (CFieldAssignMethod<?> method : list) {
                        if (value.containsKey(method.getFieldName()))
                            throw new CampanulaRuntimeException("There are duplicates in the assignment method, is : " + method.getFieldName());
                        else
                            value.put(method.getFieldName(), method);
                    }
                },
                () -> new CampanulaRuntimeException("The assignment method cannot be null"));
        this.value = value;
    }

    @Override
    protected void operate(final CProxyBeforeParam<T> beforeParam) {
        try {
            realize(beforeParam);
        }
        catch (Exception e) {
            throw new CampanulaRuntimeException(e);
        }
    }

    private void realize(final CProxyBeforeParam<T> beforeParam) throws Exception {
        Method method = beforeParam.getMethod();
        CFieldAssign annotation = method.getAnnotation(CFieldAssign.class);
        if (annotation == null) return;

        String[] fieldName = annotation.fieldName();
        if (fieldName.length < 1)
            throw new CampanulaRuntimeException("The field to assign was not selected");

        for (String f : fieldName) {
            if (!value.containsKey(f))
                throw new CampanulaRuntimeException("lack " + f + " assign method");
        }

        T proxy = beforeParam.getProxy();
        for (String f : fieldName) {
            Field declaredField = proxy.getClass().getDeclaredField(f);
            declaredField.setAccessible(true);
            declaredField.set(proxy, this.value.get(f).getFieldValue());
        }
    }
}
