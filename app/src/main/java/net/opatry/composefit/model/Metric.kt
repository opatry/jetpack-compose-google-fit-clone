/*
 * Copyright (c) 2021 Olivier Patry
 *
 * Permission is hereby granted, free of charge, to any person obtaining
 * a copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the Software
 * is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND,
 * EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES
 * OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.
 * IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY
 * CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,
 * TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE
 * OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package net.opatry.composefit.model

import kotlin.time.Duration
import kotlin.time.ExperimentalTime

sealed class Metric {
    data class HeartPoint(val score: Int) : Metric()
    data class Step(val count: Int) : Metric()
    data class Sleep @ExperimentalTime constructor(val totalSleepDuration: Duration, val deepSleepDuration: Duration) : Metric()
    data class HeartRate(val lastBPM: Int) : Metric()
    data class Weight(val gram: Int) : Metric()
    data class Calorie(val value: Int) : Metric()
    data class Distance(val meter: Int) : Metric() {
        operator fun plus(other: Metric.Distance): Metric.Distance =
            Metric.Distance(meter + other.meter)
    }

    data class Move(val moveMin: Int) : Metric()
}

val Int.heartPoints get() = Metric.HeartPoint(this)

val Int.km get() = Metric.Distance(this * 1000)
val Int.meters get() = Metric.Distance(this)

