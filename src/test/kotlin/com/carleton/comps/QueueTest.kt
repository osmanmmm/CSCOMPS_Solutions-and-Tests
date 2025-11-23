package com.carleton.comps

import com.cs.comps.LinkedQueue
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class QueueTest {

    @Test
    fun enqueuesIncreaseSizeAndPeekShowsFront() {
        val q = LinkedQueue<Int>()
        assertTrue(q.isEmpty())
        assertEquals(0, q.size())

        q.enqueue(10)
        assertFalse(q.isEmpty())
        assertEquals(1, q.size())
        assertEquals(10, q.peek())

        q.enqueue(20)
        assertEquals(2, q.size())
        // front stays 10
        assertEquals(10, q.peek())
    }

    @Test
    fun dequeueReturnsFrontAndShrinks() {
        val q = LinkedQueue<String>()
        q.enqueue("a"); q.enqueue("b"); q.enqueue("c")
        assertEquals(3, q.size())

        assertEquals("a", q.dequeue())
        assertEquals(2, q.size())
        assertEquals("b", q.peek())

        assertEquals("b", q.dequeue())
        assertEquals(1, q.size())
        assertEquals("c", q.peek())

        assertEquals("c", q.dequeue())
        assertEquals(0, q.size())
        assertTrue(q.isEmpty())
    }

    @Test
    fun fifoOrderingWithMultipleElements() {
        val q = LinkedQueue<Int>()
        (1..5).forEach { q.enqueue(it) } // front = 1
        val out = mutableListOf<Int>()
        while (!q.isEmpty()) out += q.dequeue()
        assertEquals(listOf(1, 2, 3, 4, 5), out) // FIFO
    }

    @Test
    fun peekOnEmptyReturnsNull() {
        val q = LinkedQueue<Double>()
        assertNull(q.peek())
        q.enqueue(3.14)
        assertEquals(3.14, q.peek())
        q.dequeue()
        assertNull(q.peek())
    }

    @Test
    fun dequeueOnEmptyThrows() {
        val q = LinkedQueue<Int>()
        assertThrows<NoSuchElementException> { q.dequeue() }
    }

    @Test
    fun sizeAndIsEmptyStayConsistentThroughOps() {
        val q = LinkedQueue<Int>()
        assertTrue(q.isEmpty())
        q.enqueue(1); q.enqueue(2); q.enqueue(3)
        assertEquals(3, q.size())
        assertFalse(q.isEmpty())

        q.dequeue()
        assertEquals(2, q.size())
        q.dequeue()
        assertEquals(1, q.size())
        q.dequeue()
        assertEquals(0, q.size())
        assertTrue(q.isEmpty())
        assertNull(q.peek())
    }

    @Test
    fun worksWithGenericTypes() {
        data class E(val id: Int, val name: String)
        val q = LinkedQueue<E>()
        q.enqueue(E(1, "Ada"))
        q.enqueue(E(2, "Grace"))
        assertEquals(E(1, "Ada"), q.peek())
        assertEquals(E(1, "Ada"), q.dequeue())
        assertEquals(E(2, "Grace"), q.dequeue())
    }

    @Test
    fun interleavedEnqueueDequeueMaintainsOrder() {
        val q = LinkedQueue<Int>()
        q.enqueue(1)
        q.enqueue(2)
        assertEquals(1, q.dequeue())

        q.enqueue(3)
        q.enqueue(4)
        assertEquals(2, q.dequeue())
        assertEquals(3, q.dequeue())
        assertEquals(4, q.dequeue())

        assertTrue(q.isEmpty())
        assertNull(q.peek())
    }
}