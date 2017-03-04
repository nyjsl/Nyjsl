package org.nyjsl.reflect;

/**
 * Created by pc on 2017/3/2.
 */

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 首先要明白静态代理,即普通的代理模式
 */
public class DynamicProxy {


    public static void main(String[] args) {

        UserInterface ui = new UserServiceImpl();

        final UserInterface uiProxy =
                (UserInterface) Proxy.
                        newProxyInstance(ui.getClass().getClassLoader()
                                , ui.getClass().getInterfaces(),
                                new MyInvocationHandler(ui));
        uiProxy.getName(111);
        uiProxy.getAge(111);

    }


    public static class MyInvocationHandler implements InvocationHandler{

        private Object target;

        public MyInvocationHandler(Object target) {
            this.target = target;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            if ("getName".equals(method.getName())){
                System.out.println("++++++before " + method.getName() + "++++++");
                Object result = method.invoke(target,args);
                System.out.println("++++++after " + method.getName() + "++++++");
                return result;
            }else{
                Object result = method.invoke(target, args);
                return result;
            }
        }
    }


    public static class UserServiceImpl implements UserInterface{


        @Override
        public String getName(int id) {
            System.out.println("getName = [" + id + "]");
            return "Tom";
        }

        @Override
        public Integer getAge(int id) {
            System.out.println("getAge = [" + id + "]");
            return 10;
        }
    }


    public interface UserInterface{

        public String getName(int id);

        public Integer getAge(int id);
    }

}


