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

package net.opatry.composefit.ui.util

import net.opatry.composefit.model.Metric
import net.opatry.composefit.ui.theme.FitBlue
import net.opatry.composefit.ui.theme.FitDarkBlue
import net.opatry.composefit.ui.theme.FitGreen
import net.opatry.composefit.ui.theme.FitPurple
import net.opatry.composefit.ui.theme.FitRed

val Metric.color
    get() = when (this) {
        is Metric.HeartPoint -> FitGreen
        is Metric.Step -> FitDarkBlue
        is Metric.Sleep -> FitPurple
        is Metric.HeartRate -> FitRed
        // generic metrics shared same color but keep explicit enumeration to enforce color decision for future metrics
        is Metric.Weight -> FitBlue
        is Metric.Calorie -> FitBlue
        is Metric.Distance -> FitBlue
        is Metric.Move -> FitBlue
    }