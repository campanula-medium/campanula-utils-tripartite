# 基于JDK8封装的一些工具类(依赖于三方jar)

> 基于[campanula-utils](https://github.com/li-ze-lin/campanula-utils)项目扩展 引入该项目同时连campanula-utils一起引入

## 引入
```xml
<!-- https://mvnrepository.com/artifact/io.github.campanula-medium/utils-tripartite -->
<dependency>
    <groupId>io.github.campanula-medium</groupId>
    <artifactId>utils-tripartite</artifactId>
    <version>1.0.0</version>
</dependency>
```

## 基于cglib的代理模式进行封装(继承于campanula-utils的CAbstractProxyHandler具体参考[campanula-utils](https://github.com/li-ze-lin/campanula-utils))
- [io.github.campanula.utils.tripartite.proxy.cglib.CInvocationHandler](https://github.com/li-ze-lin/campanula-utils-tripartite/blob/master/src/main/java/io/github/campanula/utils/tripartite/proxy/cglib/CInvocationHandler.java)
- [io.github.campanula.utils.tripartite.proxy.cglib.CEntityProxyFactory](https://github.com/li-ze-lin/campanula-utils-tripartite/blob/master/src/main/java/io/github/campanula/utils/tripartite/proxy/cglib/CEntityProxyFactory.java)

------
该项目只扩展了这一项暂时