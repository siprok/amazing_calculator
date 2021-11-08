package com.example.amazing_calculator.presentation.main
import com.notkamui.keval.Keval as Keval
import com.notkamui.keval.KevalException
import kotlin.math.sqrt as sqrt


fun evaluate(sourceString: String?): String =
    try
    {
        val source: String = sourceString?.let{it}.toString()
        val reformedSource = source
            .replace("(-", "(0-")
            .replace("√", "sqrt")
        val kvl = Keval()
            .withDefault()
            .withFunction(
                "sqrt",
                1
            ){sqrt(it[0])}
        kvl.eval(reformedSource).toString()
    }
    catch (e: KevalException)
    {
        e.toString().split(".")[3]
    }

fun evaluatedFormatter(evaluated: String): String =
    if (evaluated.toDoubleOrNull() != null)
    {
        evaluated
    }
    else
    {
        when(evaluated.split(": ")[0])
        {
            "KevalZeroDivisionException" -> "Пристутствует деление на ноль"
            "KevalInvalidSymbolException" -> "Ошибка в написании оператора"
            else -> " "
        }
    }


