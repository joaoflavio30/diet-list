package com.example.dietplan.extensions // ktlint-disable filename

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.example.dietplan.R
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

fun String.formatCurrentVsTotal(proteinCount: String, totalProteinCount: String, measure: String): SpannableStringBuilder {
    val proteinNumbFormat = SpannableStringBuilder()
    proteinNumbFormat.append(proteinCount)
    proteinNumbFormat.append("/")
    val slashPosition = proteinNumbFormat.length
    proteinNumbFormat.append(totalProteinCount)
    proteinNumbFormat.append(measure)
    proteinNumbFormat.setSpan(ForegroundColorSpan(Color.parseColor("#808080")), slashPosition, proteinNumbFormat.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    proteinNumbFormat.setSpan(RelativeSizeSpan(0.8f), slashPosition, proteinNumbFormat.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

    return proteinNumbFormat
}

fun Double.formatToTwoHouses(): Double {
    return String.format("%.2f", this).toDouble()
}

fun View.bindVisible() {
    this.visibility = View.VISIBLE
}

fun LocalDateTime.toTimeString(): String {
    val formatter = DateTimeFormatter.ofPattern("h:mm a")
    return this.format(formatter)
}

fun String?.isNotNullOrEmptyNotBlank(): Boolean {
    return !this.isNullOrEmpty() && this.isNotBlank()
}

fun View.highlightAView() {
    this.setBackgroundColor(
        ContextCompat.getColor(
            context,
            R.color.red,
        ),
    )
}
