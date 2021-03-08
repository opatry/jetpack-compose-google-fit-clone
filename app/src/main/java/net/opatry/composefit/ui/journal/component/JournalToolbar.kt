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

package net.opatry.composefit.ui.journal.component

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.material.ContentAlpha
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.LocalContentColor
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Loop
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.res.stringResource
import net.opatry.composefit.R
import net.opatry.composefit.ui.component.FitToolbar

@Composable
fun JournalToolbar(
    profilePictureUrl: String,
    profileName: String,
    refreshEnabled: Boolean,
    onRefreshClick: () -> Unit,
    onProfileClick: () -> Unit
) {
    val animatedAlpha by animateFloatAsState(
        when {
            refreshEnabled -> 1f
            else -> ContentAlpha.disabled
        }
    )

    // TODO lift on scroll + Toolbar title transition
    FitToolbar(
        profilePictureUrl = profilePictureUrl,
        profileName = profileName,
        title = stringResource(R.string.screen_title_journal),
        onProfileClick = onProfileClick
    ) {
        IconButton(
            enabled = refreshEnabled,
            onClick = onRefreshClick
        ) {
            Icon(
                Icons.Outlined.Loop,
                stringResource(R.string.profile_toolbar_refresh_action),
                tint =
                LocalContentColor.current.copy(alpha = animatedAlpha)
            )
        }
    }
}