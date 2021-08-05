package com.kolis.test_catalog_app.data

enum class DressSize(val nameShort: String) {
    EXTRA_SMALL("XS"),
    SMALL("S"),
    MEDIUM("M"),
    LARGE("L"),
    EXTRA_LARGE("XL");

    companion object {
        val allSizes: MutableList<DressSize> = mutableListOf(EXTRA_SMALL, SMALL, MEDIUM, LARGE, EXTRA_LARGE)
    }
}
