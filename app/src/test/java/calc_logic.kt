import com.notkamui.keval.Keval as Keval
import com.notkamui.keval.KevalException
import kotlin.math.pow
import kotlin.math.sqrt as sqrt


fun evaluate(sourceString: String): String =
    try
    {
        val reformedSource = sourceString
            .replace("^(-", "^(0-")
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
            "KevalInvalidExpressionException" -> "Некорректное выражение"
            "KevalInvalidSymbolException" -> "Ошибка в написании оператора"
            else -> "Нерассмотренная ошибка"
        }
    }

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
