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

    public T proxy(T entity) {
        return proxyPlus(entity, entityInterfaces, null, null);
    }

    public T proxyBeforePlus(T entity, CAbstractBeforeProxyHandle<T> before) {
        return proxyPlus(entity, entityInterfaces, before, null);
    }

    public T proxyAfterPlus(T entity, CAbstractAfterProxyHandle<T> after) {
        return proxyPlus(entity, entityInterfaces, null, after);
    }

    public T proxyPlus(T entity, CAbstractBeforeProxyHandle<T> before, CAbstractAfterProxyHandle<T> after) {
        return getProxy(entity, entityInterfaces, before, after);
    }

    public <T> List<T> proxy(List<T> entity) {
        return proxyPlusList(entity, entityInterfaces, null, null);
    }

    public <T> List<T> proxyBeforePlus(List<T> entity, CAbstractBeforeProxyHandle<T> before) {
        return proxyPlusList(entity, entityInterfaces, before, null);
    }

    public <T> List<T> proxyAfterPlus(List<T> entity, CAbstractAfterProxyHandle<T> after) {
        return proxyPlusList(entity, entityInterfaces, null, after);
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
        enhancer.setClassLoader(new CInvocationHandler<T>(t, before, after));
        return (I) enhancer.create();
    }
}
