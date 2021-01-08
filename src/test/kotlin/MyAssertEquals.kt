import junit.framework.ComparisonFailure
import java.lang.AssertionError
import org.junit.Assert.assertEquals

fun assertEquals(expected: Any?, actual: Any?) {
	val element = Thread.currentThread().stackTrace[2]
	try {
		println("Testing ${element.methodName} in ${element.className}")
		println("$expected\n==\n$actual\n?")
		assertEquals(expected, actual)
		println("OK\n")
	} catch (e: AssertionError) {
		println("FAIL\n")
		throw ComparisonFailure("Values are not equal", expected.toString(), actual.toString())
	}
}