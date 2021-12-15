package com.zhang.cloud.component;

/**
 * @author 98549
 * @date 2021/12/5 15:33
 */
public class MyClassLoader extends ClassLoader
{
    public MyClassLoader() {
        super(Thread.currentThread().getContextClassLoader());
    }

    public Class<?> defineClassForName(String name, byte[] data) {
        return this.defineClass(name, data, 0, data.length);
    }
    public Class<?> defineClassPublic(String name, byte[] b, //
                                      int off, int len) throws ClassFormatError {
        Class<?> clazz = defineClass(name, b, off, len);
        return clazz;
    }
}
