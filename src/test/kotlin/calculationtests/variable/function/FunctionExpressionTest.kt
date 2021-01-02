package calculationtests.variable.function

import ComputorTest
import models.dataset.Function

abstract class FunctionExpressionTest(parameter: String, val functionStr: String) : ComputorTest() {
    open var function = Function(parameter, functionStr.getList().toTypedArray())
}