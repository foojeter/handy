package com.y4or4.handy.pi.error

import com.y4or4.handy.pi.component.PiError

class UndefinedFunctionError(val functionName: String, val namespace: String): PiError {

    override fun toString(): String {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}