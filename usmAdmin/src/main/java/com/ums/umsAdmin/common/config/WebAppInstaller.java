package com.ums.umsAdmin.common.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.WebApplicationInitializer;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * web配置
 */
public class WebAppInstaller implements WebApplicationInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebAppInstaller.class);

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {

        String servletInfo = servletContext.getServerInfo();
        LOGGER.info("容器信息：" + servletInfo);
        if (servletInfo.toLowerCase().contains("tomcat")){
            String tomcatPort = System.getProperty("tomcatPort");
            if (!StringUtils.isEmpty(tomcatPort)){
//                ServiceRegister.setPort(tomcatPort);
            }
            else {
//                ServiceRegister.setPort("8080");
                LOGGER.warn("未能取得端口号");
            }
        }else if (servletInfo.toLowerCase().contains("jetty")){
//            ServiceRegister.setPort("9010");
        }else {
//            ServiceRegister.setPort("80");
        }
        try {
            Enumeration<NetworkInterface> e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()){
                NetworkInterface ni = e.nextElement();
                Enumeration<InetAddress> eIPs = ni.getInetAddresses();
                while (eIPs.hasMoreElements()){
                    InetAddress ip4 = eIPs.nextElement();
                    if (ip4 instanceof Inet4Address){
                        String ip = ip4.getHostAddress();
                        if (0 == ip.indexOf("10.")) {
                            String host = ip4.getHostName();
                            LOGGER.info("ip:" + ip);
                            LOGGER.info("host:" + host);
//                            ServiceRegister.setIp(ip);
//                            ServiceRegister.setHost(host);
                        }
                    }
                }
            }
        } catch (SocketException e) {
            LOGGER.error("获取网卡信息失败");
        }
        
        String host = System.getProperty("env_host");
//        ServiceRegister.setHost(host);
        LOGGER.debug("获取ENV_HOST:{}", host);
        
//        //1、spring上下文
//        AnnotationConfigWebApplicationContext springContext = new AnnotationConfigWebApplicationContext();
//        springContext.register(SpringConfig.class);
//        servletContext.addListener(new ContextLoaderListener(springContext));
//
//        //2、mvc上下文
//        AnnotationConfigWebApplicationContext mvcContext = new AnnotationConfigWebApplicationContext();
//        mvcContext.register(MVCConfig.class);
//
//        //3、DispatcherServlet
//        DispatcherServlet dispatcherServlet = new DispatcherServlet(mvcContext);
//
//        ServletRegistration.Dynamic dynamic = servletContext.addServlet("GQ-MVC-Servlet", dispatcherServlet);
//        dynamic.setLoadOnStartup(1);
//        dynamic.addMapping("/");
//        dynamic.setMultipartConfig(new MultipartConfigElement(""));
//        dynamic.setInitParameter("spring.profiles.active", "container");
    }
}