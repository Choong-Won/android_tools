package com.choong.extern.api.url

import android.net.Uri

class ExternUrlParser {
    private fun isONEstoreHost(host: String): Boolean {
        return regexONEstorePattern.matches(host)
    }

    private fun isONEstoreShortHost(host: String): Boolean {
        return regexONEstoreShortPattern.matches(host)
    }

    private fun isGoogleHost(host: String): Boolean {
        return regexGooglePattern.matches(host)
    }
    public fun parsingUrl(strUrl: String): UrlParseInfo {
        return  try {
            val uri: Uri = Uri.parse(strUrl);
            val uriScheme: String = uri.scheme ?: throw UnKnownInfoException("")
            when (uriScheme) {
                "tstore", "onestore" -> parseONEStoreUri(uri, strUrl)
                "intent" -> parseIntentUri(uri, strUrl)
                "market" -> parseMarketUri(uri, strUrl)
                "http", "https" -> parseWebUri(uri, strUrl)
                else -> throw UnKnownInfoException(uriScheme)
            }

        } catch (e: UnKnownInfoException) {
            return UrlParseInfo.UnknownUrlParseInfo(e.scheme, strUrl)
        } catch (e: Exception) {
            return UrlParseInfo.UnknownUrlParseInfo("", strUrl)
        }
    }

    private fun parseWebUri(uri: Uri, strUrl: String): UrlParseInfo {
        val uriHost = uri.host ?: throw UnKnownInfoException(uri.scheme!!)
        return when {
            isONEstoreHost(uriHost) -> parseONEStoreUri(uri, strUrl)
            isONEstoreShortHost(uriHost) -> parseONEStoreShortUri(uri, strUrl)
            isGoogleHost(uriHost) -> parseMarketUri(uri, strUrl)
            else -> UrlParseInfo.NormalWebUrlParseInfo(uri.scheme!!, strUrl)
        }
    }

    private fun parseONEStoreUri(uri: Uri, strUrl: String): UrlParseInfo {
        val aid = try {
            uri.getQueryParameter("prodId") ?: ""
        } catch (e: Throwable) {
            ""
        }
        return UrlParseInfo.OneStoreUrlParseInfo(uri.scheme!!, aid, strUrl)
    }

    private fun parseONEStoreShortUri(uri: Uri, strUrl: String): UrlParseInfo {
        val aid = try {
            uri.pathSegments.get(0) ?: ""
        } catch (e: Throwable) {
            ""
        }
        return UrlParseInfo.OneStoreUrlParseInfo(uri.scheme!!, aid, strUrl)
    }

    private fun parseMarketUri(uri: Uri, strUrl: String): UrlParseInfo {
        val packageName = uri.getQueryParameter("id") ?: ""
        return UrlParseInfo.GoogleUrlParseInfo(uri.scheme!!, packageName, strUrl)
    }

    private fun parseIntentUri(uri: Uri, strUrl: String): UrlParseInfo {
        val packageName = try {
            uri.fragment?.split(";")?.find {
                it.startsWith("package=")
            }?.split("=")?.get(1) ?: ""
        } catch (e: Throwable) {
            ""
        }
        return UrlParseInfo.GoogleUrlParseInfo(uri.scheme!!, packageName, strUrl)
    }

    private fun parseHttpScheme(uri: Uri): String {
        return try {
            if (uri.path?.contains(GOOGLE_PLAY_DETAIL_LINK) == true) {
                uri.getQueryParameter("id") ?: ""
            } else {
                ""
            }
        } catch (e: Throwable) {
            ""
        }
    }

    private class UnKnownInfoException(val scheme: String) : Exception()

    companion object {
        private const val GOOGLE_PLAY_DETAIL_LINK = "https://play.google.com/store/apps/details"
        private val regexONEstorePattern = "^(m\\.onestore\\.co\\.kr|m\\.onestore\\.net)$".toRegex()
        private val regexONEstoreShortPattern = "^(onesto\\.re|ones\\.to)$".toRegex()
        private val regexGooglePattern = "^(play\\.google\\.com)$".toRegex()

    }
}