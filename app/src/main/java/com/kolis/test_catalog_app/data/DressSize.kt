package com.kolis.test_catalog_app.data

import java.lang.Exception

enum class DressSize(val nameShort: String) {
    EXTRA_SMALL("XS"),
    SMALL("S"),
    MEDIUM("M"),
    LARGE("L"),
    EXTRA_LARGE("XL");

    companion object {
        val allSizes: MutableList<DressSize> = mutableListOf(EXTRA_SMALL, SMALL, MEDIUM, LARGE, EXTRA_LARGE)

        fun byName(value: String): DressSize = values().find {
            it.nameShort == value
        } ?: throw Exception("Unknown DressSize enum: $value")
    }
}
