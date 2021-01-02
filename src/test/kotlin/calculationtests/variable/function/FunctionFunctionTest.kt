package calculationtests.variable.function

import models.dataset.Function
import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.extensions.putSpaces

class FunctionFunctionTest : FunctionExpressionTest("z", "z^2 + z + 1") {

    private val secondFunctionStr = "x^3"
    private val secondFunction = Function("x", secondFunctionStr.getList().toTypedArray())

    @Test
    fun validPlusFunctionTest() {
        assertEquals("2 * ($functionStr)", (function + function).toString())
        assertEquals("($functionStr) + ($secondFunctionStr)", (function + secondFunction).toString())
    }

    @Test
    fun validMinusFunctionTest() {
        assertEquals("0", (function - function).toString())
        assertEquals("($functionStr) - ($secondFunctionStr)", (function - secondFunction).toString())
    }

    @Test
    fun validTimesFunctionTest() {
        assertEquals("($functionStr) * ($functionStr)", (function * function).toString())
        assertEquals("($functionStr) * ($secondFunctionStr)", (function * secondFunction).toString())
    }

    @Test
    fun validDivFunctionTest() {
        assertEquals("(($functionStr) / $($functionStr))", (function / function).toString())
        assertEquals("($functionStr) / ($secondFunctionStr)", (function / secondFunction).toString())
    }

    @Test(expected = IllegalOperationException::class)
    fun `fail rem Function test`() {
        function % function
    }

    @Test(expected = IllegalOperationException::class)
    fun `fail rem second Function test`() {
        function % secondFunction
    }

    @Test(expected = IllegalOperationException::class)
    fun `fail pow Function test`() {
        function.pow(function)
    }

    @Test(expected = IllegalOperationException::class)
    fun `fail pow second Function test`() {
        function.pow(secondFunction)
    }

    /*
    @Test
    fun validInvokeTest() {
        assertEquals("$functionStr($secondFunctionStr)", function(secondFunction))
    }
     */
}