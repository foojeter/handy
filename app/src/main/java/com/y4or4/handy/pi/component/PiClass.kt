package com.y4or4.handy.pi.component

open class PiClass(
    val name: String,
    val namespace: String,
    val attrTypes: Map<String, String>,
    val constructor: Method,
    val methods: Map<String, Method>) {

    val qualifiedName = "$namespace.$name"

    inner class Method(
        name: String,
        argTypes: Map<String, String>,
        procedure: List<PiOperator>)
        : PiFunction(name, "$namespace.${this.name}", argTypes, procedure)

    inner class Constructor(
        argTypes: Map<String, String>,
        procedure: List<PiOperator>)
        : PiFunction(name, "$namespace.${this.name}", argTypes, procedure)
}