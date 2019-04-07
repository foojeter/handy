package com.y4or4.handy.pi.engine

class HierarchicalMemory<K, V> {

    private val hierarchy = mutableListOf(mutableMapOf<K, V>())

    var depth = 0; private set

    fun incDepth() {
        ++depth
        if (depth <= hierarchy.size) {
            hierarchy.add(mutableMapOf())
        }
    }

    fun decDepth() {
        --depth
    }

    fun clear() {
        hierarchy.clear()
        hierarchy.add(mutableMapOf())
        depth = 0
    }

    fun at(depth: Int) = hierarchy[depth]

    operator fun get(symbol: K) = get(depth, symbol)

    operator fun get(startDepth: Int, symbol: K): V? {
        for (d in startDepth downTo 0) {
            hierarchy.getOrNull(d)?.get(symbol)?.let { return it }
        }
        return null
    }

    operator fun set(symbol: K, value: V) = set(depth, symbol, value)

    operator fun set(depth: Int, symbol: K, value: V) {
        hierarchy.getOrNull(depth)?.put(symbol, value)
    }

    operator fun plusAssign(map: Map<K, V>) {
        hierarchy[depth].putAll(map)
    }
}