package io.github.campanula.utils.tripartite.proxy.cglib;

import io.github.campanula.utils.exception.CampanulaRuntimeException;
import io.github.campanula.utils.proxy.CAbstractAfterProxyHandle;
import io.github.campanula.utils.proxy.CAbstractBeforeProxyHandle;
import net.sf.cglib.proxy.Enhancer;

import java.util.List;
import java.util.stream.Collectors;

public class CEntityProxyFactory {

    private CEntityProxyFactory() {}

    public static CEntityProxyFactory newCEntityProxyFactory() {
        return new CEntityProxyFactory();
    }

    public <T> T proxy(T entity) {
        return proxyPlus(entity, null, null);
    }

    public <T> T proxyBeforePlus(T entity, CAbstractBeforeProxyHandle<T> before) {
        return proxyPlus(entity, before, null);
    }

    public <T> T proxyAfterPlus(T entity, CAbstractAfterProxyHandle<T> after) {
        return proxyPlus(entity, null, after);
    }

    public <T> T proxyPlus(T entity, CAbstractBeforeProxyHandle<T> before, CAbstractAfterProxyHandle<T> after) {
        return getProxy(entity, before, after);
    }

    public <T> List<T> proxy(List<T> entity) {
        return proxyPlusList(entity, null, null);
    }

    public <T> List<T> proxyBeforePlus(List<T> entity, CAbstractBeforeProxyHandle<T> before) {
        return proxyPlusList(entity, before, null);
    }

    public <T> List<T> proxyAfterPlus(List<T> entity, CAbstractAfterProxyHandle<T> after) {
        return proxyPlusList(entity, null, after);
    }

    public <T> List<T> proxyPlusList(List<T> entity, final CAbstractBeforeProxyHandle<T> before, final CAbstractAfterProxyHandle<T> after) {
        if (entity == null || !entity.isEmpty()) throw new CampanulaRuntimeException("The proxy collection is empty or has no surrogate elements");
        return entity.stream().map(data -> this.getProxy(data, before, after)).collect(Collectors.toList());
    }

    private <T> T getProxy(T t, CAbstractBeforeProxyHandle<T> before, CAbstractAfterProxyHandle<T> after) {
        Class<?> aClass = t.getClass();
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(aClass.getClassLoader());
        enhancer.setSuperclass(aClass);
        enhancer.setCallback(new CInvocationHandler<T>(t, before, after));
        return (T) enhancer.create();
    }
}
