package calculationtests.variable.wrapping.brackets

import models.dataset.numeric.SetNumber
import models.exceptions.computorv2.calcexception.DivideByZeroException
import models.exceptions.computorv2.calcexception.variable.IllegalOperationException
import org.junit.Assert.assertEquals
import org.junit.Test

class BracketsSetNumberTest : BracketsExpressionTest() {

    @Test
    fun validPlusSetNumberTest() {
//        assertEquals(generalStr, (fullBrackets + SetNumber()).toString())


        assertEquals(
            "((x^2)^3 + 1 + (2.2 + 3.3i) + (x^2) + 0.1 * (x^2) + (x^2) / (2.2 + 3.3i))",
            (fullBrackets + SetNumber(0.9)).toString()
        )

        assertEquals(
            "((x^2)^3 + (2.2 + 3.3i) + (x^2) + 0.1 * (x^2) + (x^2) / (2.2 + 3.3i)) + 10",
            (fullBrackets + SetNumber(-0.1) + SetNumber(10)).toString()
        )
    }

    @Test
    fun validMinusSetNumberTest() {
        assertEquals(
            "((x^2)^3 + 0.1 + (2.2 + 3.3i) + (x^2) + 0.1 * (x^2) + (x^2) / (2.2 + 3.3i))",
            (fullBrackets - SetNumber()).toString()
        )


        assertEquals(
            "((x^2)^3 + (2.2 + 3.3i) + (x^2) + 0.1 * (x^2) + (x^2) / (2.2 + 3.3i))",
            (fullBrackets - SetNumber(0.1)).toString()
        )

        assertEquals(
            "((x^2)^3 + (2.2 + 3.3i) + (x^2) + 0.1 * (x^2) + (x^2) / (2.2 + 3.3i)) + 10",
            (fullBrackets - SetNumber(0.1) - SetNumber(10)).toString()
        )
    }

    @Test
    fun validTimesSetNumberTest() {
        assertEquals("0", (fullBrackets * SetNumber()).toString())
        assertEquals(generalStr, (fullBrackets * SetNumber(1)).toString())
        assertEquals(
            "(-0.1 - (2.2 + 3.3i) - (x^2) - 0.1 * (x^2) - (x^2) / (2.2 + 3.3i) - (x^2)^3)",
            (fullBrackets * SetNumber(-1)).toString()
        )


        assertEquals(
            "(10 + (22 + 33i) + 10 * (x^2) + (x^2) + (10 * (x^2)) / (2.2 + 3.3i) + 10 * (x^2)^3)",
            (fullBrackets * SetNumber(10)).toString()
        )

        assertEquals(
            "(-10 - (22 + 33i) - 10 * (x^2) - (x^2) - (10 * (x^2)) / (2.2 + 3.3i) - 10 * (x^2)^3)",
            (fullBrackets * SetNumber(10)).toString()
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
            "(-0.1 - (2.2 + 3.3i) - (x^2) - 0.1 * (x^2) - (x^2) / (2.2 + 3.3i) - (x^2)^3)",
            (fullBrackets / SetNumber(-1)).toString()
        )

        assertEquals(
            "(1 + (22 + 33i) + (x^2) / 0.1 + (x^2) + (10 * (x^2)) / (2.2 + 3.3i) + ((x^2) / 0.1)^3)",
            (fullBrackets / SetNumber(0.1)).toString()
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
//        assertEquals("1", fullBrackets.pow(0).toString())
//        assertEquals(generalStr, fullBrackets.pow(1).toString())
//        assertEquals("1 / $generalStr", fullBrackets.pow(-1).toString())

        assertEquals(
            "(x^2)^6 + 2 * (x^2)^4 + 0.2 * (x^2)^3 + (x^2)^2 + 0.2 * (x^2) + 0.01",
            middleBrackets.pow(2)
        )
    }
}