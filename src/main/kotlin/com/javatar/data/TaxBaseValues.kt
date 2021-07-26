package com.javatar.data

class TaxBaseValues(
    val BCBracket: IntArray = intArrayOf(),
    val BCRate: DoubleArray = doubleArrayOf(),
    val federalBracket: IntArray = intArrayOf(),
    val federalRate: DoubleArray = doubleArrayOf()
) {

    override fun toString(): String {
        return "TaxBaseValues(BCBracket=${BCBracket.contentToString()}, BCRate=${BCRate.contentToString()}, federalBracket=${federalBracket.contentToString()}, federalRate=${federalRate.contentToString()})"
    }
}