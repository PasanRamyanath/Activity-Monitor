// SensorRepository.kt
package com.example.activitymonitor.data.repository

import com.example.activitymonitor.data.models.ECGData
import com.example.activitymonitor.data.models.EMGData
import com.example.activitymonitor.data.models.HeartRateData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import org.json.JSONArray
import org.json.JSONObject

class SensorRepository {
    private val database = FirebaseDatabase.getInstance()
    private val ecgRef = database.getReference("ecg/latest")
    private val emgRef = database.getReference("emg/latest")
    private val heartRateRef = database.getReference("heartrate/latest")

    fun getECGData(): Flow<ECGData> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val jsonString = snapshot.getValue(String::class.java) ?: return
                    val jsonObject = JSONObject(jsonString)
                    val dataArray = jsonObject.getJSONArray("data")
                    val timestamp = jsonObject.getLong("timestamp")

                    val dataList = mutableListOf<Int>()
                    for (i in 0 until dataArray.length()) {
                        dataList.add(dataArray.getInt(i))
                    }

                    trySend(ECGData(dataList, timestamp))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                close(error.toException())
            }
        }

        ecgRef.addValueEventListener(listener)
        awaitClose { ecgRef.removeEventListener(listener) }
    }

    fun getEMGData(): Flow<EMGData> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val jsonString = snapshot.getValue(String::class.java) ?: return
                    val jsonObject = JSONObject(jsonString)
                    val dataArray = jsonObject.getJSONArray("data")
                    val timestamp = jsonObject.getLong("timestamp")

                    val dataList = mutableListOf<Int>()
                    for (i in 0 until dataArray.length()) {
                        dataList.add(dataArray.getInt(i))
                    }

                    trySend(EMGData(dataList, timestamp))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                close(error.toException())
            }
        }

        emgRef.addValueEventListener(listener)
        awaitClose { emgRef.removeEventListener(listener) }
    }

    fun getHeartRateData(): Flow<HeartRateData> = callbackFlow {
        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                try {
                    val jsonString = snapshot.getValue(String::class.java) ?: return
                    val jsonObject = JSONObject(jsonString)
                    val value = jsonObject.getInt("value")
                    val timestamp = jsonObject.getLong("timestamp")

                    trySend(HeartRateData(value, timestamp))
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                // Handle error
                close(error.toException())
            }
        }

        heartRateRef.addValueEventListener(listener)
        awaitClose { heartRateRef.removeEventListener(listener) }
    }
}