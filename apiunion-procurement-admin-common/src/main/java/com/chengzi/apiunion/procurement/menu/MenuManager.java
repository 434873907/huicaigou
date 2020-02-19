package com.chengzi.apiunion.procurement.menu;

import org.springframework.core.io.ClassPathResource;
import org.summercool.util.Jaxb2Marshaller;
import org.summercool.util.StackTraceUtil;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author 月汐
 * @date 2018/10/25 20:24
 */
public class MenuManager {

    private Root root;

    private ClassPathResource menuConfig;

    public void init() {
        try {
            InputStream inputStream = menuConfig.getInputStream();
            root = Jaxb2Marshaller.unmarshal(inputStream, Root.class);
        } catch (IOException exception) {
            System.out.println(StackTraceUtil.getStackTrace(exception));
        }
    }

    public Root getRoot() {
        return root;
    }

    public void setMenuConfig(ClassPathResource menuConfig) {
        this.menuConfig = menuConfig;
    }

}
