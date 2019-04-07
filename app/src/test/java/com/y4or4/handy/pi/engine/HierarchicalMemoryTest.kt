package com.y4or4.handy.pi.engine

import org.junit.Assert.assertEquals
import org.junit.Test

class HierarchicalMemoryTest {

    @Test
    fun test() {
        val memory = HierarchicalMemory<String, String>()
        assertEquals(0, memory.depth)
        memory["a"] = "gom"
        memory["b"] = "gum"
        assertEquals("gom", memory["a"])
        assertEquals("gum", memory["b"])
        assertEquals(null, memory["c"])
        memory.incDepth()
        assertEquals(1, memory.depth)
        memory[0, "c"] = "foo"
        memory["c"] = "bee"
        memory["d"] = "rom"
        assertEquals("bee", memory["c"])
        assertEquals("foo", memory[0, "c"])
        for (depth in 0..memory.depth) {
            println(memory.at(depth))
        }
        memory.decDepth()
        assertEquals("foo", memory["c"])
        assertEquals(0, memory.depth)
    }
}