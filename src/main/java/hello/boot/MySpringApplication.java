package hello.boot;

import hello.spring.HelloConfig;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.startup.Tomcat;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

import java.util.List;

public class MySpringApplication {

    public static void run(Class configClass, String[] args) {
        System.out.println("MySpringApplication.run() args => " + List.of(args));

        // Tomcat 설정
        Connector connector = new Connector();
        connector.setPort(8080);

        Tomcat tomcat = new Tomcat();
        tomcat.setConnector(connector);

        // Spring Container 생성
        AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
        appContext.register(configClass);

        // Spring MVC DispatcherServlet 생성, Spring container 연동
        DispatcherServlet dispatcherServlet = new DispatcherServlet(appContext);

        // DispatcherServlet 을 ServletContainer 에 등록
        Context context = tomcat.addContext("", "/");
        tomcat.addServlet("", "dispatcher", dispatcherServlet);
        context.addServletMappingDecoded("/", "dispatcher");

        try {
            tomcat.start();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }
}
