package com.noblel.baselibrary.fix;

import android.content.Context;
import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

import dalvik.system.BaseDexClassLoader;

import static android.content.Context.MODE_PRIVATE;

/**
 * @author Noblel
 */
public class FixDexManager {
    private static final String TAG = "FixDexManager";
    private Context mContext;
    private File mDexDir;

    public FixDexManager(Context context) {
        this.mContext = context;
        //获取应用可以访问的dex目录
        this.mDexDir = context.getDir("odex", MODE_PRIVATE);
    }

    /**
     * 修复dex包
     */
    public void fix(String fixPath) throws Exception {
        //2.获取下载好的补丁dexElements
        File srcFile = new File(fixPath);
        if (!srcFile.exists()) {
            throw new FileNotFoundException(fixPath);
        }
        File destFile = new File(mDexDir, srcFile.getName());
        if (destFile.exists()) {
            Log.d(TAG, "patch [" + fixPath + "] has be loaded.");
            return;
        }
        //2.1 copy到系统能够访问的dex目录下
        copyFile(srcFile, destFile);
        //2.2 ClassLoader读取fixDex路径
        List<File> fixDexFiles = new ArrayList<>();
        fixDexFiles.add(destFile);
        fixDexFiles(fixDexFiles);
    }

    private void injectDexElements(ClassLoader classLoader, Object dexElements) throws Exception {
        //1.先获取PathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);
        //2.获取PathList里面的dexElements
        Field dexElementsField = pathList.getClass().getDeclaredField("dexElements");
        dexElementsField.setAccessible(true);
        dexElementsField.set(pathList, dexElements);
    }

    /**
     * @param arrayLhs 前数组
     * @param arrayRhs 后数组
     * @return 合并结果
     */
    private static Object combineArray(Object arrayLhs, Object arrayRhs) {
        //拿到类的泛型
        Class<?> loadClass = arrayLhs.getClass().getComponentType();

        int i = Array.getLength(arrayLhs);
        int j = i + Array.getLength(arrayRhs);
        Object result = Array.newInstance(loadClass, j);
        for (int k = 0; k < j; k++) {
            if (k < i) {
                Array.set(result, k, Array.get(arrayLhs, k));
            } else {
                Array.set(result, k, Array.get(arrayRhs, k - i));
            }
        }
        return result;
    }

    /**
     * 从ClassLoader中获取dexElements
     *
     * @param classLoader
     * @return
     */
    private Object getDexElementsByClassLoader(ClassLoader classLoader) throws Exception {
        //1.先获取PathList
        Field pathListField = BaseDexClassLoader.class.getDeclaredField("pathList");
        pathListField.setAccessible(true);
        Object pathList = pathListField.get(classLoader);
        //2.获取PathList里面的dexElements
        Field dexElements = pathList.getClass().getDeclaredField("dexElements");
        dexElements.setAccessible(true);
        return dexElements.get(pathList);
    }

    private static void copyFile(File src, File dest) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            if (!dest.exists()) {
                dest.createNewFile();
            }
            inChannel = new FileInputStream(src).getChannel();
            outChannel = new FileOutputStream(dest).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } finally {
            if (inChannel != null) {
                inChannel.close();
            }
            if (outChannel != null) {
                outChannel.close();
            }
        }
    }

    /**
     * 加载全部的修复包
     */
    public void loadFixDex() throws Exception {
        File[] dexFiles = mDexDir.listFiles();
        List<File> fixDexFiles = new ArrayList<>();
        for (File dexFile : dexFiles) {
            if (dexFile.getName().endsWith(".dex"))
                fixDexFiles.add(dexFile);
        }
        fixDexFiles(fixDexFiles);
    }

    /**
     * 修复dex
     *
     * @param fixDexFiles
     */
    private void fixDexFiles(List<File> fixDexFiles) throws Exception {
        //1.先获取已经运行的dexElements
        ClassLoader appClassLoader = mContext.getClassLoader();
        Object dexElements = getDexElementsByClassLoader(appClassLoader);

        File optimizedDirectory = new File(mDexDir, "odex");
        if (!optimizedDirectory.exists()) {
            optimizedDirectory.mkdirs();
        }

        for (File fixDexFile : fixDexFiles) {
            //dexPath dex路径
            //optimizedDirectory  解压路径
            //librarySearchPath  .so文件的位置
            //ClassLoader  父ClassLoader
            ClassLoader fixDexClassLoader = new BaseDexClassLoader(
                    fixDexFile.getAbsolutePath(),//必须要在应用目录下的odex文件中
                    optimizedDirectory,
                    null, appClassLoader);
            Object fixDexElements = getDexElementsByClassLoader(fixDexClassLoader);
            //3.把补丁dexElement插到已经运行dexElement最前面,合并
            dexElements = combineArray(fixDexElements, dexElements);
        }

        //4.注入到原来的ClassLoader中
        injectDexElements(appClassLoader, dexElements);

    }
}
