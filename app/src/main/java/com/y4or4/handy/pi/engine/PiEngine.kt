package com.y4or4.handy.pi.engine

import com.y4or4.handy.pi.component.PiClass
import com.y4or4.handy.pi.component.PiError
import com.y4or4.handy.pi.component.PiFunction
import com.y4or4.handy.pi.operator.FunctionCall

class PiEngine {

    private val definedTypes = mutableMapOf<String, MutableMap<String, PiClass>>()
    private val definedFunctions = mutableMapOf<String, MutableMap<String, PiFunction>>()
    private val frames = Frames(env = this)

    private fun prepare(entryFunc: String, namespace: String) {
        refresh()
        val main = PiFunction(
            name = Pi.SYS_FUNC_MAIN,
            namespace = Pi.SYS_NAMESPACE,
            argTypes = emptyMap(),
            procedure = listOf(FunctionCall(entryFunc, namespace)))
        frames.push(main)
    }

    fun exe(funcName: String, namespace: String): PiError? {
        prepare(funcName, namespace)
        var error: PiError? = null
        while (error == null && frames.hasNext()) {
            error = frames.next()
        }
        return error
    }

    fun refresh() {
        frames.clear()
    }

    fun clear() {
        refresh()
        definedTypes.clear()
        definedFunctions.clear()
    }

    fun debug() = Debugger(BackdoorImpl())

    fun define(klass: PiClass) {
        val map = definedTypes[klass.namespace] ?: mutableMapOf()
        map[klass.name] = klass
        definedTypes[klass.namespace] = map
    }

    fun define(function: PiFunction) {
        val map = definedFunctions[function.namespace] ?: mutableMapOf()
        map[function.name] = function
        definedFunctions[function.namespace] = map
    }

    fun findType(name: String, namespace: String)
            = definedTypes[namespace]?.get(name)

    fun findFunction(name: String, namespace: String)
            = definedFunctions[namespace]?.get(name)

    fun findMethod(name: String, className: String, namespace: String)
            = definedMethodsOf(className, namespace)[name]

    fun definedTypesIn(namespace: String): List<PiClass>
            = definedTypes[namespace]?.values?.toList() ?: emptyList()

    fun definedFunctionsIn(namespace: String): List<PiFunction>
            = definedFunctions[namespace]?.values?.toList() ?: emptyList()

    fun definedMethodsOf(className: String, namespace: String)
            = findType(className, namespace)?.methods ?: emptyMap()

    fun definedNamespaces(): Set<String>
            = definedTypes.keys + definedFunctions.keys

    fun definedTypes(): List<PiClass>
            = definedTypes.flatMap { it.value.values }

    fun definedFunctions(): List<PiFunction>
            = definedFunctions.flatMap { it.value.values }

    interface Backdoor {
        val definedTypes: MutableMap<String, MutableMap<String, PiClass>>
        val definedFunctions: MutableMap<String, MutableMap<String, PiFunction>>
        val frames: Frames
        fun findClass(name: String, namespace: String): PiClass?
        fun findFunction(name: String, namespace: String): PiFunction?
        fun findMethod(name: String, className: String, namespace: String): PiClass.Method?
        fun definedTypesIn(namespace: String): List<PiClass>
        fun definedFunctionsIn(namespace: String): List<PiFunction>
        fun definedMethodsOf(className: String, namespace: String): Map<String, PiClass.Method>
        fun definedNamespaces(): Set<String>
        fun definedTypes(): List<PiClass>
        fun definedFunctions(): List<PiFunction>
    }

    private inner class BackdoorImpl: Backdoor {
        override val definedTypes; get() = this@PiEngine.definedTypes
        override val definedFunctions; get() = this@PiEngine.definedFunctions
        override val frames; get() = this@PiEngine.frames
        override fun findClass(name: String, namespace: String) = this@PiEngine.findType(name, namespace)
        override fun findFunction(name: String, namespace: String) = this@PiEngine.findFunction(name, namespace)
        override fun findMethod(name: String, className: String, namespace: String) = this@PiEngine.findMethod(name, className, namespace)
        override fun definedTypesIn(namespace: String) = this@PiEngine.definedTypesIn(namespace)
        override fun definedFunctionsIn(namespace: String) = this@PiEngine.definedFunctionsIn(namespace)
        override fun definedMethodsOf(className: String, namespace: String) = this@PiEngine.definedMethodsOf(className, namespace)
        override fun definedNamespaces() = this@PiEngine.definedNamespaces()
        override fun definedTypes() = this@PiEngine.definedTypes()
        override fun definedFunctions() = this@PiEngine.definedFunctions()
    }
}