package com.choong.extern.api.intent

import android.content.Intent
import android.net.Uri

class ExternIntentCreator {


    private fun createGooglePlaycIntent(uri: Uri): Intent {
        return Intent(Intent.ACTION_VIEW).apply {
            data = uri
            setPackage(GOOGLE_PLAY_PACKAGE_NAME)
        }
    }

    public fun createGooglePlayDetail(packageName: String): Intent {
        return createGooglePlaycIntent( Uri.parse(GOOGLE_PLAY_DETAIL_LINK).buildUpon().apply {
            appendQueryParameter("id", packageName)
        }.build())
    }

    public fun createLaunchInstant(packageName: String, launch: Boolean, referrer: String): Intent {
        return createGooglePlaycIntent(
            Uri.parse(GOOGLE_PLAY_DETAIL_LINK).buildUpon().apply {
                appendQueryParameter("id", packageName)
                appendQueryParameter("launch", launch.toString())

                if (referrer.isNotBlank()) {
                    appendQueryParameter("referrer", referrer)
                }
            }.build()
        )
    }

    companion object {
        private const val GOOGLE_PLAY_DETAIL_LINK = "https://play.google.com/store/apps/details"
        private const val GOOGLE_PLAY_PACKAGE_NAME = "com.android.vending"
    }
}