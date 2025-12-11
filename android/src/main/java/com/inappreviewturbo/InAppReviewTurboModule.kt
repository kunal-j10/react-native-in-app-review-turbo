package com.inappreviewturbo

import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.module.annotations.ReactModule
import com.google.android.play.core.review.ReviewManagerFactory
import com.google.android.play.core.review.ReviewException
import android.content.pm.PackageManager

@ReactModule(name = InAppReviewTurboModule.NAME)
class InAppReviewTurboModule(reactContext: ReactApplicationContext) :
  NativeInAppReviewTurboSpec(reactContext) {

  override fun getName(): String {
    return NAME
  }

  override fun isAvailable(promise: Promise) {
    try {
      val pm = reactApplicationContext.packageManager
      val playStoreInstalled = try {
        pm.getPackageInfo("com.android.vending", 0)
        true
      } catch (e: PackageManager.NameNotFoundException) {
        false
      }

      if (!playStoreInstalled) {
        promise.resolve(false)
        return
      }

      try {
        ReviewManagerFactory.create(reactApplicationContext)
        promise.resolve(true)
      } catch (e: Exception) {
        promise.resolve(false)
      }

    } catch (e: Exception) {
      promise.reject("ERR_AVAILABILITY", e.message)
    }
  }

  override fun request(promise: Promise) {
    try {
      val manager = ReviewManagerFactory.create(reactApplicationContext)

      val requestFlow = manager.requestReviewFlow()
      requestFlow.addOnCompleteListener { task ->
        if (!task.isSuccessful) {
          promise.resolve(false)
          return@addOnCompleteListener
        }

        val activity = currentActivity
        if (activity == null) {
          promise.reject("NO_ACTIVITY", "No current activity available")
          return@addOnCompleteListener
        }

        val reviewInfo = task.result
        val flow = manager.launchReviewFlow(activity, reviewInfo)
        flow.addOnCompleteListener {
          promise.resolve(true)
        }
      }
    } catch (e: ReviewException) {
      promise.reject("ERR_INAPP_REVIEW", e.message)
    } catch (e: Exception) {
      promise.reject("ERR_INAPP_REVIEW", e.message)
    }
  }

  companion object {
    const val NAME = "InAppReviewTurbo"
  }
}
