import computorv1.models.PolynomialTerm
import org.junit.Test
import org.junit.Assert.assertEquals

class PolynomialTermTests {

	@Test
	fun toStringTest() {
		val t = PolynomialTerm(1, 2)
		assertEquals("1 * X^2", t.toString())

		t.number = 0
		t.degree = 0
		assertEquals("0 * X^0", t.toString())

		t.number = 2.3
		assertEquals("2.3 * X^0", t.toString())

		t.number = -1.1
		t.degree = 2
		assertEquals("-1.1 * X^2", t.toString())
	}

	@Test
	fun testAdd() {
		var t1 = PolynomialTerm(10, 2)
		val t2 = PolynomialTerm(-10, 2)

		t1 += t2
		assertEquals(0, t1.number)
		assertEquals(2, t1.degree)
	}
}
