package com.ls;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;


@WebListener
public class MyListener implements ServletContextListener {

    public MyListener() {
        // TODO Auto-generated constructor stub
    	System.out.println("리스너 객체 생성");
    }


    public void contextDestroyed(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	System.out.println("컨테이너 안에 서블릿이 소멸하는 이벤트가 발생했을 때");
    }

    public void contextInitialized(ServletContextEvent sce)  { 
         // TODO Auto-generated method stub
    	System.out.println("컨테이너 안에 서블릿이 초기화되었다는 이벤트가 발생했을 때");
    }
	
}