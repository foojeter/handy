package com.y4or4.handy.pi.operator

import com.y4or4.handy.pi.component.PiError
import com.y4or4.handy.pi.component.PiOperator
import com.y4or4.handy.pi.engine.Frame
import com.y4or4.handy.pi.engine.PiEngine
import com.y4or4.handy.pi.error.EndWhileOperatorNotFoundError
import com.y4or4.handy.pi.error.TypeMismatchError
import com.y4or4.handy.pi.error.UndefinedSymbolError
import com.y4or4.handy.pi.objekt.PiBool

class While(val condition: String): PiOperator {

    override fun exe(frame: Frame, env: PiEngine.Backdoor): PiError? {
        val con = frame.memory[condition]
        con ?: return UndefinedSymbolError(condition)
        if (con !is PiBool) return TypeMismatchError(PiBool.TYPE, con.type)
        if (con.ktb) frame.memory.incDepth()
        else for (p in frame.pointer until frame.size) {
            if (frame.operatorAt(p) is EndWhile) {
                frame.jumpTo(p + 1)
                return null
            }
        }
        return EndWhileOperatorNotFoundError()
    }
}