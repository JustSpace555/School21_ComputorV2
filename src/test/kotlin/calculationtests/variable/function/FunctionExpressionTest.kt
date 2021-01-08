package calculationtests.variable.function

import ComputorTest
import models.dataset.Function

abstract class FunctionExpressionTest(parameter: String, val functionStr: String) : ComputorTest() {
    open var newFunction = Function(parameter, functionStr.getList().toTypedArray())
}