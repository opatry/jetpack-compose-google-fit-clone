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

import java.util.Date
import kotlin.time.Duration
import kotlin.time.ExperimentalTime

@ExperimentalTime
sealed class FitActivity(
    val date: Date,
    val label: String,
    val source: String
) {
    class Walk(
        date: Date,
        label: String,
        source: String,
        val distance: Metric.Distance,
        val duration: Duration
    ) :
        FitActivity(date, label, source)

    class Run(
        date: Date,
        label: String,
        source: String,
        val distance: Metric.Distance,
        val duration: Duration,
        heartPoint: Metric.HeartPoint
    ) : FitActivity(date, label, source)

    class Sleep(
        date: Date,
        label: String,
        source: String,
        sleep: Metric.Sleep
    ) : FitActivity(date, label, source)
}