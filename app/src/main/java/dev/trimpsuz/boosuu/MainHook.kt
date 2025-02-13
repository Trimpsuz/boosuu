package dev.trimpsuz.boosuu

import android.os.Bundle
import de.robv.android.xposed.IXposedHookLoadPackage
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedBridge
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import de.robv.android.xposed.XSharedPreferences

class MainHook : IXposedHookLoadPackage {
    
    private var logging = false
    
    override fun handleLoadPackage(lpparam: XC_LoadPackage.LoadPackageParam) {
       if (lpparam.packageName != "com.busuu.android.enc") return

        try {
            val prefs = XSharedPreferences("dev.trimpsuz.boosuu", "app_preferences")
            logging = prefs.getBoolean("pref_enable_logging", false)
        } catch (_: Throwable) { }

        if(logging) XposedBridge.log("boosuu: Loaded app: " + lpparam.packageName);

        XposedHelpers.findAndHookMethod(
            "com.android.paywall.ui.PaywallActivity",
            lpparam.classLoader,
            "onCreate",
            Bundle::class.java,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    if(logging) XposedBridge.log("boosuu: Skipping PaywallActivity")
                    XposedHelpers.callMethod(param.thisObject, "onPaywallClosed")
                }
            }
        )

        XposedHelpers.findAndHookMethod(
            "com.android.ads.ui.IntermediateAdsScreenActivity",
            lpparam.classLoader,
            "onCreate",
            Bundle::class.java,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    if(logging) XposedBridge.log("boosuu: Skipping IntermediateAdsScreenActivity")
                    XposedHelpers.callMethod(param.thisObject, "I", 1006)
                }
            }
        )

        XposedHelpers.findAndHookMethod(
            "com.android.ads.ui.AdsActivity",
            lpparam.classLoader,
            "onCreate",
            Bundle::class.java,
            object : XC_MethodHook() {
                override fun afterHookedMethod(param: MethodHookParam) {
                    if(logging) XposedBridge.log("boosuu: Skipping AdsActivity")
                    XposedHelpers.callMethod(param.thisObject, "R", true)
                }
            }
        )

        XposedHelpers.findAndHookMethod(
            "com.busuu.android.base_ui.ui.bottombar.BottomBarActivity",
            lpparam.classLoader,
            "p0",
            Boolean::class.javaPrimitiveType,
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    if(logging) XposedBridge.log("boosuu: Hiding premium button from bottom bar")
                    param.args[0] = false
                }
            }
        )

        XposedHelpers.findAndHookMethod(
            "com.busuu.android.common.profile.model.a",
            lpparam.classLoader,
            "isPremium",
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    val instance = param.thisObject

                    if(instance != null) {
                        if(logging) XposedBridge.log("boosuu: Spoofing premium status")
                        try{
                            val B = XposedHelpers.getBooleanField(instance, "B")
                            if(!B) {
                                if(logging) XposedBridge.log("boosuu: Setting premium status")
                                XposedHelpers.callMethod(instance, "setPremium", true)
                            }
                            param.result = true;
                        } catch (t: Throwable) {
                            if(logging) XposedBridge.log(t.toString())
                            return;
                        }
                    }
                }
            }
        )

        XposedHelpers.findAndHookMethod(
            "com.busuu.android.common.profile.model.a",
            lpparam.classLoader,
            "getHasActiveSubscription",
            object : XC_MethodHook() {
                override fun beforeHookedMethod(param: MethodHookParam) {
                    val instance = param.thisObject

                    if(instance != null) {
                        if(logging) XposedBridge.log("boosuu: Spoofing subscription status")
                        try{
                            val G = XposedHelpers.getBooleanField(instance, "G")
                            if(!G) {
                                if(logging) XposedBridge.log("boosuu: Setting subscription status")
                                XposedHelpers.callMethod(instance, "setHasActiveSubscription", true)
                            }
                            param.result = true;
                        } catch (t: Throwable) {
                            if(logging) XposedBridge.log(t.toString())
                            return;
                        }
                    }
                }
            }
        )
    }
}