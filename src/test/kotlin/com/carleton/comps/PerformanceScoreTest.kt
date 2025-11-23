package com.carleton.comps

import com.cs.comps.Employee
import com.cs.comps.computePerformanceScore
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.time.LocalDate

class PerformanceScoreTest {

    private fun baseEmp(
        id: String = "X",
        self: Double = Double.NaN,         // use NaN for "missing"
        peer: Double = Double.NaN,
        mgr: Double = Double.NaN,
        okr: Double = Double.NaN,          // 0..100 or NaN for "missing"
        punctuality: Double = Double.NaN,  // 0..1 or NaN for "missing"
        fixed: Int = 0,
        assigned: Int = 0
    ): Employee = Employee(
        id = id, name = id, hireDate = LocalDate.parse("2022-01-01"),
        role = "r", salary = 0, tenureMonths = 0,
        punctualityRate = punctuality, problemsFixed = fixed, problemsAssigned = assigned,
        volunteeringHours = 0, costToCompany = 0,
        dept = "d", manager = "m", location = "loc", employmentType = "full",
        selfEvaluation = self, peer360Feedback = peer, managerFeedback = mgr, okr = okr,
        gender = "g", disabilityStatus = "No", sponsorship = "No", performanceScore = null
    )

    @Test
    fun perfectReviewsAndBehavior_scoreIs5() {
        // Reviews: [5,5,5, 100/20=5] -> 5.0
        // Behavior: punctuality 1.0 -> 5, productivity 10/10=1.0 -> 5 -> avg 5.0
        val e = baseEmp(self = 5.0, peer = 5.0, mgr = 5.0, okr = 100.0,
                        punctuality = 1.0, fixed = 10, assigned = 10)
        assertEquals(5, computePerformanceScore(e))
    }

    @Test
    fun reviewsOnly_whenBehaviorMissing_usesReviewsMeanIncludingOKR() {
        // Reviews: [2,2,2, 40/20=2] -> mean 2.0
        // Behavior missing: punctuality=NaN, assigned=0 -> prod skipped
        val e = baseEmp(self = 2.0, peer = 2.0, mgr = 2.0, okr = 40.0,
                        punctuality = Double.NaN, fixed = 0, assigned = 0)
        assertEquals(2, computePerformanceScore(e))
    }

    @Test
    fun behaviorOnly_whenReviewsMissing_averagesMappedSignals_andRounds() {
        // punctuality 0.25 -> 1 + 4*0.25 = 2.0
        // productivity 10/20=0.5 -> 1 + 4*0.5 = 3.0
        // behavior mean = 2.5 -> round() -> 3
        val e = baseEmp(punctuality = 0.25, fixed = 10, assigned = 20)
        assertEquals(3, computePerformanceScore(e))
    }

    @Test
    fun combined_usesWeights_pointSevenReviews_pointThreeBehavior_andRounds() {
        // Reviews: [3,3,3, 80/20=4] -> 3.25
        // Behavior: punctuality 0.5 -> 3.0; productivity 1/4=0.25 -> 2.0; mean 2.5
        // Combined: 0.7*3.25 + 0.3*2.5 = 3.025 -> round() -> 3
        val e = baseEmp(self = 3.0, peer = 3.0, mgr = 3.0, okr = 80.0,
                        punctuality = 0.5, fixed = 1, assigned = 4)
        assertEquals(3, computePerformanceScore(e))
    }

    @Test
    fun productivitySkippedWhenAssignedIsZero_behaviorFromPunctualityOnly() {
        // assigned=0 -> productivity skipped; punctuality 0.9 -> 1 + 3.6 = 4.6 -> round() -> 5
        val e = baseEmp(punctuality = 0.9, fixed = 10, assigned = 0)
        assertEquals(5, computePerformanceScore(e))
    }

    @Test
    fun returnsNull_whenNoUsableReviewOrBehaviorSignals() {
        // everything NaN or zero -> no reviews, no behavior -> null
        val e = baseEmp(self = Double.NaN, peer = Double.NaN, mgr = Double.NaN, okr = Double.NaN,
                        punctuality = Double.NaN, fixed = 0, assigned = 0)
        assertNull(computePerformanceScore(e))
    }

    @Test
    fun clampsFinalToRangeOneToFive() {
        // Low side: punctuality 0 -> mapped 1.0 -> round() -> 1
        val eLow = baseEmp(punctuality = 0.0, fixed = 0, assigned = 0)
        // High side: reviews all 5 including okr=100 -> 5
        val eHigh = baseEmp(self = 5.0, peer = 5.0, mgr = 5.0, okr = 100.0)

        val sLow = computePerformanceScore(eLow)
        val sHigh = computePerformanceScore(eHigh)
        assertNotNull(sLow); assertTrue(sLow!! in 1..5)
        assertNotNull(sHigh); assertTrue(sHigh!! in 1..5)
    }
}
