package com.betterme.localizer.data

import com.betterme.localizer.data.constants.MetaDataContants
import java.io.File

internal interface TranslationsLocalStore {

    fun saveToFile(resFolderPath: String, fileContents: String, locale: String)

    fun getStringsFilePath(resFolderPath: String, locale: String): String
}

internal class TranslationsLocalStoreImpl : TranslationsLocalStore {

    override fun saveToFile(resFolderPath: String, fileContents: String, locale: String) {
        val fileName = getStringsFilePath(resFolderPath, locale)
        val translationFile = File(fileName)
        if (!translationFile.parentFile.exists()) {
            translationFile.parentFile.mkdir()
        }
        if (!translationFile.exists()) {
            translationFile.createNewFile()
        }
        translationFile.bufferedWriter().use { out ->
            out.write(fileContents)
        }
    }

    override fun getStringsFilePath(resFolderPath: String, locale: String): String {
        val valuesFolderPrefix = if (locale.isEmpty() || locale ==
                MetaDataContants.Values.Locales.VALUE_ENG) {

            "$resFolderPath/values"
        } else if (locale.contains(Regex("[a-z\\-A-Z]"))) {
            val processedRegionalLocale = locale.replace("-", "-r")
            "$resFolderPath/values-$processedRegionalLocale"
        } else {
            "$resFolderPath/values-${locale.toLowerCase()}"
        }
        return "$valuesFolderPrefix/strings.xml"
    }
}
