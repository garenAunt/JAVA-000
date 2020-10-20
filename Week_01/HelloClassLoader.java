package gjava.week01;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class HelloClassLoader extends ClassLoader {
    @Override
    public Class findClass(String name) {
        byte[] b = new byte[0];
        try {
            b = loadClassFromFile(name);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return defineClass("Hello", b, 0, b.length);
    }

    /**
     * 读取class文件，转化内容为字节流
     */
    private byte[] loadClassFromFile(String fileName) throws IOException {
        System.out.println(fileName);
        File file = new File(fileName);
        byte[] bytes = new byte[(int) file.length()];
        new FileInputStream(file).read(bytes);

        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = (byte)(255 - bytes[i]);
        }

        return bytes;
    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InstantiationException, InvocationTargetException {
        String path = "C:\\Users\\Garen\\Desktop\\java\\Hello.xlass".replace("\\", "/");
        HelloClassLoader loader = new HelloClassLoader();
        Class hello = loader.loadClass(path);

        Method method = hello.getMethod("hello");
        method.invoke(hello.getDeclaredConstructor().newInstance());
    }
}
