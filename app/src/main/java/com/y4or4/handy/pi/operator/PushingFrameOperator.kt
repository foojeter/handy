package com.y4or4.handy.pi.operator

import com.y4or4.handy.pi.component.PiError
import com.y4or4.handy.pi.component.PiFunction
import com.y4or4.handy.pi.component.PiObject
import com.y4or4.handy.pi.component.PiOperator
import com.y4or4.handy.pi.engine.Frame
import com.y4or4.handy.pi.engine.PiEngine
import com.y4or4.handy.pi.error.ArgNotFoundError
import com.y4or4.handy.pi.error.TypeMismatchError
import com.y4or4.handy.pi.error.UndefinedSymbolError

abstract class PushingFrameOperator: PiOperator {

    private val mappedArgs = mutableMapOf<String, PiObject>()

    protected fun pushNewFrame(
        env: PiEngine.Backdoor,
        frame: Frame,
        function: PiFunction,
        args: Map<String, String>,
        extraArgs: Map<String, PiObject>): PiError? {

        function.argTypes.forEach {
            val argName = it.key
            val argType = it.value
            val symbol = args[argName]
            symbol ?: return ArgNotFoundError(argName, argType)
            val arg = frame.memory[symbol]
            arg ?: return UndefinedSymbolError(it.value)
            val type = arg.type.qualifiedName
            if (type != argType) return TypeMismatchError(argType, type)
            mappedArgs[argName] = arg
        }

        env.frames.push(function)
        val fr = env.frames.queue.last()
        fr.memory += mappedArgs
        fr.memory += extraArgs
        mappedArgs.clear()
        return null
    }
}