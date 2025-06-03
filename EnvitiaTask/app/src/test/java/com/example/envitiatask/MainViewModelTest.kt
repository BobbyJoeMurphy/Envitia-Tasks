package com.example.envitiatask

import com.example.envitiatask.ui.MainViewModel
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class MainViewModelTest {

    private lateinit var viewModel: MainViewModel

    @Before
    fun setup() {
        viewModel = MainViewModel()
    }

    @Test
    fun addEntry_shouldAddLog() {
        viewModel.addEntry("Test log")

        val logs = viewModel.logEntries.value
        assertNotNull(logs)
        assertEquals(1, logs?.size)
        assertEquals("Test log", logs?.first()?.message)
    }

    @Test
    fun clearEntries_shouldEmptyList() {
        viewModel.addEntry("One")
        viewModel.clearEntries()

        val logs = viewModel.logEntries.value
        assertTrue(logs?.isEmpty() == true)
    }
}
