# IDE 版本过低导致启动tomcat报错
## 描述
将项目部署好后，启动tomcat后报错，java.lang.NoClassDefFoundError: org/apache/juli/logging/LogFactory
报这个错说明你用的是tomcat7.

## 原因
IDE 内核不支持.

## 解决办法
- 打开myeclipse，Preferentces->MyEclipse->Servers->Tomcat->Tomcat 6.x,载入Tomcat7，在"Paths"下，点击“Add JAR/ZIP”,加载tomcat7下的……/bin/tomcat-juli.jar。点击“OK”.

- 设置环境变量添加classpath到CATALINE_HOME/bin/tomcat-juli.jar，Tomcat默认启动会自动加载. classpath=……;%CATALINA_HOME%\bin\tomcat-juli.jar;

