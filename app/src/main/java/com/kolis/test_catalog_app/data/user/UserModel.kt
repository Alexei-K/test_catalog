package com.kolis.test_catalog_app.data.user

data class UserModel(val uid: String, val username: String, val isAdmin: Boolean, val email: String?, val phone: String?, val photoUrl: String?) {
}