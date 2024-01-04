#include <jni.h>
#include "picosha2.h"
#include <vector>
#include <string>
#include <sstream>
#include <fstream>
using namespace std;
using namespace picosha2;

extern "C"
JNIEXPORT jstring JNICALL

Java_com_example_androidengine_NDKManager_generateHash(JNIEnv *env, jclass clazz, jstring data) {

    jboolean isCopy;
    const char *convertedValue = env->GetStringUTFChars(data, &isCopy);

    vector<unsigned char> hash(32);
    hash256(convertedValue, convertedValue+strlen(convertedValue), hash.begin(), hash.end());

    string hex_str = bytes_to_hex_string(hash.begin(), hash.end());
    env->ReleaseStringUTFChars(data,convertedValue);

    return env->NewStringUTF(hex_str.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_example_androidengine_NDKManager_generateHashHMAC(JNIEnv *env, jclass clazz,
                                                           jstring data) {

    //jstring codigo2= (jstring) "BUSTAMANTE";
    const char *convertedData = env->GetStringUTFChars(data, nullptr);
    const char *codigo1 = "GARBANZOS";

    // Concatenar las dos cadenas
    string concatenatedData = string(codigo1) + convertedData;

    jstring firstHash=Java_com_example_androidengine_NDKManager_generateHash(env, clazz, env->NewStringUTF(concatenatedData.c_str()));
    const char *convertedData2= env->GetStringUTFChars(firstHash, nullptr);
    const char *codigo2 = "BUSTAMANTE";

    string concatenatedData2 = string(codigo2) + convertedData2;
    return Java_com_example_androidengine_NDKManager_generateHash(env, clazz, env->NewStringUTF(concatenatedData2.c_str()));

}