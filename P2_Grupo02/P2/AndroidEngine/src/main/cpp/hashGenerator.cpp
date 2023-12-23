#include <jni.h>

// Write C++ code here.
//
// Do not forget to dynamically load the C++ library into your application.
//
// For instance,
//
// In MainActivity.java:
//    static {
//       System.loadLibrary("AndroidEngine");
//    }
//
// Or, in MainActivity.kt:
//    companion object {
//      init {
//         System.loadLibrary("AndroidEngine")
//      }
//    }
extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_androidengine_NDKManager_generateHash(JNIEnv *env, jclass clazz, jstring data) {
    // TODO: implement generateHash()
}