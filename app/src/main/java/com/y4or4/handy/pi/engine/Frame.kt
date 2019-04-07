package com.y4or4.handy.pi.engine

import com.y4or4.handy.pi.component.PiError
import com.y4or4.handy.pi.component.PiFunction
import com.y4or4.handy.pi.component.PiObject

class Frame (

    val env: PiEngine.Backdoor,

    val function: PiFunction) {

    val memory = HierarchicalMemory<String, PiObject>()

    val nextOperator; get() = function.procedure.getOrNull(pointer)

    val size; get() = function.procedure.size

    var pointer = 0; private set

    fun next(): PiError? {
        nextOperator?.let {
            val error = it.exe(this, env)
            error ?: ++pointer
            return error
        }
        return null
    }

    fun hasNext() = pointer < size

    // This method should be called in the context of next().
    fun jumpTo(index: Int) {
        pointer = index - 1
    }

    fun operatorAt(index: Int)
            = function.procedure[index]
}
