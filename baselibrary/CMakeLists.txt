# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.

cmake_minimum_required(VERSION 3.4.1)

#设置生成的so动态库最后输出的路径
set(CMAKE_LIBRARY_OUTPUT_DIRECTORY ${PROJECT_SOURCE_DIR}/libs/${ANDROID_ABI})

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

#配置头文件路径
include_directories(${PROJECT_SOURCE_DIR}/src/main/jni/bsdiff/)

#添加源文件路径
aux_source_directory(${PROJECT_SOURCE_DIR}/src/main/jni/bsdiff/ CORE_SRC_LIST)
list(APPEND SRC_LIST ${CORE_SRC_LIST})

#添加libbspatch 库
add_library( # Sets the name of the library.
             bspatch
             SHARED
             ${PROJECT_SOURCE_DIR}/src/main/jni/bsdiff/huffman.c
             ${PROJECT_SOURCE_DIR}/src/main/jni/bsdiff/randtable.c
             ${PROJECT_SOURCE_DIR}/src/main/jni/bsdiff/crctable.c
             ${PROJECT_SOURCE_DIR}/src/main/jni/bsdiff/blocksort.c
             ${PROJECT_SOURCE_DIR}/src/main/jni/bsdiff/bzip2.c
             ${PROJECT_SOURCE_DIR}/src/main/jni/bsdiff/compress.c
             ${PROJECT_SOURCE_DIR}/src/main/jni/bsdiff/decompress.c
             ${PROJECT_SOURCE_DIR}/src/main/jni/bsdiff/bzlib.c
             ${PROJECT_SOURCE_DIR}/src/main/jni/bsdiff/bspatch.c)

#***************************************************************************************************

add_library( # Sets the name of the library.
             diffpatch
             SHARED
             ${PROJECT_SOURCE_DIR}/src/main/jni/diffpatch.cpp)

target_link_libraries( # Specifies the target library.
                       diffpatch
                       jnigraphics
                       bspatch
                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )

#***************************************************************************************************

add_library( # Sets the name of the library.
             compressimg

             # Sets the library as a shared library.
             SHARED

             # Provides a relative path to your source file(s).
             src/main/jni/compress_image.cpp )

# 添加libjpeg.so依赖库
add_library(jpeg SHARED IMPORTED)

#设置libjpeg.so依赖库路径
set_target_properties(
  jpeg
  PROPERTIES IMPORTED_LOCATION
  ${PROJECT_SOURCE_DIR}/libs/${ANDROID_ABI}/libjpeg.so)


# Searches for a specified prebuilt library and stores the path as a
# variable. Because CMake includes system libraries in the search path by
# default, you only need to specify the name of the public NDK library
# you want to add. CMake verifies that the library exists before
# completing its build.

find_library( # Sets the name of the path variable.
              log-lib

              # Specifies the name of the NDK library that
              # you want CMake to locate.
              log )

# Specifies libraries CMake should link to your target library. You
# can link multiple libraries, such as libraries you define in this
# build script, prebuilt third-party libraries, or system libraries.

target_link_libraries( # Specifies the target library.
                       compressimg
                       jnigraphics
                       jpeg
                       # Links the target library to the log library
                       # included in the NDK.
                       ${log-lib} )

