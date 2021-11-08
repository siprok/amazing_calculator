package com.example.amazing_calculator.presentation.main

fun calc_test()
{
    val testStrings = arrayOf("5/0",
        "((5+2)",
        "sqrt(1+3)",
        "√(1+3)",
        "(5+2)(1+3)",
        "2^3",
        "4^0.5",
        "4^(0-0.5)",
        "4^(-0.5)",
        "sqrt(-1)")
    testStrings.forEach { println("Input $it \nOutput ${evaluatedFormatter(evaluate(it))}") }
}

fun main()
{
    calc_test();
}