package com.y4or4.handy.pi.engine

import com.y4or4.handy.pi.component.PiError
import com.y4or4.handy.pi.component.PiFunction

class Frames(

    val env: PiEngine.Backdoor) {

    val queue; get() = _queue

    val currentFrame; get() = _queue.lastOrNull()

    private val _queue = mutableListOf<Frame>()

    fun pop() {
        val last = queue.lastIndex
        if (0 <= last) {
            val prev = queue.removeAt(last)
            queue.getOrNull(queue.lastIndex)?.let { next ->
                prev.memory.at(0)[Pi.SYS_VAR_RET]?.let {
                    next.memory.at(0)[Pi.SYS_VAR_RET] = it
                }
            }
        }
    }

    fun push(function: PiFunction) {
        queue.add(Frame(env, function))
    }

    fun next(): PiError? {
        currentFrame?.let {
            val error = it.next()
            error ?: pop()
            return error
        }
        return null
    }

    fun hasNext() = currentFrame?.hasNext() ?: false

    fun clear() {
        queue.clear()
    }
}