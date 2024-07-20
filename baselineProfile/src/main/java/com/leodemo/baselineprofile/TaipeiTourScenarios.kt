package com.leodemo.baselineprofile

import androidx.benchmark.macro.MacrobenchmarkScope
import androidx.test.uiautomator.By
import androidx.test.uiautomator.BySelector
import androidx.test.uiautomator.Direction
import androidx.test.uiautomator.UiDevice
import androidx.test.uiautomator.UiObject2
import androidx.test.uiautomator.Until

fun MacrobenchmarkScope.taipeiTourScenarios() {
    testAttractionScreen()
    testDetailScreen()
    testWebScreen()
}

fun MacrobenchmarkScope.testAttractionScreen() = device.apply {
    attractionListScrollDownUp()
    navigateAttractionScreenToDetailScreen()
}

fun MacrobenchmarkScope.attractionListScrollDownUp() = device.apply {
    val attractionList = waitAndFindObject(By.res("AttractionList"), 15_000)
    attractionList.setGestureMargin(displayWidth / 5)
    attractionList.fling(Direction.DOWN)
    waitForIdle()
    attractionList.fling(Direction.UP)
}

fun MacrobenchmarkScope.navigateAttractionScreenToDetailScreen() = device.apply {
    val attractionItem = waitAndFindObject(By.res("AttractionItem"), 15_000)
    attractionItem.click()
    waitForIdle()
}

fun MacrobenchmarkScope.testDetailScreen() = device.apply {
    waitDetailContent()
    detailScrollToUrl()
    navigateDetailScreenToWebScreen()
}

fun MacrobenchmarkScope.waitDetailContent() = device.apply {
    wait(Until.hasObject(By.res("Title")), 15_000)
}

fun MacrobenchmarkScope.detailScrollToUrl() = device.apply {
    val scrollMaxCount = 5
    val detailInfoScrollView = waitAndFindObject(By.res("DetailScrollView"), 15_000)
    detailInfoScrollView.setGestureMargin(displayWidth / 5)
    for (currentScrollTimes in 0..scrollMaxCount) {
        if (currentScrollTimes == scrollMaxCount) {
            throw  AssertionError("UrlText not found after $currentScrollTimes times")
        }
        if (wait(Until.hasObject(By.res("UrlText")), 5_000)) {
            break
        } else {
            detailInfoScrollView.fling(Direction.DOWN)
        }
    }
}

fun MacrobenchmarkScope.navigateDetailScreenToWebScreen() = device.apply {
    waitAndFindObject(By.res("UrlText"), 15_000).click()
}

fun MacrobenchmarkScope.testWebScreen() = device.apply {
    waitWebContent()
}

fun MacrobenchmarkScope.waitWebContent() = device.apply {
    wait(Until.hasObject(By.res("WebView")), 15_000)
}

fun UiDevice.waitAndFindObject(selector: BySelector, timeout: Long): UiObject2 {
    if (!wait(Until.hasObject(selector), timeout)) {
        throw AssertionError("selector: $selector Not Found, timeout: $timeout")
    }
    return findObject(selector)
}