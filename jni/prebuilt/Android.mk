LOCAL_PATH := $(call my-dir)

include $(CLEAR_VARS)
LOCAL_MODULE    := forcetv
LOCAL_SRC_FILES := libforcetv.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := CloudTV
LOCAL_SRC_FILES := libCloudTV.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := add
LOCAL_SRC_FILES := libadd.so
include $(PREBUILT_SHARED_LIBRARY)

include $(CLEAR_VARS)
LOCAL_MODULE    := rtmpserver
LOCAL_SRC_FILES := librtmpserver.so
include $(PREBUILT_SHARED_LIBRARY)



