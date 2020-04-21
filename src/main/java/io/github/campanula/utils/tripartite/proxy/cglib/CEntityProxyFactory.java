package io.github.campanula.utils.tripartite.proxy.cglib;

import io.github.campanula.utils.exception.CampanulaRuntimeException;
import io.github.campanula.utils.proxy.CAbstractAfterProxyHandle;
import io.github.campanula.utils.proxy.CAbstractBeforeProxyHandle;
import net.sf.cglib.proxy.Enhancer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 获取cglib代理对象工厂
 */
public class CEntityProxyFactory {

    private CEntityProxyFactory() {}

    public static CEntityProxyFactory newCEntityProxyFactory() {
        return new CEntityProxyFactory();
    }

    /**
     * 获取代理后的对象
     * @param entity 要代理的对象
     * @param <T> 要代理的对象类型
     * @return 返回传入实现接口类型代理后的对象
     * @throws CampanulaRuntimeException CampanulaRuntimeException
     */
    public <T> T proxy(T entity) {
        return proxyPlus(entity, null, null);
    }

    /**
     * 获取代理后的对象
     * @param entity 要代理的对象
     * @param before 要在方法执行之前进行处理的逻辑
     * @param <T> 要代理的对象类型
     * @return 返回传入实现接口类型代理后的对象
     * @throws CampanulaRuntimeException CampanulaRuntimeException
     */
    public <T> T proxyBeforePlus(T entity, CAbstractBeforeProxyHandle<T> before) {
        return proxyPlus(entity, before, null);
    }

    /**
     * 获取代理后的对象
     * @param entity 要代理的对象
     * @param after 要在方法执行完进行处理的逻辑
     * @param <T> 要代理的对象类型
     * @return 返回传入实现接口类型代理后的对象
     * @throws CampanulaRuntimeException CampanulaRuntimeException
     */
    public <T> T proxyAfterPlus(T entity, CAbstractAfterProxyHandle<T> after) {
        return proxyPlus(entity, null, after);
    }

    /**
     * 获取代理后的对象
     * @param entity 要代理的对象
     * @param before 要在方法执行之前进行处理的逻辑
     * @param after 要在方法执行完进行处理的逻辑
     * @param <T> 要代理的对象类型
     * @return 返回传入实现接口类型代理后的对象
     * @throws CampanulaRuntimeException CampanulaRuntimeException
     */
    public <T> T proxyPlus(T entity, CAbstractBeforeProxyHandle<T> before, CAbstractAfterProxyHandle<T> after) {
        return getProxy(entity, before, after);
    }

    /**
     * 获取代理后的对象集合
     * @param entity 要代理的对象集合
     * @param <T> 要代理的对象类型
     * @return 返回代理后的对象集合
     * @throws CampanulaRuntimeException CampanulaRuntimeException
     */
    public <T> List<T> proxy(List<T> entity) {
        return proxyPlusList(entity, null, null);
    }

    /**
     * 获取代理后的对象集合
     * @param entity 要代理的对象集合
     * @param before 要在方法执行之前进行处理的逻辑
     * @param <T> 要代理的对象类型
     * @return 返回代理后的对象集合
     * @throws CampanulaRuntimeException CampanulaRuntimeException
     */
    public <T> List<T> proxyBeforePlus(List<T> entity, CAbstractBeforeProxyHandle<T> before) {
        return proxyPlusList(entity, before, null);
    }

    /**
     * 获取代理后的对象集合
     * @param entity 要代理的对象集合
     * @param after 要在方法执行完进行处理的逻辑
     * @param <T> 要代理的对象类型
     * @return 返回代理后的对象集合
     * @throws CampanulaRuntimeException CampanulaRuntimeException
     */
    public <T> List<T> proxyAfterPlus(List<T> entity, CAbstractAfterProxyHandle<T> after) {
        return proxyPlusList(entity, null, after);
    }

    /**
     * 获取代理后的对象集合
     * @param entity 要代理的对象集合
     * @param before 要在方法执行之前进行处理的逻辑
     * @param after 要在方法执行完进行处理的逻辑
     * @param <T> 要代理的对象类型
     * @return 返回代理后的对象集合
     * @throws CampanulaRuntimeException CampanulaRuntimeException
     */
    public <T> List<T> proxyPlusList(List<T> entity, final CAbstractBeforeProxyHandle<T> before, final CAbstractAfterProxyHandle<T> after) {
        if (entity == null || !entity.isEmpty()) throw new CampanulaRuntimeException("The proxy collection is empty or has no surrogate elements");
        return entity.stream().map(data -> this.getProxy(data, before, after)).collect(Collectors.toList());
    }

    /**
     * 获取代理后的对象
     * @param t 要代理的对象
     * @param before 要在方法执行之前进行处理的逻辑
     * @param after 要在方法执行完进行处理的逻辑
     * @param <T> 要代理的对象类型
     * @return 返回传入实现接口类型代理后的对象
     * @throws CampanulaRuntimeException CampanulaRuntimeException
     */
    private <T> T getProxy(T t, CAbstractBeforeProxyHandle<T> before, CAbstractAfterProxyHandle<T> after) {
        Class<?> aClass = t.getClass();
        Enhancer enhancer = new Enhancer();
        enhancer.setClassLoader(aClass.getClassLoader());
        enhancer.setSuperclass(aClass);
        enhancer.setCallback(new CInvocationHandler<T>(t, before, after));
        return (T) enhancer.create();
    }
}
