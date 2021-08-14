package com.kolis.test_catalog_app.data.dress

import android.content.Context
import android.util.Log
import com.kolis.test_catalog_app.data.DressModel.Companion.fromFirebaseDocument
import com.google.firebase.firestore.FirebaseFirestore
import androidx.lifecycle.MutableLiveData
import com.kolis.test_catalog_app.data.DressModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.QuerySnapshot
import com.kolis.test_catalog_app.data.dress.db.DressDatabase
import com.kolis.test_catalog_app.data.dress.db.DressInCartEntity
import com.kolis.test_catalog_app.ui.login.OnPasswordCheckObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.Exception
import java.util.*

class DressRepository(context: Context) : DressRepositoryType {

    companion object {
        private const val DRESS_COLLECTION_PATH = "dresses"
        private const val PROFILES_COLLECTION_PATH = "profiles_4578"
        var TAG = "firebase_debug"
    }

    private var firebaseDatabase = FirebaseFirestore.getInstance()

    private var dressDatabase = DressDatabase.getInstance(context)
    private val _allDresses = MutableLiveData<List<DressModel>>()

    private fun allDresses(): LiveData<List<DressModel>> {
        return _allDresses
    }

    override fun addDress(model: DressModel?) {
        firebaseDatabase.collection(DRESS_COLLECTION_PATH).add(model!!.toMap())
            .addOnSuccessListener { documentReference: DocumentReference -> Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.id) }
            .addOnFailureListener { exception: Exception? -> Log.d(TAG, "upload failed ") }
    }

    override val allDressesLD: LiveData<List<DressModel>>
        get() {
            firebaseDatabase.collection(DRESS_COLLECTION_PATH)
                .get()
                .addOnCompleteListener { task: Task<QuerySnapshot> ->
                    if (task.isSuccessful) {
                        val dressModels = ArrayList<DressModel>()
                        for (document in task.result!!) {
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
        CoroutineScope(Dispatchers.IO).launch {
            val id = dressDatabase.dressInCardDao().addItemInCard(DressInCartEntity.fromModel(dress))
            Log.d("AlexLog", "Item with id $id added to cart")
        }
    }

    override fun removeFromCart(dress: DressInCartModel) {
        CoroutineScope(Dispatchers.IO).launch {
            dressDatabase.dressInCardDao().removeFromCart(DressInCartEntity.fromModel(dress))
        }
    }

    override fun isAnyDressInCart(): LiveData<Boolean> {
        return Transformations.map(dressDatabase.dressInCardDao().countDressInCart()) { rowCount ->
            rowCount > 0
        }
    }

    override fun countDressInCart(): LiveData<Int> {
        return dressDatabase.dressInCardDao().countDressInCart()
    }

    override fun dressesInCart(): LiveData<List<DressInCartModel>> {
        return Transformations.map(dressDatabase.dressInCardDao().dressesInCart()) { entityList ->
            entityList.map { entity ->
                entity.toModel()
            }
        }
    }

    override fun totalCartPrice(): LiveData<Float> {
        return Transformations.map(dressDatabase.dressInCardDao().dressesInCart()) { entityList ->
            var totalPrice = 0f
            entityList.forEach { dress ->
                totalPrice += dress.newPrice * dress.amount
            }
            totalPrice
        }
    }

    override fun addProfile(login: String?, password: String?) {
        val profile = HashMap<String, String?>()
        profile["login"] = login
        profile["password"] = password
        val date = Date(System.currentTimeMillis())
        profile["register date"] = date.toString()
        firebaseDatabase.collection(PROFILES_COLLECTION_PATH)
            .add(profile)
            .addOnSuccessListener { documentReference: DocumentReference -> Log.d(TAG, "Login added " + documentReference.id) }
            .addOnFailureListener { documentReference: Exception? -> Log.d(TAG, "Login adding failed! ") }
    }

    override fun isPasswordRight(login: String?, password: String?, observer: OnPasswordCheckObserver) {
        firebaseDatabase.collection(PROFILES_COLLECTION_PATH).whereEqualTo("login", login).whereEqualTo("password", password).get()
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

    /**
     * for test purpose only
     */
    fun addSampleDataToFirebase() {
        DressModel.sampleList.forEach {
            addDress(it)
        }
    }
}