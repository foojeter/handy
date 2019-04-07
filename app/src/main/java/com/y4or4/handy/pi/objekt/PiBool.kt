package com.y4or4.handy.pi.objekt

import com.y4or4.handy.pi.component.PiObject
import com.y4or4.handy.pi.engine.Pi

class PiBool(val ktb: Boolean): PiObject(TYPE) {

    companion object {
        const val TYPE = "${Pi.STDLIB_NAMESPACE}.bool"
    }
}