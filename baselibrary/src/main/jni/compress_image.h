#ifndef ESSAYJOKE_COMPRESS_IMAGE_H
#define ESSAYJOKE_COMPRESS_IMAGE_H

#include <jni.h>
#include <string>

/**
 * Log打印
 */
#define LOG_TAG "jni"
#define LOG_W(...)  __android_log_print(ANDROID_LOG_WARN,LOG_TAG,__VA_ARGS__)
#define LOG_I(...) __android_log_print(ANDROID_LOG_INFO,LOG_TAG,__VA_ARGS__)
#define LOG_E(...) __android_log_print(ANDROID_LOG_ERROR,LOG_TAG,__VA_ARGS__)

#define true 1
#define false 0

/**
 * 统一编译方式
 */
#ifdef __cplusplus
extern "C"{
#endif

#include "jpeg/jpeglib.h"
#include "jpeg/cdjpeg.h"        /* Common decls for cjpeg/djpeg applications */
#include "jpeg/jversion.h"        /* for version message */
#include "jpeg/jconfig.h"


extern "C"
JNIEXPORT jint JNICALL
Java_com_noblel_baselibrary_utils_ImageUtil_jpegCompressBitmap2(JNIEnv *env, jclass type, jobject bitmap,
                                                      jint width, jint height, jstring path_);

extern "C"
JNIEXPORT jint JNICALL
Java_com_noblel_baselibrary_utils_ImageUtil_jpegCompressBitmap(JNIEnv *env, jclass type, jobject bitmap,
                                                             jint quality, jstring path_);
#ifdef __cplusplus
};
#endif


#endif //ESSAYJOKE_COMPRESS_IMAGE_H
