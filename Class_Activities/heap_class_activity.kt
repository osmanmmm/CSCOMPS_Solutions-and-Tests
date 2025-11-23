/*
Priority registration demo (Max-Heap)
Rule: ONLY class year matters (Senior > Junior > Sophomore > First-Year).
Goal: focus on heap structure: insert + percolateUp, allocateSeat + percolateDown.
*/

// -------- Types --------
enum class Year { FIRST_YEAR, SOPHOMORE, JUNIOR, SENIOR }

data class RegRequest(val id: String, val year: Year)

fun yearRank(y: Year) = when (y) {
    Year.SENIOR -> 4
    Year.JUNIOR -> 3
    Year.SOPHOMORE -> 2
    Year.FIRST_YEAR -> 1
}

// TODO: return true if a outranks b by YEAR only.
fun higherPriority(a: RegRequest, b: RegRequest): Boolean {
    // return yearRank(a.year) > yearRank(b.year)
    TODO("Implement higherPriority")
}

// -------- Minimal manual Max-Heap --------
class MaxYearHeap {
    private val h = mutableListOf<RegRequest>()  // array-backed heap

    private fun parent(i: Int) = (i - 1) / 2
    private fun left(i: Int) = 2 * i + 1
    private fun right(i: Int) = 2 * i + 2
    private fun swap(i: Int, j: Int) { val t = h[i]; h[i] = h[j]; h[j] = t }

    // TODO: bubble up from index `start` while child outranks parent
    private fun percolateUp(start: Int) {
        // var i = start
        // while (i > 0) {
        //     val p = parent(i)
        //     if (higherPriority(h[i], h[p])) {
        //         swap(i, p); i = p
        //     } else break
        // }
        TODO("Implement percolateUp")
    }

    // TODO: push down from index `start` while any child outranks current
    private fun percolateDown(start: Int) {
        // var i = start
        // while (true) {
        //     val l = left(i); val r = right(i)
        //     var largest = i
        //     if (l < h.size && higherPriority(h[l], h[largest])) largest = l
        //     if (r < h.size && higherPriority(h[r], h[largest])) largest = r
        //     if (largest == i) break
        //     swap(i, largest); i = largest
        // }
        TODO("Implement percolateDown")
    }

    // TODO: append at end, then percolateUp
    fun insert(r: RegRequest) {
        // h.add(r)
        // percolateUp(h.lastIndex)
        TODO("Implement insert")
    }

    // TODO: remove-and-return root; move last to root and percolateDown
    fun allocateSeat(): RegRequest? {
        // if (h.isEmpty()) return null
        // val top = h[0]
        // val last = h.removeLast()
        // if (h.isNotEmpty()) { h[0] = last; percolateDown(0) }
        // return top
        TODO("Implement allocateSeat")
    }

    fun asArray(): List<RegRequest> = h.toList()
}

// -------- Tiny demo (FILLED; will run after TODOs are done) --------
fun main() {
    val heap = MaxYearHeap()
    heap.insert(RegRequest("A123", Year.JUNIOR))
    heap.insert(RegRequest("B456", Year.SENIOR))
    heap.insert(RegRequest("C789", Year.SOPHOMORE))
    heap.insert(RegRequest("D234", Year.SENIOR))

    println("Heap array (root at index 0): " + heap.asArray())
    println("Seat 1 -> " + heap.allocateSeat())   // Senior
    println("After seat 1: " + heap.asArray())
    println("Seat 2 -> " + heap.allocateSeat())   // next Senior
    println("After seat 2: " + heap.asArray())
    println("Seat 3 -> " + heap.allocateSeat())   // Junior
    println("After seat 3: " + heap.asArray())
    println("Seat 4 -> " + heap.allocateSeat())   // Sophomore
    println("Seat 5 -> " + heap.allocateSeat())   // null
}
