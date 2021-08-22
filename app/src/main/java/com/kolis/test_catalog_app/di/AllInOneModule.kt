package com.kolis.test_catalog_app.di

import android.content.Context
import com.google.firebase.firestore.FirebaseFirestore
import com.kolis.test_catalog_app.data.dress.db.DressDatabase
import dagger.Module
import dagger.Provides

@Module
class AllInOneModule {

    @Provides
    fun provideFirebaseDatabase(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Provides
    fun provideRoomDressDatabase(context: Context): DressDatabase {
        return DressDatabase.getInstance(context)
    }
}