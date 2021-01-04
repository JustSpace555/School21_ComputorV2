package calculationtests.variable.function

import models.dataset.Function
import models.dataset.numeric.Complex
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Assert.assertEquals
import org.junit.Test
import parser.extensions.putSpaces

class FunctionComplexTest : FunctionExpressionTest("z", "z^2 + z + 1") {

    @Test
    fun validPlusComplexTest() {
        assertEquals(functionStr, (newFunction + Complex(0, 0)).toString())
        assertEquals("(i + ($functionStr))", (newFunction + Complex(0, 1)).toString())
        assertEquals("((1 + i) + ($functionStr))", (newFunction + Complex(1, 1)).toString())
    }

    @Test
    fun validMinusComplexTest() {
        assertEquals(functionStr, (newFunction - Complex(0, 0)).toString())
        assertEquals("(-i + ($functionStr))", (newFunction - Complex(0, 1)).toString())
        assertEquals("((-1 - i) + ($functionStr))", (newFunction - Complex(1, 1)).toString())
    }

    @Test
    fun validTimesComplexTest() {
        assertEquals("0", (newFunction * Complex(0, 0)).toString())
        assertEquals("($functionStr) * i", (newFunction * Complex(0, 1)).toString())
        assertEquals("($functionStr) * (1 + 2i)", (newFunction * Complex(1, 2)).toString())
    }

    @Test(expected = DivideByZeroException::class)
    fun `fail div Complex zero test`() {
        newFunction / Complex(0, 0)
    }

    @Test
    fun validDivComplexTest() {
        assertEquals("($functionStr) / i", (newFunction / Complex(0, 1)).toString())
        assertEquals("($functionStr) / (1 + i)", (newFunction / Complex(1, 1)).toString())
        assertEquals("($functionStr) / (-1 - 2.2i)", (newFunction / Complex(-1, -2.2)).toString())
    }

    @Test(expected = IllegalOperationException::class)
    fun `fail rem complex test`() {
        newFunction % Complex(1, 1)
    }

    @Test(expected = IllegalOperationException::class)
    fun `fail pow complex test`() {
        newFunction.pow(Complex(1, 1))
    }

    @Test
    fun validInvokeTest() {
        assertEquals(Complex(2, 3), newFunction(Complex(1, 1)))

        newFunction = Function(
            "abc",
            putSpaces("0.5 * abc - abc + abc ^ 2 - (abc + abc * 2)").split(' ').toTypedArray()
        )
        assertEquals(Complex(-3.5, -1.5), newFunction(Complex(1, 1)))
    }
}