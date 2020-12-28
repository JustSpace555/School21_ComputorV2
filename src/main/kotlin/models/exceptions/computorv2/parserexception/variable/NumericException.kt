package models.exceptions.computorv2.parserexception.variable

import models.exceptions.computorv2.ComputorV2Exception
import kotlin.reflect.KClass

@ComputorV2Exception
abstract class NumericException : VariableParserException()

@ComputorV2Exception
class NumericFormatException(input: String = "", parseTo: KClass<*>? = null) : NumericException() {
    override val message: String = "SetNumber format exception. " +
            if (input.isNotEmpty() && parseTo != null) "Can't parse $input to ${parseTo.simpleName}" else ""
}