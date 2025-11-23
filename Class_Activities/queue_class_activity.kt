/*
During registration week, CS 201 fills up fast.
When students try to join after it’s full, the Registrar’s website puts them on a waitlist.
Each student joins the line in the order they click “Join Waitlist.”
When someone drops the class, the first student in line automatically gets the seat.

That’s a queue (FIFO) system:

enqueue() → a new student joins the end of the line
dequeue() → the first student in line gets a seat and leaves the queue
*/

val q = mutableListOf<String>()  // front = index 0, back = last index

// TODO 1: add x to the BACK of q (one line)
fun enqueue(x: String) {
    // q.add(x)
}

// TODO 2: remove-and-return from the FRONT of q; return null if empty (1–3 lines)
fun dequeue(): String? {
    // if (q.isEmpty()) return null
    // return q.removeAt(0)
}

fun main() {
    println(q)                 // []
    enqueue("A123")            // A123 joins
    enqueue("B456")            // B456 joins
    enqueue("C789")            // C789 joins
    println(q)                 // [A123, B456, C789]
    println(dequeue())         // A123
    println(q)                 // [B456, C789]
    println(dequeue())         // B456
    println(q)                 // [C789]
    println(dequeue())         // C789
    println(dequeue())         // null (empty)
}
