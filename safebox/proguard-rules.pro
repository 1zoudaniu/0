# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in D:\workSoft\Android\sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

#-keep public class * extends android.app.Activity
#-keep public class * extends android.app.Application
#-keep public class * extends android.app.Service
#-keep public class * extends android.content.BroadcastReceiver
#-keep public class * extends android.content.ContentProvider
#-keep public class * extends android.support.v4.app.FragmentActivity
#-keep public class * extends android.support.v4.app.Fragment
#-keep class * extends java.lang.annotation.Annotation { *; }
#
#
#-keep class cn.lkhealth.epos.pub.common.SerialPort {*;}
#
#
#-keepattributes Signature
#
#
#-keep class sun.misc.Unsafe { *; }
#-keep class com.google.gson.stream.** { *; }
#-keep class com.google.gson.examples.android.model.** { *; }
#-keep class com.google.gson.** { *;}
#
#
#-keep class cn.lkhealth.epos.about.bean.**{ *; }
#-keep class cn.lkhealth.epos.cashier.bean.**{ *; }
#-keep class cn.lkhealth.epos.order.bean.**{ *; }
#-keep class cn.lkhealth.epos.pub.bean.**{ *; }
