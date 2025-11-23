package com.carleton.comps

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class StackTest {

    @Test
    fun pushesIncreaseSizeAndPeekShowsTop() {
        val st = Stack<Int>()
        assertTrue(st.isEmpty())
        assertEquals(0, st.size())

        st.push(10)
        assertFalse(st.isEmpty())
        assertEquals(1, st.size())
        assertEquals(10, st.peek())

        st.push(20)
        assertEquals(2, st.size())
        assertEquals(20, st.peek()) // last pushed is on top
    }

    @Test
    fun popReturnsTopAndShrinks() {
        val st = Stack<String>()
        st.push("a"); st.push("b"); st.push("c")
        assertEquals(3, st.size())

        assertEquals("c", st.pop())
        assertEquals(2, st.size())
        assertEquals("b", st.peek())

        assertEquals("b", st.pop())
        assertEquals(1, st.size())
        assertEquals("a", st.peek())

        assertEquals("a", st.pop())
        assertEquals(0, st.size())
        assertTrue(st.isEmpty())
    }

    @Test
    fun lifoOrderingWithMultipleElements() {
        val st = Stack<Int>()
        (1..5).forEach { st.push(it) } // stack top = 5
        val popped = mutableListOf<Int>()
        while (!st.isEmpty()) popped += st.pop()
        assertEquals(listOf(5, 4, 3, 2, 1), popped) // LIFO
    }

    @Test
    fun peekOnEmptyReturnsNull() {
        val st = Stack<Double>()
        assertNull(st.peek())
        st.push(3.14)
        assertEquals(3.14, st.peek())
        st.pop()
        assertNull(st.peek())
    }

    @Test
    fun popOnEmptyThrows() {
        val st = Stack<Int>()
        assertThrows<NoSuchElementException> { st.pop() }
    }

    @Test
    fun sizeAndIsEmptyStayConsistentThroughOps() {
        val st = Stack<Int>()
        assertTrue(st.isEmpty())
        st.push(1); st.push(2); st.push(3)
        assertEquals(3, st.size())
        assertFalse(st.isEmpty())

        st.pop()
        assertEquals(2, st.size())
        st.pop()
        assertEquals(1, st.size())
        st.pop()
        assertEquals(0, st.size())
        assertTrue(st.isEmpty())
    }

    @Test
    fun worksWithGenericTypes() {
        data class E(val id: Int, val name: String)
        val st = Stack<E>()
        st.push(E(1, "Ada"))
        st.push(E(2, "Grace"))
        assertEquals(E(2, "Grace"), st.peek())
        assertEquals(E(2, "Grace"), st.pop())
        assertEquals(E(1, "Ada"), st.pop())
    }
}

