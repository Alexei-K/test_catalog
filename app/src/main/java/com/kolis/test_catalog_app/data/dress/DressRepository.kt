package com.kolis.test_catalog_app.data.dress

import android.util.Log
import com.kolis.test_catalog_app.data.DressModel.Companion.fromFirebaseDocument
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.MutableLiveData
import com.kolis.test_catalog_app.data.DressModel
import androidx.lifecycle.LiveData
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.kolis.test_catalog_app.ui.login.OnPasswordCheckObserver
import java.lang.Exception
import java.util.*

class DressRepository : DressRepositoryType {
    var db = FirebaseFirestore.getInstance()
    private val _allDresses = MutableLiveData<List<DressModel>>()
    private fun allDresses(): LiveData<List<DressModel>> {
        return _allDresses
    }

    override fun addDress(model: DressModel?) {
        db.collection(DRESS_COLLECTION_PATH).add(model!!.toMap())
            .addOnSuccessListener { documentReference: DocumentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.id) }
            .addOnFailureListener { exception: Exception? -> Log.d(TAG, "upload failed ") }
    }

    override val allDressesLD: LiveData<List<DressModel>>
        get() {
            db.collection(DRESS_COLLECTION_PATH)
                .get()
                .addOnCompleteListener { task: Task<QuerySnapshot> ->
                    if (task.isSuccessful) {
                        val dressModels = ArrayList<DressModel>()
                        for (document in task.result!!) {
                            Log.d(TAG, document.id + " => " + document.data)
                            dressModels.add(fromFirebaseDocument(document))
                        }
                        _allDresses.postValue(dressModels)
                    } else {
                        Log.w(TAG, "Error getting data from firebase.", task.exception)
                    }
                }
            return allDresses()
        }

    override fun addDressToCart(dress: DressInCartModel) {
        //TODO implement
    }

    override fun addProfile(login: String?, password: String?) {
        val profile = HashMap<String, String?>()
        profile["login"] = login
        profile["password"] = password
        val date = Date(System.currentTimeMillis())
        profile["register date"] = date.toString()
        db.collection(PROFILES_COLLECTION_PATH)
            .add(profile)
            .addOnSuccessListener { documentReference: DocumentReference -> Log.d(TAG, "Login added " + documentReference.id) }
            .addOnFailureListener { documentReference: Exception? -> Log.d(TAG, "Login adding failed! ") }
    }

    override fun isPasswordRight(login: String?, password: String?, observer: OnPasswordCheckObserver) {
        db.collection(PROFILES_COLLECTION_PATH).whereEqualTo("login", login).whereEqualTo("password", password).get()
            .addOnCompleteListener { task: Task<QuerySnapshot?> ->
                if (task.isSuccessful) {
                    if (task.result != null) {
                        if (!task.result!!.isEmpty) {
                            observer.onPasswordCorrect(login, password)
                        } else {
                            observer.onPasswordWrong()
                        }
                    }
                } else {
                    Log.w(TAG, "Error getting data from firebase.", task.exception)
                }
            }
    }

    companion object {
        private const val DRESS_COLLECTION_PATH = "dresses"
        private const val PROFILES_COLLECTION_PATH = "profiles_4578"
        var TAG = "firebase_debug"
    }
}