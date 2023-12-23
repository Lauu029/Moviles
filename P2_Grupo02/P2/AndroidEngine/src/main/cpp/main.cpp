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