/*
Gould Library returns cart.
When books are returned, they’re placed on TOP of a single cart.
When a student worker starts shelving, they grab the most recently placed book first.
That’s a stack (LIFO) system:

push(x) → place item on the TOP of the stack
pop()   → take the item from the TOP of the stack (or null if empty)
*/

val s = mutableListOf<String>()  // bottom = index 0, TOP = last index

// TODO 1: push x ONTO THE TOP of the stack (one line)
fun push(x: String) {
    // s.add(x)
}

// TODO 2: pop FROM THE TOP; return null if empty (1–3 lines)
fun pop(): String? {
    // if (s.isEmpty()) return null
    // return s.removeAt(s.lastIndex)
}

fun main() {
    println(s)      // []
    push("B1")      // return book B1
    push("B2")      // return book B2
    push("B3")      // return book B3
    println(s)      // [B1, B2, B3]
    println(pop())  // B3 (most recent first)
    println(s)      // [B1, B2]
    println(pop())  // B2
    println(s)      // [B1]
    println(pop())  // B1
    println(pop())  // null (empty)
}
