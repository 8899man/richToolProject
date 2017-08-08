package pers.posse.tool.service.impl.JDBC;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by posse on 17-7-20.
 */
public class InitHibernate {
    /***
     * Hibernate运行过程：
        1.通过Configuration().configure();读取并解析hibernate.cfg.xml配置文件
        2.由hibernate.cfg.xml中的<mappingresource="com/xx/domain.hbm.xml"/>读取并解析映射信息
        3.通过config.buildSessionFactory();//创建SessionFactory
        4.sessionFactory.openSession();//打开Sesssion
        5.session.beginTransaction();//创建事务Transation
        6.persistent operate持久化操作 //一般指Save这个方法
        7.session.getTransaction().commit();//提交事务
        8.关闭Session
        9.关闭SesstionFactory

     * Hibernate的体系结构:
        1.SessionFactory ： 这是Hibernate的关键对象，它是单个数据库映射关系经过编译后的内存镜像，也是线程安全的。
            它是生成Session的工厂，本身需要依赖于ConnectionProvider。该对象可以在进程或集群的级别上，
            为那些事务之间可以重用的数据提供可选的二级缓存.
        2.Session ： 它是应用程序与持久存储层之间交互操作的一个单线程对象。它也是Hibernate持久化操作的关键，
            所有的持久化对象必须在Session管理下才可以进行持久化操作。此对象生存期河段。它底层封装了JDBC连接，
            它也是Transaction的工厂。Session对象持有必选的一级缓存，在显式执行flush之前，所有持久化操作的数据都在缓存中的Session对象处。
        3.持久化对象（Persistent Object） ： 系统创建的POJO实例，一旦与特定的Session关联，并对应数据表的指定记录，该对象就处于持久化状态，
            这一系列对象都被称为持久化兑现。在程序中对持久化对象执行的修改，都将自动被转换为对持久层的修改。
            持久化对象完全可以是普通的JavaBean/POJO，唯一的区别是它们正与一个Session关联。
        4.瞬态对象和脱管对象 ： 系统通过new关键字创建的Java实例，没有与Session相关联，此时处于瞬态。瞬态实例可能是在被应用程序实例化后，
            尚未进行持久化的对象。如果一个曾经持久化过的实例，如果Session被关闭则转换为脱管状态。
        5.事务（Transaction） ： 代表一次原子操作，它具有数据库事务的概念。Hibernate事务是对底层具体的JDBC、JTA以及CORBA事务的抽象。在某些情况下，
            一个Session之内可能包含多个Transaction对象。虽然事务操作是可选的，但所有持久化操作都应该在事务管理下进行，即使是只读操作。
        6.连接提供者（ConnectionProvider） ： 它是生成JDBC连接的工厂，它通过抽象将应用程序与底层的DataSource或DriverManager隔离开。
            这个对象无须应用程序直接访问，仅在应用程序需要扩展时使用。
            注：在实际应用中很少会直接使用DriverManager来获取数据库连接，通常都会使用DataSource来获取数据库连接，
                因此ConnectionProvider通常由DataSource充当。由于SessionFactory底层封装了ConnectionProvider，
                因此在实际应用中SessionFactory底层封装了DataSource。
        7.事务工厂（TransactionFactory） ： 它是生成Transaction对象实例的工厂。该对象也无须应用程序直接访问。
            它负责对底层具体的事务实现进行封装，将底层具体的事务抽象成Hibernate事务。
     */

    public static void main(String[] args) {
        init();
    }


    private static void init()
    {
        /**
         * 1.由于hibernate.properties作为配置文件时，没有提供添加Hibernate持久化类的方式，
         *  因此必须调用Configuration对象的addAnnotatedClass()方法添加持久化类。
         *  Configuration对象可调用addAnnotatedClass()方法逐个地添加持久化类.
         *
         * 2.可调用addPackage()方法添加指定包下的所有持久化类.
         *
         * 3.使用hibernate.cfg.xml配置文件可以通过<mapping.../>子元素添加Hibernate持久化类，因此无须通过编程方式添加持久化类.
         *  configure()方法将会负责加载hibernate.cfg.xml文件
         *
         * 4.不使用配置文件, coding添加configuration的属性, 硬编码方式
         *  Configuration setProperties(Properties properties) : 用于为Configuration对象设置一系列属性，这一系列属性通过Properties实例传入。
         *  Configuration setProperty(String propertyName,String value) : 用于为Configuration对象设置一个单独的属性。
         */
//        Configuration configuration1 = new Configuration().configure().addAnnotatedClass(Object.class).addAnnotatedClass(String.class).addAnnotatedClass(Long.class);
//        Configuration configuration2 = new Configuration().configure().addPackage("pers.posse.tool.service.impl.domain");
//        // 指定配置文件,默认"hibernate.cfg.xml"
//        Configuration configuration3 = new Configuration().configure("hibernate.cfg.xml");
//        Configuration configuration4 = new Configuration().configure().addAnnotatedClass(Object.class).setProperty("hibernate.connection.driver_class","com.mysql.jdbc.Driver")
//                .setProperty("hibernate.connection.url", "jdbc:mysql:///hibernate")
//                .setProperty("hibernate.connection.username", "root")
//                .setProperty("hibernate.connection.password", "root");

        // ServiceRegistry? ServiceRegistry 是 Service 的注册表, 它为Service提供了一个统一的加载 / 初始化 / 存放 / 获取机制.

        /**
         * Hibernate Core 4.0 , 构造 SessionFactory 的方法已经不推荐使用了
         * Hibernate ORM 4 里面推荐的方式是 org.hibernate.cfg.Configuration#buildSessionFactory(ServiceRegistry serviceRegistry),
         * 需要先构造一个 ServiceRegistry 对象
         */
        Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                .applySettings(configuration.getProperties()).build();
        SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);


        //openSession() 和 getCurrentSession() 方法主要有两个区别:
        //1.getCurrentSession在事务提交或回滚之后会自动关闭,而openSession需要手动关闭,
        //  如果使用openSession而没有手动关闭,多次之后会导致连接池溢出.
        //2.openSession每次都创建新的session对象, getCurrentSession使用现有的session对象.
        Session session = sessionFactory.openSession();
//
//        Student student = new Student();
//        session.beginTransaction();
//        session.save(student);
//        session.getTransaction().commit();
//        session.close();
    }
}
