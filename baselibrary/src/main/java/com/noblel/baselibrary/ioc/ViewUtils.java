package com.noblel.baselibrary.ioc;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author Noblel
 */

public class ViewUtils {

    public static void inject(Activity activity) {
        inject(new ViewFinder(activity), activity);
    }

    public static void inject(View view) {
        inject(new ViewFinder(view), view);
    }

    public static void inject(View view, Object object) {
        inject(new ViewFinder(view), object);
    }

    /**
     * 兼容上面三个方法
     *
     * @param finder
     * @param object 反射执行的类
     */
    private static void inject(ViewFinder finder, Object object) {
        injectFiled(finder, object);
        injectEvent(finder, object);
    }

    /**
     * 事件的注入
     *
     * @param finder
     * @param object
     */
    private static void injectEvent(ViewFinder finder, Object object) {
        //1.获取类里面所有方法
        Class<?> clazz = object.getClass();
        Method[] methods = clazz.getDeclaredMethods();
        //2.获取OnClick的value值
        for (Method method : methods) {
            OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null) {
                int[] viewIds = onClick.value();
                for (int viewId : viewIds) {
                    //3.findViewById找到View
                    View view = finder.findViewById(viewId);
                    //拓展功能
                    boolean isCheckNet = method.getAnnotation(CheckNet.class) != null;
                    if (view != null) {
                        //4.view.setOnClickListener
                        view.setOnClickListener(new DeclaredOnClickListener(method, object, isCheckNet));
                    }
                }
            }
        }
    }

    /**
     * findViewById注入
     *
     * @param finder
     * @param object
     */
    private static void injectFiled(ViewFinder finder, Object object) {
        //1.获取类里面所有属性
        Class<?> clazz = object.getClass();
        Field[] declaredFields = clazz.getDeclaredFields();
        //2.获取ViewById里面的value值
        for (Field declaredField : declaredFields) {
            ViewById viewById = declaredField.getAnnotation(ViewById.class);
            if (viewById != null) {
                //获取注解里面的id值
                int viewId = viewById.value();
                //3.findViewById找到View
                View view = finder.findViewById(viewId);
                if (view != null) {
                    //能够注入所有修饰符
                    declaredField.setAccessible(true);
                    //4.动态的注入找到View
                    try {
                        declaredField.set(object, view);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private static class DeclaredOnClickListener implements View.OnClickListener {

        private Object mObject;
        private Method mMethod;
        private boolean mIsCheckNet;

        public DeclaredOnClickListener(Method method, Object object, boolean isCheckNet) {
            this.mMethod = method;
            this.mObject = object;
            this.mIsCheckNet = isCheckNet;
        }

        @Override
        public void onClick(View v) {
            //是否需要检测网络
            if (mIsCheckNet) {
                if (!networkAvailable(v.getContext())) {
                    //打印Toast
                    Toast.makeText(v.getContext(), "网络不太给力", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            try {
                mMethod.setAccessible(true);
                //5.反射执行方法
                mMethod.invoke(mObject, v);
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    mMethod.invoke(mObject);
                } catch (Exception e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private static boolean networkAvailable(Context context) {
        try {
            //得到连接管理器对象
            ConnectivityManager connectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo activeNetworkInfo = connectivityManager
                    .getActiveNetworkInfo();
            if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
