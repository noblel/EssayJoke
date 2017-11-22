package com.noblel.framelibrary.skin;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import com.noblel.framelibrary.skin.attrs.SkinAttr;
import com.noblel.framelibrary.skin.attrs.SkinType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Noblel
 *         皮肤属性解析支持类
 */
public class SkinAttrSupport {
    private static final String TAG = SkinAttrSupport.class.getSimpleName();

    /**
     * 获取SkinAttr的属性
     *
     * @param context
     * @param attrs
     * @return
     */
    public static List<SkinAttr> getSkinAttrs(Context context, AttributeSet attrs) {
        //background src textColor
        List<SkinAttr> skinAttrs = new ArrayList<>();
        int attributeCount = attrs.getAttributeCount();
        for (int i = 0; i < attributeCount; i++) {
            //获取名字和值
            String attributeName = attrs.getAttributeName(i);
            String attributeValue = attrs.getAttributeValue(i);
//            Log.i(TAG, "attributeName -> " + attributeName + " ,attributeValue -> " + attributeValue);
            SkinType skinType = getSkinType(attributeName);
            if (skinType != null) {
                String resName = getResName(context, attributeValue);
                if (TextUtils.isEmpty(resName)) continue;
                SkinAttr skinAttr = new SkinAttr(resName, skinType);
                skinAttrs.add(skinAttr);
            }
        }
        return skinAttrs;
    }

    /**
     * 获取资源名称(@12345679)
     *
     * @param context
     * @param attributeValue
     * @return
     */
    private static String getResName(Context context, String attributeValue) {
        if (attributeValue.startsWith("@")) {
            int resId = Integer.parseInt(attributeValue.substring(1));
            return context.getResources().getResourceEntryName(resId);
        }
        return null;
    }

    /**
     * 通过名称获取SkinType
     *
     * @param attributeName
     * @return
     */
    private static SkinType getSkinType(String attributeName) {
        SkinType[] skinTypes = SkinType.values();
        for (SkinType skinType : skinTypes) {
            if (skinType.getResName().equals(attributeName))
                return skinType;
        }
        return null;
    }
}
