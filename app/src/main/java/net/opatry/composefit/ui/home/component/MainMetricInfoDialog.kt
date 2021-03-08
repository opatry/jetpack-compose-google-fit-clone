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

package net.opatry.composefit.ui.home.component

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.DirectionsWalk
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import net.opatry.composefit.R
import net.opatry.composefit.ui.theme.FitDarkBlue
import net.opatry.composefit.ui.theme.FitGreen
import net.opatry.composefit.ui.theme.typography

@Composable
fun MainMetricInfoDialog(showDialog: Boolean, setShowDialog: (Boolean) -> Unit) {
    if (showDialog) {
        // TODO how to set dim color to white?
        Dialog(onDismissRequest = { setShowDialog(false) }) {
            Surface(
                shape = RoundedCornerShape(8.dp),
                color = MaterialTheme.colors.surface
            ) {
                Column(
                    Modifier.padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    // TODO pager of various screens
                    Text(
                        "Keep track of your activity with ${stringResource(R.string.app_name)}",
                        Modifier.padding(8.dp),
                        style = typography.h5,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(24.dp))
                    // TODO retrieve color from metric type
                    MetricExplanation(
                        icon = Icons.Outlined.FavoriteBorder,
                        color = FitGreen,
                        metricLabel = "Heart Points",
                        explanation = "Pick up the pace to score points toward this goal"
                    )
                    Spacer(Modifier.height(24.dp))
                    // TODO retrieve color from metric type
                    MetricExplanation(
                        icon = Icons.Outlined.DirectionsWalk,
                        color = FitDarkBlue,
                        metricLabel = "Steps",
                        explanation = "Just keep moving to meet this goal"
                    )
                    Spacer(Modifier.height(24.dp))
                    Text("As well as counting steps, Fit gives you Heart Points when you push yourself", textAlign = TextAlign.Center)

                    Spacer(Modifier.height(24.dp))
                    // TODO tweak color like in Fit
                    Button(onClick = { /*TODO*/ setShowDialog(false) }) {
                        Text("Next")
                    }
                }
            }
        }
    }
}

@Composable
fun MetricExplanation(icon: ImageVector, color: Color, metricLabel: String, explanation: String) {
    Column(Modifier.fillMaxWidth(.6f), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(icon, null, Modifier.size(24.dp), tint = color)
        Text(metricLabel, style = typography.subtitle1, textAlign = TextAlign.Center, fontWeight = FontWeight.SemiBold)
        Text(explanation, style = typography.caption, textAlign = TextAlign.Center)
    }
}
