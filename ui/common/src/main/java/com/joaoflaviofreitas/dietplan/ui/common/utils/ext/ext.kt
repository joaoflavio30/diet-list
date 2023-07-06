package com.joaoflaviofreitas.dietplan.ui.common.utils.ext // ktlint-disable filename

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.RelativeSizeSpan
import android.view.View
import androidx.core.content.ContextCompat
import com.joaoflaviofreitas.dietplan.ui.common.R

fun String.formatCurrentVsTotal(proteinCount: String, totalProteinCount: String, measure: String): SpannableStringBuilder {
    val proteinNumbFormat = SpannableStringBuilder()
    proteinNumbFormat.append(proteinCount)
    proteinNumbFormat.append("/")
    val slashPosition = proteinNumbFormat.length
    proteinNumbFormat.append(totalProteinCount)
    proteinNumbFormat.append(measure)
    proteinNumbFormat.setSpan(ForegroundColorSpan(Color.parseColor("#808080")), slashPosition, proteinNumbFormat.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    proteinNumbFormat.setSpan(RelativeSizeSpan(0.7f), slashPosition, proteinNumbFormat.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

    return proteinNumbFormat
}

fun Double.formatToTwoHouses(): Double {
    val formattedString = String.format("%.2f", this).replace(",", ".")
    return formattedString.toDouble()
}

fun View.bindVisible() {
    this.visibility = View.VISIBLE
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

fun formatPropertiesWithTwoDecimalPlaces(obj: Any) {
    val properties = obj.javaClass.declaredFields
    for (property in properties) {
        if (property.type == Double::class.java) {
            property.isAccessible = true
            val value = property.getDouble(obj)
            val formattedValue = value.formatToTwoHouses()
            property.set(obj, formattedValue)
        }
    }
}
