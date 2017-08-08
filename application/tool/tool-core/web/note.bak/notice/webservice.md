[TOC]
#Java JAX-WS和JAX-RS webservice

一直搞不清楚JAXB中一些概念, 这里记一下

##webService 三要素
Web Service是一种能够使应用程序在不同的平台使用不同的编程语言进行通讯的技术规范,而这种技术规范的实现可以用不同的方法,比如使用基于XML形式的协议（SOAP）进行通讯或者是RESTFUL形式的.
> SOAP、WSDL(WebServicesDescriptionLanguage)、UDDI(UniversalDescriptionDiscovery andIntegration)之一， soap用来描述传递信息的格式， WSDL 用来描述如何访问具体的接口， uddi用来管理，分发，查询webService 。具体实现可以搜索 Web Services简单实例 ; SOAP 可以和现存的许多因特网协议和格式结合使用，包括超文本传输协议（HTTP），简单邮件传输协议（SMTP），多用途网际邮件扩充协议（MIME）。它还支持从消息系统到远程过程调用（RPC）等大量的应用程序。SOAP使用基于XML的数据结构和超文本传输协议(HTTP)的组合定义了一个标准的方法来使用Internet上各种不同操作环境中的分布式对象。

### SOAP
- 封装:它定义了一个框架,该框架描述了消息中的内容是什么,谁应当处理它以及它是可选的还是必须的.
- 编码规则:它定义了一种序列化的机制，用于交换应用程序所定义的数据类型的实例.
- RPC表示:它定义了用于表示远程过程调用和应答的协定.
- 绑定:定义了一种使用底层传输协议来完成在节点间交换SOAP封装的约定.

##JAXB
> JAXB（Java Architecture for XML Binding) 是一个业界的标准,是一项可以根据XML Schema产生Java类的技术.该过程中,JAXB也提供了将XML实例文档反向生成Java对象树的方法,并能将Java对象树的内容重新写到XML实例文档.从另一方面来讲,JAXB提供了快速而简便的方法将XML模式绑定到Java表示,从而使得Java开发者在Java应用程序中能方便地结合XML数据和处理函数.
> JAXB能够使用Jackson对JAXB注解的支持实现(jackson-module-jaxb-annotations),既方便生成XML，也方便生成JSON，这样一来可以更好的标志可以转换为JSON对象的JAVA类。JAXB允许JAVA人员将JAVA类映射为XML表示方式，常用的注解包括：@XmlRootElement,@XmlElement等等. JAXB能够使用Jackson对JAXB注解的支持实现(jackson-module-jaxb-annotations),既方便生成XML,也方便生成JSON,这样一来可以更好的标志可以转换为JSON对象的JAVA类.JAXB允许JAVA人员将JAVA类映射为XML表示方式,常用的注解包括:@XmlRootElement,@XmlElement等等.

JAXB 2.0是JDK 1.6的组成部分。JAXB 2.2.3是JDK 1.7的组成部分

###JDK中JAXB相关的重要Class和Interface
- JAXBContext类，是应用的入口，用于管理XML/Java绑定信息。
- Marshaller接口，将Java对象序列化为XML数据。
- Unmarshaller接口，将XML数据反序列化为Java对象。

###JDK中JAXB相关的重要Annotation
- @XmlType，将Java类或枚举类型映射到XML模式类型
- @XmlAccessorType(XmlAccessType.FIELD) ，控制字段或属性的序列化。FIELD表示JAXB将自动绑定Java类中的每个非静态的（static）、非瞬态的（由@XmlTransient标注）字段到XML。其他值还有XmlAccessType.PROPERTY和XmlAccessType.NONE。
- @XmlAccessorOrder，控制JAXB 绑定类中属性和字段的排序。
- @XmlJavaTypeAdapter，使用定制的适配器（即扩展抽象类XmlAdapter并覆盖marshal()和unmarshal()方法），以序列化Java类为XML。
- @XmlElementWrapper ，对于数组或集合（即包含多个元素的成员变量），生成一个包装该数组或集合的XML元素（称为包装器）。
- @XmlRootElement，将Java类或枚举类型映射到XML元素。
- @XmlElement，将Java类的一个属性映射到与属性同名的一个XML元素。
- @XmlAttribute，将Java类的一个属性映射到与属性同名的一个XML属性。

## JAX-WS与JAX-RS

###全称
JAX-WS与JAX-RS两者是不同风格的SOA架构
> SOA(Service-oriented architecture) 面向服务的架构.前者以动词为中心,指定的是每次执行函数.而后者以名词为中心,每次执行的时候指的是资源.
> SOA服务具有平台独立的自我描述XML文档. Web服务描述语言(WSDL, Web Services Description Language)是用于描述服务的标准语言.
> SOA 服务用消息进行通信，该消息通常使用XML Schema来定义（也叫做XSD， XML Schema Definition）

- JAX-WS：全称是JavaTM API forXML-Based Web Services, 实现基于soap协议的Web Service提供的API，SOAP：简单对象访问协议，它是交换数据的一种协议规范，是一种轻量的、简单的、基于XML（标准通用标记语言下的一个子集）的协议.
- JAX-RS :全称是 JavaTM API forRESTful Web Services, 基于REST设计风格的WebServcice提供的API。它是一个Java 编程语言的应用程序接口，支持按照表述性状态转移(REST)架构风格创建Web服务.

###JAX-WS 和 JAX-RS 的应用场景

- jax-ws 是一套标准的soap协议,他是跨语言平台的,可移植性较高,目前在webService服务中是主流,通过服务端发布wsdl格式的xml文件,供客户端访问.
- jax-rs 是一套java 编程的应用程序风格,那他就不具备夸平台性,移植性单一.但是目前主流的访问都趋向于rest风格,和springMVC无缝衔接,同时它为dubbo提供了接近透明的REST调用支持.

###支持的框架

####JAX-WS
- CXF
- Axis
- Xfire

#####JAX-RS
- CXF —— XFire和Celtix的合并.
- Jersey —— Sun公司的JAX-RS参考实现.
- RESTEASY —— JBoss的JAX-RS项目.
- Restlet —— 也许是最早的REST框架,它在JAX-RS之前就有了.

### service 发布

> @ApiOperation(value = "Import external billing", response = ExternalOperateEntityResponse.class, httpMethod = "PUT", notes = "接口发布说明")
  @ApiParam(required = "是否必须参数", name = "参数名称", value = "参数具体描述")
  swagger http://blog.csdn.net/fansunion/article/details/51923720
  http://blog.csdn.net/gebitan505/article/details/51658643
  consumes： 指定处理请求的提交内容类型（Content-Type），例如application/json, text/html;
  produces: 指定返回的内容类型，仅当request请求头中的(Accept)类型中包含该指定类型才返回；



