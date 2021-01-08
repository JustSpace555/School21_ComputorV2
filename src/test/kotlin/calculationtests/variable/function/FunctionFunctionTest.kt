package calculationtests.variable.function

import assertEquals
import models.dataset.Function
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Test

class FunctionFunctionTest : FunctionExpressionTest("z", "z^2 + z + 1") {

    private val secondFunctionStr = "x^3"
    private val secondFunction = Function("x", secondFunctionStr.getList().toTypedArray())

    @Test
    fun validPlusFunctionTest() {
        assertEquals("2 * ($functionStr)", (newFunction + newFunction).toString())
        assertEquals("(($functionStr) + ($secondFunctionStr))", (newFunction + secondFunction).toString())
    }

    @Test
    fun validMinusFunctionTest() {
        assertEquals("0", (newFunction - newFunction).toString())
        assertEquals("(($functionStr) - ($secondFunctionStr))", (newFunction - secondFunction).toString())
    }

    @Test
    fun validTimesFunctionTest() {
        assertEquals("($functionStr) * ($functionStr)", (newFunction * newFunction).toString())
        assertEquals("($functionStr) * ($secondFunctionStr)", (newFunction * secondFunction).toString())
    }

    @Test
    fun validDivFunctionTest() {
        assertEquals("1", (newFunction / newFunction).toString())
        assertEquals("($functionStr) / ($secondFunctionStr)", (newFunction / secondFunction).toString())
    }

    @Test(expected = IllegalOperationException::class)
    fun `fail rem Function test`() {
        newFunction % newFunction
    }

    @Test(expected = IllegalOperationException::class)
    fun `fail rem second Function test`() {
        newFunction % secondFunction
    }

    @Test(expected = IllegalOperationException::class)
    fun `fail pow Function test`() {
        newFunction.pow(newFunction)
    }

    @Test(expected = IllegalOperationException::class)
    fun `fail pow second Function test`() {
        newFunction.pow(secondFunction)
    }

    /*
    @Test
    fun validInvokeTest() {
        assertEquals("$functionStr($secondFunctionStr)", newFunction(secondFunction))
    }
     */
}