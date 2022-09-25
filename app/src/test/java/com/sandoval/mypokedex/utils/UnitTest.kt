package com.sandoval.mypokedex.utils

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import org.junit.Rule

abstract class UnitTest {
    @Rule
    @JvmField
    val injectMocks = InjectMocksRule.create(this@UnitTest)

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()
}