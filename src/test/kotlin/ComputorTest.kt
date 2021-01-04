import computorv1.models.PolynomialTerm
import models.dataset.Function
import models.dataset.Matrix
import models.dataset.numeric.Complex
import models.dataset.numeric.SetNumber
import models.dataset.wrapping.Brackets
import models.dataset.wrapping.Fraction
import models.dataset.wrapping.FunctionStack
import parser.extensions.deleteSpacesFromMultipleOperations
import parser.extensions.putSpaces

abstract class ComputorTest {
	protected fun String.getList() = putSpaces(this)
		.split(' ')
		.deleteSpacesFromMultipleOperations()
		.filter { it.isNotEmpty() }

	val number = SetNumber(0.1)
	val complex = Complex(2.2, 3.3)
	val function = Function("x", "x^2".getList().toTypedArray())
	val functionStack = FunctionStack(function, number)
	val fraction = Fraction(function, complex)
	val matrix = Matrix("[[1, 2]] ; [[3, 4]]".getList().toTypedArray())
	val pt = PolynomialTerm(function, 3, "y")

	val emptyBrackets = Brackets()

	val middleBrackets = number + pt + function
	val middleStr = "((x^2) * y^3 + 0.1 + (x^2))"

	val fullBrackets = number + complex + function + middleBrackets + functionStack + fraction + pt
	val generalStr = "((x^2) * 2 * y^3 + (2.4 + 3.3i) + (x^2) / (2.2 + 3.3i) + 2.1 * (x^2))"
}