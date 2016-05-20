LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := moon

include $(BUILD_SHARED_LIBRARY)



LOCAL_SHARED_LIBRARIES := forcetv
include $(LOCAL_PATH)/prebuilt/Android.mk