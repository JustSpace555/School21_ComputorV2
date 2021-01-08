package calculationtests.variable.wrapping.brackets

import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import assertEquals
import org.junit.Test

class BracketsSetNumberTest : BracketsExpressionTest() {

    @Test
    fun validPlusSetNumberTest() {
        assertEquals(generalStr, (fullBrackets + SetNumber()).toString())


        assertEquals(
            "((x^2) * 2 * y^3 + (3.3 + 3.3i) + (x^2) / (2.2 + 3.3i) + 2.1 * (x^2))",
            (fullBrackets + SetNumber(0.9)).toString()
        )

        assertEquals(
            "((x^2) * 2 * y^3 + (12.3 + 3.3i) + (x^2) / (2.2 + 3.3i) + 2.1 * (x^2))",
            (fullBrackets + SetNumber(-0.1) + SetNumber(10)).toString()
        )
    }

    @Test
    fun validMinusSetNumberTest() {
        assertEquals(generalStr, (fullBrackets - SetNumber()).toString())


        assertEquals(
            "((x^2) * 2 * y^3 + (2.3 + 3.3i) + (x^2) / (2.2 + 3.3i) + 2.1 * (x^2))",
            (fullBrackets - number).toString()
        )

        assertEquals(
            "((x^2) * 2 * y^3 + (-7.7 + 3.3i) + (x^2) / (2.2 + 3.3i) + 2.1 * (x^2))",
            (fullBrackets - number - SetNumber(10)).toString()
        )
    }

    @Test
    fun validTimesSetNumberTest() {
        assertEquals("0", (fullBrackets * SetNumber()).toString())
        assertEquals(generalStr, (fullBrackets * SetNumber(1)).toString())
        assertEquals(
            "(-2 * (x^2) * y^3 + (-2.4 - 3.3i) + ((x^2) * -1) / (2.2 + 3.3i) - 2.1 * (x^2))",
            (fullBrackets * SetNumber(-1)).toString()
        )


        assertEquals(
            "(20 * (x^2) * y^3 + (24 + 33i) + ((x^2) * 10) / (2.2 + 3.3i) + 21 * (x^2))",
            (fullBrackets * SetNumber(10)).toString()
        )

        assertEquals(
            "(0.2 * (x^2) * y^3 + (0.24 + 0.33i) + ((x^2) * 0.1) / (2.2 + 3.3i) + 0.21 * (x^2))",
            (fullBrackets * SetNumber(0.1)).toString()
        )
    }

    @Test(expected = DivideByZeroException::class)
    fun `fail div SetNumber test`() {
        fullBrackets / SetNumber()

    }

    @Test
    fun validDivSetNumberTest() {
        assertEquals(generalStr, (fullBrackets / SetNumber(1)).toString())
        assertEquals(
            "(-2 * (x^2) * y^3 + (-2.4 - 3.3i) + (x^2) / (-2.2 - 3.3i) - 2.1 * (x^2))",
            (fullBrackets / SetNumber(-1)).toString()
        )

        assertEquals(
            "(20 * (x^2) * y^3 + (24 + 33i) + (x^2) / (0.22 + 0.33i) + 21 * (x^2))",
            (fullBrackets / number).toString()
        )
    }

    @Test(expected = IllegalOperationException::class)
    fun `fail rem SetNumber test`() {
        fullBrackets % SetNumber(1)
    }

    @Test(expected = IllegalOperationException::class)
    fun `fail pow SetNumber test`() {
        fullBrackets.pow(1.1)
    }

    @Test
    fun validPowSetNumberTest() {
        assertEquals("1", fullBrackets.pow(0).toString())
        assertEquals(generalStr, fullBrackets.pow(1).toString())
        assertEquals("1 / $generalStr", fullBrackets.pow(-1).toString())

        assertEquals(
            "((x^2) * (x^2) * y^6 + 0.2 * (x^2) * y^3 + 0.01 + (x^2) * 2 * y^3 * (x^2) + 0.2 * (x^2) + (x^2) * (x^2))",
            middleBrackets.pow(2).toString()
        )
    }
}