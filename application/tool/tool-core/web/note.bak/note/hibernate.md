## 环境

Spring4 + hibernate5 + jpa

## 坑一

### 描述
打算使用es, 所以给hibernate 配置了拦截器, 如下

``` java
    <bean id="entityManagerFactory" class="org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean">
        <property name="persistenceUnitName" value="persistenceUnit"/>
        <property name="persistenceUnitManager" ref="persistenceUnitManager"/>
        <property name="dataSource" ref="dataSource" />
        <property name="jpaVendorAdapter">
            <bean id="jpaVendorAdapter" class="org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter">
                <property name="database" value="MYSQL"/>
            </bean>
        </property>
        <property name="jpaDialect">
            <bean class="org.springframework.orm.jpa.vendor.HibernateJpaDialect"/>
        </property>
        <property name="jpaPropertyMap">
            <map>
                <entry key="hibernate.ejb.interceptor.session_scoped">
                    <value>per.posse.tool.interceptor.HibernateSessionInterceptor</value>
                </entry>
            </map>
        </property>
    </bean>
```
HibernateSessionInterceptor 实现了 org.hibernate.Interceptor 接口

### 问题

RETRIEVE, CREATE, CREATE, 全无问题, 但是UPDATE 操作毫无反应, 数据不更新, 也不报错, 也不打印update的sql.

### 原因及解决

HibernateSessionInterceptor 具体实现还没写, 编译器默认生成Interceptor接口的实现方法, 有个方法叫findDirty(), 编译器默认实现空方法是

``` java
    @Override
    public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
            String[] propertyNames, Type[] types) {
        return new int[0];
    }
```

查看描述
> Called from <tt>flush()</tt>. The return value determines whether the entity is updated
    <ul>
        <li>an array of property indices - the entity is dirty
        <li>an empty array - the entity is not dirty
        <li><tt>null</tt> - use Hibernate's default dirty-checking algorithm
    </ul>

也就是hibernate 在执行 flush() 操作的时候, 会调用这个方法, 返回值决定实体是否被update,返回值有值, 实体被改写, 需要更新持久化上下文, 返回值是空数组, hibernate不认为实体被改动, 不执行更新操作,返回null, 则由hibernate 默认的dirty-checking 机制判断是否需要更新, 它有自己的一套DirtyCheckEventListener, DirtyCheckEvent机制.

所以编译器给我默认返回的new int[0], hibernate 认为实体不需要更新, 所以就没有执行update, 所以把返回先改成null 就好了, 再做update操作, 打印sql, 数据库更新,
OK.
