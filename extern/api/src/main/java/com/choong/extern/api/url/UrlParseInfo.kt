package com.choong.extern.api.url

sealed class UrlParseInfo() {
    data class NormalWebUrlParseInfo(val scheme: String, val originUrl: String): UrlParseInfo()
    data class GoogleUrlParseInfo(val scheme: String, val packageName: String, val originUrl: String): UrlParseInfo()
    data class OneStoreUrlParseInfo(val scheme: String, val aid: String, val originUrl: String): UrlParseInfo()
    data class UnknownUrlParseInfo(val scheme: String, val originUrl: String): UrlParseInfo()
}
