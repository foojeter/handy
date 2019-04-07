package com.y4or4.handy.pi.operator

import com.y4or4.handy.pi.component.PiError
import com.y4or4.handy.pi.component.PiObject
import com.y4or4.handy.pi.engine.Frame
import com.y4or4.handy.pi.engine.Pi
import com.y4or4.handy.pi.engine.PiEngine
import com.y4or4.handy.pi.error.UndefinedClassError

class NewObject(
    val className: String,
    val namespace: String,
    val args: Map<String, String>): PushingFrameOperator() {

    override fun exe(frame: Frame, env: PiEngine.Backdoor): PiError? {
        val klass = env.findClass(className, namespace)
        klass ?: return UndefinedClassError(className, namespace)
        val obj = PiObject(klass)
        return pushNewFrame(env, frame, klass.constructor, args, mapOf(Pi.SYS_VAR_SELF to obj))
    }
}