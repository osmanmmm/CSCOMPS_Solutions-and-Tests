package com.cs.comps

import kotlin.random.Random

// ----- Priority helpers -----
private fun perfKey(e: Employee): Int = e.performanceScore ?: Int.MAX_VALUE
private fun costKey(e: Employee): Int {
    val c = e.costToCompany ?: 0
    return if (c > 0) -c else Int.MAX_VALUE
}

// ----- Binary Min-Heap -----
private class MinHeap<T>(private val less: (T, T) -> Boolean) {
    private val a = ArrayList<T>()
    fun isEmpty() = a.isEmpty()
    fun size() = a.size
    fun peek(): T = a.first()
    fun push(x: T) { a.add(x); siftUp(a.lastIndex) }
    fun pop(): T {
        val top = a.first()
        val last = a.removeAt(a.lastIndex)
        if (a.isNotEmpty()) { a[0] = last; siftDown(0) }
        return top
    }
    private fun parent(i: Int) = (i - 1) / 2
    private fun left(i: Int) = 2 * i + 1
    private fun right(i: Int) = 2 * i + 2
    private fun siftUp(i0: Int) {
        var i = i0
        while (i > 0) {
            val p = parent(i)
            if (!less(a[i], a[p])) break
            val tmp = a[i]; a[i] = a[p]; a[p] = tmp
            i = p
        }
    }
    private fun siftDown(i0: Int) {
        var i = i0
        val n = a.size
        while (true) {
            val l = left(i); val r = right(i)
            var m = i
            if (l < n && less(a[l], a[m])) m = l
            if (r < n && less(a[r], a[m])) m = r
            if (m == i) break
            val tmp = a[i]; a[i] = a[m]; a[m] = tmp
            i = m
        }
    }
}

// ----- Public API: builds priority order, then shuffles exact ties -----
fun sortEmployeesByPriority(employees: List<Employee>): List<Employee> {
    // recompute/attach performance using students' function in Performance.kt
    val withScores = employees.map { it.copy(performanceScore = computePerformanceScore(it)) }

    // heap by (perf ascending; then cost preferring higher -> smaller negative)
    val heap = MinHeap<Employee> { a, b ->
        val ap = perfKey(a); val bp = perfKey(b)
        if (ap != bp) ap < bp
        else {
            val ac = costKey(a); val bc = costKey(b)
            if (ac != bc) ac < bc else false // stop: no deterministic tiebreakers
        }
    }

    withScores.forEach { heap.push(it) }

    // pop all in two-key order
    val ordered = ArrayList<Employee>(withScores.size)
    while (!heap.isEmpty()) ordered.add(heap.pop())

    // “flip a coin” within exact ties (same perf AND same cost)
    val byKey = ordered.groupBy { perfKey(it) to costKey(it) }
    val seen = HashSet<Pair<Int, Int>>()
    val out = ArrayList<Employee>(ordered.size)
    val rng = Random.Default

    for (e in ordered) {
        val k = perfKey(e) to costKey(e)
        if (seen.add(k)) {
            val group = byKey[k]!!.toMutableList()
            group.shuffle(rng)
            out.addAll(group)
        }
    }
    return out
}
