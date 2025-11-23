package com.cs.comps

class Stack<T> {
    private class Node<E>(val v: E, var next: Node<E>? = null)

    private var head: Node<T>? = null
    private var n = 0

    fun push(item: T) {
        head = Node(item, head)
        n++
    }

    fun pop(): T {
        val h = head ?: error("Stack underflow")
        head = h.next
        n--
        return h.v
    }

    fun peek(): T? = head?.v
    fun isEmpty(): Boolean = head == null
    fun size(): Int = n
}






