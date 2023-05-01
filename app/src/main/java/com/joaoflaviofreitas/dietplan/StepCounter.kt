package com.joaoflaviofreitas.dietplan

import android.app.Activity
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.fitness.Fitness
import com.google.android.gms.fitness.FitnessOptions
import com.google.android.gms.fitness.data.DataSource
import com.google.android.gms.fitness.data.DataType
import com.google.android.gms.fitness.data.Field
import com.google.android.gms.fitness.request.DataReadRequest
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.concurrent.TimeUnit

class StepCounter() {
    val fitnessOptions = FitnessOptions.builder().addDataType(DataType.TYPE_STEP_COUNT_CUMULATIVE).build()

    fun registerStep(activity: Activity) {
        Fitness.getRecordingClient(activity, GoogleSignIn.getAccountForExtension(activity, fitnessOptions))
            .subscribe(DataType.TYPE_STEP_COUNT_CUMULATIVE)
            .addOnSuccessListener {
                Log.i("TAG", "Subscription was successful!")
            }
            .addOnFailureListener { e ->
                Log.w("TAG", "There was a problem subscribing ", e)
            }
    }

    fun getStep(activity: Activity) {
        val startTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault())
        val endTime = LocalDateTime.now().atZone(ZoneId.systemDefault())

        val datasource = DataSource.Builder()
            .setAppPackageName("com.google.android.gms")
            .setDataType(DataType.TYPE_STEP_COUNT_DELTA)
            .setType(DataSource.TYPE_DERIVED)
            .setStreamName("estimated_steps")
            .build()

        val request = DataReadRequest.Builder()
            .aggregate(datasource)
            .bucketByTime(1, TimeUnit.DAYS)
            .setTimeRange(startTime.toEpochSecond(), endTime.toEpochSecond(), TimeUnit.SECONDS)
            .build()

        Fitness.getHistoryClient(activity, GoogleSignIn.getAccountForExtension(activity, fitnessOptions))
            .readData(request)
            .addOnSuccessListener { response ->
                val totalSteps = response.buckets
                    .flatMap { it.dataSets }
                    .flatMap { it.dataPoints }
                    .sumBy { it.getValue(Field.FIELD_STEPS).asInt() }
                Log.i("TAG", "Total steps: $totalSteps")
            }
    }
}
