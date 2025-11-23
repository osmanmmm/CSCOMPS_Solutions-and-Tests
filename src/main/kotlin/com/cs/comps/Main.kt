package com.cs.comps

private const val DATA_FILE = "data/assignment_b1.csv"

// ---------- Utilities ----------
private fun withScores(list: List<Employee>) =
    list.map { it.copy(performanceScore = computePerformanceScore(it)) }

// ---------- Dump ----------
private fun dumpAll() {
    val employees = withScores(loadEmployees(DATA_FILE))
    println("Loaded ${employees.size} employees")
    employees.forEach { println(it) }
}

// ---------- FIFO (First Hired, First Fired) ----------
private fun runFifo() {
    val employees = withScores(loadEmployees(DATA_FILE)).sortedBy { it.hireDate }
    val q = LinkedQueue<Employee>()
    employees.forEach { q.enqueue(it) }
    println("=== FIFO ===")
    while (!q.isEmpty()) {
        val x = q.dequeue()
        println(
            "Laid off: ${x.id} ${x.name} – Hire: ${x.hireDate} – CTC: ${x.costToCompany} – " +
            "Perf: ${x.performanceScore ?: "insufficient"}"
        )
    }
}

// ---------- LIFO (Last Hired, First Fired) ----------
private fun runLifo() {
    val employees = withScores(loadEmployees(DATA_FILE)).sortedBy { it.hireDate }
    val st = Stack<Employee>()
    employees.forEach { st.push(it) }
    println("=== LIFO ===")
    while (!st.isEmpty()) {
        val x = st.pop()
        println(
            "Laid off: ${x.id} ${x.name} – Hire: ${x.hireDate} – CTC: ${x.costToCompany} – " +
            "Perf: ${x.performanceScore ?: "insufficient"}"
        )
    }
}

// ---------- Priority (min-heap; ties randomized in sortEmployeesByPriority) ----------
private fun runPriority() {
    val employees = loadEmployees(DATA_FILE)
    val ordered = sortEmployeesByPriority(employees) // returns employees with performanceScore filled
    println("=== PRIORITY (min-heap; ties randomized) ===")
    ordered.forEach { x ->
        println(
            "Laid off: ${x.id} ${x.name} – Hire: ${x.hireDate} – CTC: ${x.costToCompany ?: "n/a"} – " +
            "Perf: ${x.performanceScore ?: "insufficient"}"
        )
    }
}

// ---------- Run all demos ----------
private fun runAll() {
    runLifo()
    runFifo()
    runPriority()
    println("=== DUMP ===")
    dumpAll()
}

// ---------- Main ----------
fun main(args: Array<String>) {
    when (args.firstOrNull()?.lowercase()) {
        null, "", "lifo"     -> runLifo()
        "fifo"               -> runFifo()
        "priority"           -> runPriority()
        "dump"               -> dumpAll()
        "all"                -> runAll()
        else -> {
            System.err.println(
                "Unknown option: ${args.first()}\n" +
                "Usage: lifo | fifo | priority | dump | all"
            )
        }
    }
}
