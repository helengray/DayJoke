# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in F:\Gray\android\sdk/tools/proguard/proguard-android.txt
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

-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-keepattributes Signature
-dontpreverify
-keepattributes Exceptions

-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver

-dontwarn android.support.v4.**
-keep class android.support.v4.** { *; }
-keep public class * extends android.support.v4.**
-keep public class * extends android.app.Fragment

-dontwarn com.google.gson.**
-keep class com.google.gson.** { *; }
-keep public class * extends com.google.gson.**

#不混淆 Entity
-keepclassmembers class * extends com.helen.dayjoke.entity.BaseEn{
	*;
}

-keep class com.helen.dayjoke.entity.**{
    *;
}

-keep class * implements android.os.Parcelable {
public static final android.os.Parcelable$Creator *;
}

# fresco
-keep,allowobfuscation @interface com.facebook.common.internal.DoNotStrip

# Do not strip any method/class that is annotated with @DoNotStrip
-keep @com.facebook.common.internal.DoNotStrip class *
-keepclassmembers class * {
    @com.facebook.common.internal.DoNotStrip *;
}

# Keep native methods
-keepclassmembers class * {
    native <methods>;
}

-dontwarn okio.**
-dontwarn javax.annotation.**
#有米广告sdk
-dontwarn net.youmi.android.**
-keep class net.youmi.android.** {
    *;
}

-keep class okhttp3.** {
 *;
}
-keep class okio.** {
 *;
}

-dontwarn retrofit2.**
-keep class retrofit2.** { *; }

-dontwarn rx.**
-keep class rx.** {
 *;
}

-dontwarn com.jaredrummler.materialspinner.**
-keep class com.jaredrummler.materialspinner.** {
 *;
}
#友盟
-keepclassmembers class * {
   public <init> (org.json.JSONObject);
}
-keep public class com.helen.dayjoke.R$*{
public static final int *;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}