package com.y4or4.handy.pi.component

import com.y4or4.handy.pi.engine.Frame
import com.y4or4.handy.pi.engine.PiEngine

interface PiOperator {
    fun exe(frame: Frame, env: PiEngine.Backdoor): PiError?
}