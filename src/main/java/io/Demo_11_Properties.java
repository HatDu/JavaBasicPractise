package io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;
import java.util.Set;

public class Demo_11_Properties {
    public static void main(String[] args) throws Exception{
        Properties props = new Properties();
        addProps(props);
        // print(props);
        String sv_path = "src/main/resources/io/data_demo_11.txt";
        save(props,sv_path);
        Properties pl = load(sv_path);
        print(pl);
    }

    /**
     * 添加属性
     * @param props
     */
    public static void addProps(Properties props){
        props.setProperty("url", "jdbc:mysql://localhost:3306/test");
        props.setProperty("username", "root");
        props.setProperty("password", "1234");
        props.setProperty("driver", "com.mysql.jdbc.driver");
    }

    /**
     * 打印属性
     * @param props
     */
    public static void print(Properties props){
        Set<String> propNames = props.stringPropertyNames();
        for(String key : propNames){
            System.out.println(key + ":" + props.getProperty(key));
        }
    }

    /**
     * 保存属性
     * @param props
     * @param path
     */
    public static void save(Properties props, String path) throws Exception{
        FileOutputStream fos = new FileOutputStream(path);
        props.store(fos, "注释");
        fos.close();
    }

    /**
     * 加载并打印属性
     * @param path
     * @throws Exception
     */
    public static Properties load(String path) throws Exception{
        FileInputStream fis = new FileInputStream(path);
        Properties pro = new Properties();
        pro.load(fis);
        fis.close();
        return pro;
    }
}
