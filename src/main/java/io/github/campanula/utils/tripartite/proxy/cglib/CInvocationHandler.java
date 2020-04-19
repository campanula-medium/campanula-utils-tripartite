package io.github.campanula.utils.tripartite.proxy.cglib;

import io.github.campanula.utils.exception.CampanulaRuntimeException;
import io.github.campanula.utils.proxy.CAbstractAfterProxyHandle;
import io.github.campanula.utils.proxy.CAbstractBeforeProxyHandle;
import io.github.campanula.utils.proxy.CAbstractProxyHandler;
import io.github.campanula.utils.proxy.param.CProxyAfterParam;
import io.github.campanula.utils.proxy.param.CProxyBeforeParam;

import java.lang.reflect.Method;

public class CInvocationHandler<T> extends CAbstractProxyHandler<T> {

    public CInvocationHandler(T t, CAbstractBeforeProxyHandle<T> before, CAbstractAfterProxyHandle<T> after) {
        super(t, before, after);
    }

    //@Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        Object invoke = null;

        try {

            if (this.before != null) {
                this.before.setParam(new CProxyBeforeParam<T>(t, method, args));
                CProxyBeforeParam<T> execute = (CProxyBeforeParam<T>)this.before.execute();
                t = execute.getProxy();
                method = execute.getMethod();
                args = execute.getArgs();
            }

            invoke = method.invoke(t, args);

            if (this.after != null) {
                this.after.setParam(new CProxyAfterParam<T>(t, method, args, invoke));
                CProxyAfterParam<T> execute = (CProxyAfterParam<T>)this.after.execute();
                t = execute.getProxy();
                invoke = execute.getResult();
            }

        }
        catch (Exception e) {
            throw new CampanulaRuntimeException(e);
        }

        return invoke;
    }

}
