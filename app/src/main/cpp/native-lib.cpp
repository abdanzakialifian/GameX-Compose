#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_games_gamex_data_di_NetworkModule_gameApiKey(JNIEnv *env, jobject) {
    std::string apiKey = "8a3a5fce65fd47008dbffaa2a0470c0b";
    return env->NewStringUTF(apiKey.c_str());
}