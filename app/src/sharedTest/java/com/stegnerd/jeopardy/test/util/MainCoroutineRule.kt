package com.stegnerd.jeopardy.test.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.test.resetMain
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * This creates a coroutine wrapper for using a test dispatcher rather than
 * the Dispatchers.Main
 */
@ExperimentalCoroutinesApi
class MainCoroutineRule(
    val testDispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
):  TestWatcher(){

    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(testDispatcher)
    }

    override fun finished(description: Description?) {
        super.finished(description)
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }
}

/**
 * Overload for runBlockingTest that uses the testDispatcher instead of Dispatchers.Main.
 */
@ExperimentalCoroutinesApi
fun MainCoroutineRule.runBlockingTest(block: suspend () -> Unit) =
    this.testDispatcher.runBlockingTest {
        block()
    }

/**
 * Creates a new [CoroutineScope] with the rule's testDispatcher
 */
@ExperimentalCoroutinesApi
fun MainCoroutineRule.CoroutineScope(): CoroutineScope = CoroutineScope(testDispatcher)