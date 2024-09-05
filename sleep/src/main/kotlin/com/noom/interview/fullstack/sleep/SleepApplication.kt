package com.noom.interview.fullstack.sleep

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class SleepApplication {
	companion object {
		const val UNIT_TEST_PROFILE = "unittest"
		const val DB_TEST_PROFILE = "dbtest"
	}
}

fun main(args: Array<String>) {
	runApplication<SleepApplication>(*args)
}
