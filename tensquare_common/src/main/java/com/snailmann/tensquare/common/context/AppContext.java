package com.snailmann.tensquare.common.context;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class AppContext implements ApplicationContextAware {

    private static ApplicationContext applicationContext;

    public static Object getBean(String name) {
        return applicationContext.getBean(name);
    }

    public static <T> T getBean(Class<T> clazz) {
        return applicationContext.getBean(clazz);
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        AppContext.applicationContext = applicationContext;
    }
}
