package com.gg.crashmonitor.crash

import android.app.Application
import android.os.MessageQueue.IdleHandler
import android.os.Looper

object CrashMonitor : IdleHandler {
    private var mApplication: Application? = null
    fun init(application: Application?) {
        Looper.myQueue().addIdleHandler(this)
        mApplication = application
    }

    override fun queueIdle(): Boolean {
        // 监听所有的 Activity
        mApplication!!.registerActivityLifecycleCallbacks(LifecycleCallback.getInstance())
        val activityManager = ActivityManager()
        val processFinisher = ProcessFinisher(mApplication!!, true, activityManager)
        Thread.setDefaultUncaughtExceptionHandler { t: Thread?, e: Throwable ->
            // 先走 bugly 逻辑然后再走这里
            // 少了没写上传崩溃信息到服务器
            // 如果自己写一套，这在里应该先上报到自己的后台服务器
//            CrashReport.postCatchedException(e, t) // bugly会将这个throwable上报

            //阿里云日志上报 crash 异常，需要及时提醒
//            ALiYunLogManager.push(logLevel = LogPush.LogLevel_Crash, logDesc = e.message ?: "", tr = e, isAlarm = LogPush.Alarm_Push)


            e.printStackTrace()
            // 1. 把所有的状态信息清空，service ，activity 这些尽量都干掉
            // 2. 然后退到首页，但是不闪退到桌面 （记录所有的 activity）
            processFinisher.finishLastActivity(t)
            // 3. 也不触发系统的检测（提示卸载 app），不走系统的默认逻辑
            processFinisher.endApplication()
        }
        return false
    }

}