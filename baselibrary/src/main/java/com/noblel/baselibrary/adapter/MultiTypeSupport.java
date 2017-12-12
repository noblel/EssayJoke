package com.noblel.baselibrary.adapter;

/**
 * @author Noblel
 * 多布局支持
 */
public interface MultiTypeSupport<T> {
    int getLayoutId(T item);
}
