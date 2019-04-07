package com.y4or4.handy.pi.operator

import com.y4or4.handy.pi.component.PiError
import com.y4or4.handy.pi.component.PiOperator
import com.y4or4.handy.pi.engine.Frame
import com.y4or4.handy.pi.engine.PiEngine

class EndIf: PiOperator {

    override fun exe(frame: Frame, env: PiEngine.Backdoor): PiError? {
        frame.memory.decDepth()
        return null
    }
}