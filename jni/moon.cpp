#include <jni.h>
#include <string.h>

 extern "C" {
     JNIEXPORT jstring JNICALL Java_com_moon_android_live_custom007_Configs_getHttpsServer(JNIEnv * env, jobject obj);
 };

 extern "C" {
     JNIEXPORT jstring JNICALL Java_com_moon_android_live_custom007_Configs_getHttpsServer02(JNIEnv * env, jobject obj);
 };

 extern "C" {
     JNIEXPORT jstring JNICALL Java_com_moon_android_live_custom007_Configs_getHttpsServer03(JNIEnv * env, jobject obj);
 };

 extern "C" {
      JNIEXPORT jstring JNICALL Java_com_moon_android_live_custom007_Configs_getHttpServer01(JNIEnv * env, jobject obj);
  };

 JNIEXPORT jstring JNICALL Java_com_moon_android_live_custom007_Configs_getHttpsServer(JNIEnv * env, jobject obj)
 {
     return env->NewStringUTF("http://23.89.145.178:9023");
 }

 JNIEXPORT jstring JNICALL Java_com_moon_android_live_custom007_Configs_getHttpsServer02(JNIEnv * env, jobject obj)
 {
     return env->NewStringUTF("http://23.89.145.178:9023");
 }


 JNIEXPORT jstring JNICALL Java_com_moon_android_live_custom007_Configs_getHttpsServer03(JNIEnv * env, jobject obj)
  {
      return env->NewStringUTF("http://23.89.145.178:9023");
     // http://hk3.ibcde.net:9011
  }


 JNIEXPORT jstring JNICALL Java_com_moon_android_live_custom007_Configs_getHttpServer01(JNIEnv * env, jobject obj)
  {
      return env->NewStringUTF("http://23.89.145.178:9023");
  }
