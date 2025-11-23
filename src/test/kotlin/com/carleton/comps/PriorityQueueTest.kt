package com.carleton.comps

import com.cs.comps.Employee
import com.cs.comps.sortEmployeesByPriority
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PriorityQueueTest {

    // Build employees in our model. All review fields default to NaN so performance
    // comes from behavior only (punctuality -> [1..5]).
    private fun emp(
        id: String,
        self: Double = Double.NaN,
        peer: Double = Double.NaN,
        mgr: Double = Double.NaN,
        okr: Double = Double.NaN,
        punctuality: Double = Double.NaN,  // 0..1 or NaN (ignored)
        fixed: Int = 0,
        assigned: Int = 0,
        cost: Int = 0                      // NON-NULL Int to match your Employee.kt
    ): Employee = Employee(
        id = id, name = id, hireDate = LocalDate.parse("2022-01-01"),
        role = "r", salary = 0, tenureMonths = 0,
        punctualityRate = punctuality, problemsFixed = fixed, problemsAssigned = assigned,
        volunteeringHours = 0, costToCompany = cost,
        dept = "d", manager = "m", location = "loc", employmentType = "full",
        selfEvaluation = self, peer360Feedback = peer, managerFeedback = mgr, okr = okr,
        gender = "g", disabilityStatus = "No", sponsorship = "No", performanceScore = null
    )

    @Test
    fun lowerPerformanceFirst_realBeforeZeroCost() {
        val p1 = emp("p1", punctuality = 0.0, cost = 999)      // perf 1
        val p3 = emp("p3", punctuality = 0.5, cost = 999)      // perf 3
        val p5 = emp("p5", punctuality = 1.0, cost = 999)      // perf 5
        val pz = emp("pz", punctuality = Double.NaN, cost = 1, assigned = 0) // perf null -> will be null -> treated after real

        val out = sortEmployeesByPriority(listOf(p5, pz, p3, p1))
        assertEquals(listOf("p1", "p3", "p5", "pz"), out.map { it.id })
    }

    @Test
    fun higherCostWinsWhenPerformanceEqual_zeroIsLowest() {
        // Same performance (3 via punctuality 0.5)
        val high = emp("high", punctuality = 0.5, cost = 1000)
        val low  = emp("low",  punctuality = 0.5, cost = 10)
        val zero = emp("zero", punctuality = 0.5, cost = 0)     // should be after any positive

        val out = sortEmployeesByPriority(listOf(low, zero, high))
        val ids = out.map { it.id }

        // Highest cost first
        assertEquals("high", ids.first())

        // zero must appear after both positive-cost equals
        val posMaxIndex = maxOf(ids.indexOf("high"), ids.indexOf("low"))
        val zeroIndex = ids.indexOf("zero")
        assertTrue(zeroIndex > posMaxIndex)
    }

    @Test
    fun realScoresBeforeZeroCost_evenIfZeroHasHugeCost() {
        val realLow  = emp("realLow",  punctuality = 0.0, cost = 10)       // perf 1
        val realHigh = emp("realHigh", punctuality = 1.0, cost = 20)       // perf 5
        val zeroHuge = emp("zeroHuge", punctuality = Double.NaN, cost = 1_000_000, assigned = 0) // perf null

        val out = sortEmployeesByPriority(listOf(zeroHuge, realHigh, realLow))
        assertEquals(listOf("realLow", "realHigh", "zeroHuge"), out.map { it.id })
    }

    @Test
    fun exactTiesHaveNoDeterministicOrder_butAreAllPresent() {
        // Same perf (3) and same cost (100)
        val a = emp("a", punctuality = 0.5, cost = 100)
        val b = emp("b", punctuality = 0.5, cost = 100)
        val c = emp("c", punctuality = 0.5, cost = 100)

        val out = sortEmployeesByPriority(listOf(a, b, c))
        assertEquals(setOf("a", "b", "c"), out.map { it.id }.toSet())
    }

    @Test
    fun groupsByPerfAscending_thenCostDescendingWithinPerf() {
        // perf 2 group
        val p2a = emp("p2a", punctuality = 0.25, cost = 500)
        val p2b = emp("p2b", punctuality = 0.25, cost = 50)
        val p2c = emp("p2c", punctuality = 0.25, cost = 0)  // lowest within perf=2

        // perf 4 group
        val p4a = emp("p4a", punctuality = 0.75, cost = 5)
        val p4b = emp("p4b", punctuality = 0.75, cost = 1)

        val out = sortEmployeesByPriority(listOf(p4b, p2c, p2a, p4a, p2b))
        val ids = out.map { it.id }

        assertEquals(listOf("p2a", "p2b", "p2c", "p4a", "p4b"), ids)
    }
}