package io.github.campanula.utils.tripartite.proxy.cglib;

import io.github.campanula.utils.exception.CampanulaRuntimeException;
import io.github.campanula.utils.proxy.CAbstractAfterProxyHandle;
import io.github.campanula.utils.proxy.CAbstractBeforeProxyHandle;

import java.util.List;
import java.util.stream.Collectors;

public class CEntityProxyFactory {

    private CEntityProxyFactory() {}

    public static CEntityProxyFactory newCEntityProxyFactory() {
        return new CEntityProxyFactory();
    }

    public <T extends I, I> I proxy(T entity, Class<I> entityInterfaces) {
        return proxyPlus(entity, entityInterfaces, null, null);
    }

    public <T extends I, I> I proxyBeforePlus(T entity, Class<I> entityInterfaces, CAbstractBeforeProxyHandle<T> before) {
        return proxyPlus(entity, entityInterfaces, before, null);
    }

    public <T extends I, I> I proxyAfterPlus(T entity, Class<I> entityInterfaces, CAbstractAfterProxyHandle<T> after) {
        return proxyPlus(entity, entityInterfaces, null, after);
    }

    public <T extends I, I> I proxyPlus(T entity, Class<I> entityInterfaces, CAbstractBeforeProxyHandle<T> before, CAbstractAfterProxyHandle<T> after) {
        return getProxy(entity, entityInterfaces, before, after);
    }

    public <T extends I, I> List<I> proxy(List<T> entity, Class<I> entityInterfaces) {
        return proxyPlusList(entity, entityInterfaces, null, null);
    }

    public <T extends I, I> List<I> proxyBeforePlus(List<T> entity, Class<I> entityInterfaces, CAbstractBeforeProxyHandle<T> before) {
        return proxyPlusList(entity, entityInterfaces, before, null);
    }

    public <T extends I, I> List<I> proxyAfterPlus(List<T> entity, Class<I> entityInterfaces, CAbstractAfterProxyHandle<T> after) {
        return proxyPlusList(entity, entityInterfaces, null, after);
    }

    public <T extends I, I> List<I> proxyPlusList(List<T> entity, final Class<I> entityInterfaces, final CAbstractBeforeProxyHandle<T> before, final CAbstractAfterProxyHandle<T> after) {
        if (entity == null || !entity.isEmpty()) throw new CampanulaRuntimeException("The proxy collection is empty or has no surrogate elements");
        return entity.stream().map(data -> this.getProxy(data, entityInterfaces, before, after)).collect(Collectors.toList());
    }

    private  <T extends I, I> I getProxy(T t, Class<I> entityInterfaces, CAbstractBeforeProxyHandle<T> before, CAbstractAfterProxyHandle<T> after) {
        Class<?> aClass = t.getClass();
        CInvocationHandler<T> handler = new CInvocationHandler(t, before, after);
        return null;//(I) Proxy.newProxyInstance(aClass.getClassLoader(), aClass.getInterfaces(), handler);
    }
}
