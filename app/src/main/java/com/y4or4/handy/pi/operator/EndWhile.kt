package com.y4or4.handy.pi.operator

import com.y4or4.handy.pi.component.PiError
import com.y4or4.handy.pi.component.PiOperator
import com.y4or4.handy.pi.engine.Frame
import com.y4or4.handy.pi.engine.PiEngine
import com.y4or4.handy.pi.error.WhileOperatorNotFoundError

class EndWhile: PiOperator {

    override fun exe(frame: Frame, env: PiEngine.Backdoor): PiError? {
        for (p in frame.pointer downTo 0) {
            if (frame.operatorAt(p) is While) {
                frame.memory.decDepth()
                frame.jumpTo(p)
                return null
            }
        }
        return WhileOperatorNotFoundError()
    }
}