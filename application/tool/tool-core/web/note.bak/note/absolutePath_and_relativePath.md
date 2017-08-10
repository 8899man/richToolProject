# ava绝对路径及相对路径总结
* **基本概念**
    - 绝对路径
    绝对路径就是你的主页上的文件或目录在硬盘上真正的路径，(URL和物理路径)例如：
    C:xyz est.txt 代表了test.txt文件的绝对路径。http://www.sun.com/index.htm也代表了一个URL绝对路径.
    - 相对路径
    相对与某个基准目录的路径。包含Web的相对路径（HTML中的相对目录），例如：在
    Servlet中，"/"代表Web应用的跟目录。和物理路径的相对表示。例如："./" 代表当前目录,"../"代表上级目录。这种类似的表示，也是属于相对路径。

* **关于JSP/Servlet**
    - 服务器的地址
    服务器端的相对地址指的是相对于你的web应用的地址，这个地址是在服务器端解析的（不同于html和javascript中的相对地址，他们是由客户端浏览器解析的）也就是说这时候在jsp和servlet中的相对地址应该是相对于你的web应用，即相对于http: //192.168.0.1/webapp/的。
    forward：servlet中的request.getRequestDispatcher(address);这个address是在服务器端解析的，所以，你要forward到a.jsp应该这么写：request.getRequestDispatcher(“/toolUser/a.jsp”)这个/ 相对于当前的web应用webapp，其绝对地址就是：http://192.168.0.1/webapp/toolUser/a.jsp。 sendRedirect：在jsp中<%response.sendRedirect("/rtccp/toolUser/a.jsp");%>
    - 客户端地址
    所有的html页面中的相对地址都是相对于服务器根目录(http://192.168.0.1/)的，而不是(跟目录下的该Web应用的目录) http://192.168.0.1/webapp/的。 Html中的form表单的action属性的地址应该是相对于服务器根目录(http://192.168.0.1/)的，所以，如果提交到a.jsp 为：action＝"/webapp/toolUser/a.jsp"或action="<%=request.getContextPath()% >"/toolUser/a.jsp；提交到servlet为actiom＝"/webapp/handleservlet" Javascript也是在客户端解析的，所以其相对路径和form表单一样。

      因此，一般情况下，在JSP/HTML页面等引用的CSS,Javascript.Action等属性前面最好都加上
    <%=request.getContextPath()%>,以确保所引用的文件都属于Web应用中的目录。另外，应该尽量避免使用类似".","./","../../"等类似的相对该文件位置的相对路径，这样当文件移动时，很容易出问题。

* **JSP/Servlet中获得当前应用的相对路径和绝对路径**
    - JSP中获得当前应用的相对路径和绝对路径
    根目录所对应的绝对路径:request.getRequestURI();
    文件的绝对路径:application.getRealPath(request.getRequestURI());
    当前web应用的绝对路径:application.getRealPath("/");
    取得请求文件的上层目录:newFile(application.getRealPath(request.getRequestURI())).getParent()

    - Servlet中获得当前应用的相对路径和绝对路径
    根目录所对应的绝对路径:request.getServletPath();
    文件的绝对路径:request.getSession().getServletContext().getRealPath
    (request.getRequestURI())
    当前web应用的绝对路径:servletConfig.getServletContext().getRealPath("/");
    (ServletContext对象获得几种方式：javax.servlet.http.HttpSession.getServletContext();javax.servlet.jsp.PageContext.getServletContext();javax.servlet.ServletConfig.getServletContext();)

* **java 的Class中获得相对路径，绝对路径的方法**
    - 单独的Java类中获得绝对路径
    根据java.io.File的Doc文挡，可知:
    默认情况下new File("/")代表的目录为：System.getProperty("toolUser.dir")。
    ``` java
        package org.cheng.file;

        import java.io.File;

        public class FileTest {
            public static void main(String[] args) throws Exception {
                System.out.println(Thread.currentThread().getContextClassLoader().getResource(""));

                System.out.println(FileTest.class.getClassLoader().getResource(""));

                System.out.println(ClassLoader.getSystemResource(""));
                System.out.println(FileTest.class.getResource(""));
                System.out.println(FileTest.class.getResource("/"));
                //Class文件所在路径
                System.out.println(new File("/").getAbsolutePath());
                System.out.println(System.getProperty("toolUser.dir"));
            }
        }
    ```

    - 服务器中的Java类获得当前路径
        - WebLogic
        WebApplication的系统文件根目录是你的weblogic安装所在根目录。
        例如：如果你的weblogic安装在c:eaweblogic700.....
        那么，你的文件根路径就是c:.
        所以，有两种方式能够让你访问你的服务器端的文件：
            - a.使用绝对路径：
            比如将你的参数文件放在c:yourconfigyourconf.properties，
            直接使用 new FileInputStream("yourconfig/yourconf.properties");
            - b.使用相对路径：
            相对路径的根目录就是你的webapplication的根路径，即WEB-INF的上一级目录，将你的参数文件放在yourwebappyourconfigyourconf.properties,
            这样使用：new FileInputStream("./yourconfig/yourconf.properties");

        - Tomcat
        在类中输出System.getProperty("toolUser.dir");显示的是%Tomcat_Home%/bin

        - Resin
        在类中输出System.getProperty("toolUser.dir");显示的是%Tomcat_Home%/bin

        - 如何读相对路径哪
        在Java文件中getResource或getResourceAsStream均可
        例：getClass().getResourceAsStream(filePath);//filePath可以是"/filename",这里的/代表web
        发布根路径下WEB-INF/classes,默认使用该方法的路径是：WEB-INF/classes

* **读取文件时的相对路径，避免硬编码和绝对路径的使用**
    - 采用Spring的DI机制获得文件，避免硬编码.
    http://www.javajia.net/viewtopic.php?p=90213&

    - 配置文件的读取
    http://dev.csdn.net/develop/article/39/39681.shtm

    - 通过虚拟路径或相对路径读取一个xml文件，避免硬编码
    http://club.gamvan.com/club/clubPage.jsp?iPage=1&tID=10708&ccID=8

* **Java中文件的常用操作（复制，移动，删除，创建等）**
    - 常用的操作类
    http://www.easydone.cn/014/200604022353065155.htm
    - Java JSP文件操作大全
    http://www.pconline.com.cn/pcedu/empolder/gj/java/0502/559401.html
    - java文件操作详解
    http://www.51cto.com/html/2005/1108/10947.htm
    - JAVA 如何创建删除修改复制目录及文件
    http://www.gamvan.com/developer/java/2005/2/264.html

* **URI,的RFC标准文挡**
