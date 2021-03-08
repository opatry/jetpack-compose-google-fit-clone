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

package net.opatry.composefit.ui.component

import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.transform.CircleCropTransformation
import dev.chrisbanes.accompanist.coil.CoilImage

@Composable
fun FitToolbar(
    profilePictureUrl: String,
    profileName: String,
    onProfileClick: () -> Unit,
    title: String? = null,
    screenActions: @Composable () -> Unit
) {
    // TODO lift on scroll (using a remember anim elevation value based on parent scroll?
    TopAppBar(
        title = {
            if (!title.isNullOrEmpty()) {
                Text(title)
            }
        },
        backgroundColor = MaterialTheme.colors.surface,
        elevation = 4.dp,
        actions = {
            screenActions()
            IconButton(onClick = onProfileClick) {
                CoilImage(
                    data = profilePictureUrl,
                    profileName,
                    Modifier
                        .size(48.dp)
                        .padding(8.dp)
                        .fillMaxHeight(),
                    requestBuilder = { transformations(CircleCropTransformation()) },
                    fadeIn = true
                )
            }
        }
    )
}