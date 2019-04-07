package com.y4or4.handy.pi.operator

import com.y4or4.handy.pi.component.PiError
import com.y4or4.handy.pi.engine.Frame
import com.y4or4.handy.pi.engine.Pi
import com.y4or4.handy.pi.engine.PiEngine
import com.y4or4.handy.pi.error.UndefinedMethodError
import com.y4or4.handy.pi.error.UndefinedSymbolError

class MethodCall(
    val obj: String,
    val methodName: String,
    val args: Map<String, String>): PushingFrameOperator() {

    override fun exe(frame: Frame, env: PiEngine.Backdoor): PiError? {
        val ob = frame.memory[obj]
        ob ?: return UndefinedSymbolError(obj)
        val method = ob.type.methods[methodName]
        method ?: return UndefinedMethodError(methodName, ob.type)
        return pushNewFrame(env, frame, method, args, mapOf(Pi.SYS_VAR_SELF to ob))
    }
}