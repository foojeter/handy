package com.y4or4.handy.pi.component

open class PiObject(val type: PiClass) {

    val attrs = mutableMapOf<String, PiObject>()

    operator fun get(attrName: String): PiObject? {
        return attrs[attrName]
    }

    operator fun set(attrName: String, value: PiObject) {
        attrs[attrName] = value
    }
}