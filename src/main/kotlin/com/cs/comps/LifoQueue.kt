package com.cs.comps

class LinkedQueue<T> {
    private class Node<E>(val v: E, var next: Node<E>? = null)
    private var head: Node<T>? = null
    private var tail: Node<T>? = null
    private var n = 0

    fun isEmpty() = n == 0
    fun size() = n

    fun enqueue(x: T) {
        val node = Node(x)
        if (tail == null) { head = node; tail = node }
        else { tail!!.next = node; tail = node }
        n++
    }

    fun dequeue(): T {
        val h = head ?: error("Queue underflow")
        val v = h.v
        head = h.next
        if (head == null) tail = null
        n--
        return v
    }

    fun peek(): T? = head?.v
}


