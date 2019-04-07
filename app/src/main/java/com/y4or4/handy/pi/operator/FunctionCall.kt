package com.y4or4.handy.pi.operator

import com.y4or4.handy.pi.component.PiError
import com.y4or4.handy.pi.engine.Frame
import com.y4or4.handy.pi.engine.PiEngine
import com.y4or4.handy.pi.error.UndefinedFunctionError

class FunctionCall(
    val functionName: String,
    val namespace: String,
    val args: Map<String, String> = emptyMap()): PushingFrameOperator() {

    override fun exe(frame: Frame, env: PiEngine.Backdoor): PiError? {
        val func = env.findFunction(functionName, namespace)
        func ?: return UndefinedFunctionError(functionName, namespace)
        return pushNewFrame(env, frame, func, args, emptyMap())
    }
}