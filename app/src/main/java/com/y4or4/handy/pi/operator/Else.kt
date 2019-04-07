package com.y4or4.handy.pi.operator

import com.y4or4.handy.pi.component.PiError
import com.y4or4.handy.pi.component.PiOperator
import com.y4or4.handy.pi.engine.Frame
import com.y4or4.handy.pi.engine.PiEngine
import com.y4or4.handy.pi.error.EndIfOperatorNotFoundError

class Else: PiOperator {

    override fun exe(frame: Frame, env: PiEngine.Backdoor): PiError? {
        for (p in frame.pointer until frame.size) {
            if (frame.operatorAt(p) is EndIf) {
                frame.jumpTo(p)
                return null
            }
        }
        return EndIfOperatorNotFoundError()
    }
}