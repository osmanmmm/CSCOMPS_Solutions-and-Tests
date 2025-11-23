package com.cs.comps

import java.math.BigDecimal
import java.math.RoundingMode

fun computePerformanceScore(e: Employee): Int? {
    // --- Reviews (ignore NaN) ---
    val reviewSignals = buildList {
        if (!e.selfEvaluation.isNaN()) add(e.selfEvaluation)     // 1..5
        if (!e.peer360Feedback.isNaN()) add(e.peer360Feedback)   // 1..5
        if (!e.managerFeedback.isNaN()) add(e.managerFeedback)   // 1..5
        if (!e.okr.isNaN()) add(e.okr / 20.0)                    // 0..100 -> 0..5
    }
    val R: Double? = reviewSignals.takeIf { it.isNotEmpty() }?.average()

    // --- Behavior: punctuality + productivity (ignore NaN; include prod iff assigned>0) ---
    val behaviorSignals = buildList {
        // productivity
        if (e.problemsAssigned > 0) {
            val ratio = (e.problemsFixed.toDouble() / e.problemsAssigned.toDouble())
                .coerceIn(0.0, 1.0)
            add(1.0 + 4.0 * ratio) // 0..1 -> 1..5
        }
        // punctuality
        if (!e.punctualityRate.isNaN()) {
            add(1.0 + 4.0 * e.punctualityRate.coerceIn(0.0, 1.0))
        }
    }
    val B: Double? = behaviorSignals.takeIf { it.isNotEmpty() }?.average()

    // --- Combine or return null if nothing usable ---
    val combined = when {
        R != null && B != null -> 0.7 * R + 0.3 * B
        R != null -> R
        B != null -> B
        else -> return null
    }

    // --- HALF_UP rounding then clamp to [1,5] ---
    val clamped = combined.coerceIn(1.0, 5.0)
    val rounded = BigDecimal(clamped).setScale(0, RoundingMode.HALF_UP).toInt()
    return rounded.coerceIn(1, 5)
}
