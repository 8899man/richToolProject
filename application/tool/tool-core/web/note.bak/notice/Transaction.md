## 代码环境

Spring4.3.7 + Hibernate5.1.4 + Jpa

## 意图

配置声明式事务.
Spring 事务管理中, 开启注解驱动< tx:annotation-driven />

## 问题

使用默认基于代理的方式配置, 事务生效, 想要使用基于aspectj 模式,  配置, Service代码都无误, 但是就是报错.
jars :

- spring-aspects
- spring-aspects
- aspectjrt
``` xml
<tx:annotation-driven transaction-manager="transactionManager" mode="aspectj" />
```
### 错误信息
```
javax.persistence.TransactionRequiredException: No EntityManager with actual transaction available for current thread - cannot reliably process 'persist' call
	at org.springframework.orm.jpa.SharedEntityManagerCreator$SharedEntityManagerInvocationHandler.invoke(SharedEntityManagerCreator.java:282)
	at com.sun.proxy.$Proxy33.persist(Unknown Source)

```

### 血泪
跟踪代码发现, EntityManager 拿不到事务, 却想做persist 操作, spring 换成4.0.0, 不会报错, 但是事务无效, 不会真正执行保存操作, 这个问题折磨了我好几天, 最后终于从同事那儿get 到.

### 原因
点开mode="aspectj", 发现Spring有这么一段
> AspectJ weaving requires spring-aspects.jar on the classpath,
as well as load-time weaving (or compile-time weaving) enabled.

也就是说使用spring-aspects.jar 的aspectj, 需要指定编织时间, 使用编译时织入.
aspectj 是静态织入的, 也就是编译期织入、类加载期织入(LTW), 编译期织入可以采用编辑器(maven, ant)等工具, 类加载器可以通过类加载器，在类字节码加载到JVM时，织入切面.
这里提供通过maven,指定spring-aspectj通过编译时织入事务代码, 在pom.xml添加
```
<plugin>
    <groupId>org.codehaus.mojo</groupId>
    <artifactId>aspectj-maven-plugin</artifactId>
    <version>${aspectj-maven-plugin.version}</version>
    <dependencies>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjrt</artifactId>
            <version>${aspectj.version}</version>
        </dependency>
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjtools</artifactId>
            <version>${aspectj.version}</version>
        </dependency>
    </dependencies>
    <configuration>
        <complianceLevel>1.8</complianceLevel>
        <source>1.8</source>
        <target>1.8</target>
    </configuration>
    <executions>
        <execution>
            <goals>
                <goal>compile</goal>
                <goal>test-compile</goal>
            </goals>
        </execution>
    </executions>
</plugin>
```
编译完成后, 在class文件可以看到 service 中加入以下代码

```
AnnotationTransactionAspect var10000 = AnnotationTransactionAspect.aspectOf();
...
var10000.ajc$around$org_springframework_transaction_aspectj_AbstractTransactionAspect$1$2a73e96c(this, new UserService$AjcClosure1(var4), ajc$tjp_0);
```
这就是aspectj自动完成代码编织, 加入事务管理.

做插入修改,操作,无误,不再报错.