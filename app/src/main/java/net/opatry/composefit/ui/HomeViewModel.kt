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

package net.opatry.composefit.ui

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.opatry.composefit.model.Metric
import net.opatry.composefit.model.UserProfile
import kotlin.time.ExperimentalTime

data class HomeMetrics(
    val steps: Metric.Step,
    val heartPoints: Metric.HeartPoint,
    val secondaryMetrics: List<Metric>,
    val otherMetrics: List<Pair<Color, Int>>
)

@ExperimentalTime
class HomeViewModel(
    private val userProfile: UserProfile,
    private val mainDispatcher: CoroutineDispatcher = Dispatchers.Main
) : ViewModel() {
    private val _homeMetrics = MutableLiveData<HomeMetrics>()
    val homeMetrics: LiveData<HomeMetrics>
        get() = _homeMetrics

    init {
        viewModelScope.launch(mainDispatcher) {
            _homeMetrics.value = HomeMetrics(
                userProfile.steps,
                userProfile.heartPoints,
                userProfile.secondaryMetrics,
                userProfile.otherMetrics
            )
        }
    }
}

class HomeViewModelFactory @ExperimentalTime constructor(private val userProfile: UserProfile) :
    ViewModelProvider.Factory {
    @ExperimentalTime
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userProfile) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel $modelClass")
        }
    }
}

