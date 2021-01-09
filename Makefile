SCRIPTS_FOLDER = scripts
DEPENDENCIES_FOLDER = Dependencies

COMPILER_FOLDER = $(DEPENDENCIES_FOLDER)/kotlinc/bin
COMPILER = $(COMPILER_FOLDER)/kotlinc
JUNIT_FOLDER = $(DEPENDENCIES_FOLDER)/junit/

SRC_FOLDER = src/main/kotlin
TEST_FOLDER = src/test/kotlin

NAME = computorV2

SRC_COMPUTATION =		$(SRC_FOLDER)/computation/polishnotation/extensions/CheckingOperand.kt \
						$(SRC_FOLDER)/computation/polishnotation/extensions/Compute.kt \
						$(SRC_FOLDER)/computation/polishnotation/extensions/GetOperandLastIndex.kt \
						$(SRC_FOLDER)/computation/polishnotation/CalcPolishNotation.kt \
						$(SRC_FOLDER)/computation/polishnotation/ConvertToPolishNotation.kt \
						$(SRC_FOLDER)/computation/SampleMath.kt

SRC_COMPUTOR_V1 =		$(SRC_FOLDER)/computorv1/calculations/CalculateSolutions.kt \
						$(SRC_FOLDER)/computorv1/models/Discriminant.kt \
						$(SRC_FOLDER)/computorv1/models/PolynomialTerm.kt \
						$(SRC_FOLDER)/computorv1/output/ok/GetOkOutput.kt \
						$(SRC_FOLDER)/computorv1/output/ok/GetSolutions.kt \
						$(SRC_FOLDER)/computorv1/parser/extensions/FindMaxDegree.kt \
						$(SRC_FOLDER)/computorv1/parser/extensions/PutSpaces.kt \
						$(SRC_FOLDER)/computorv1/parser/extensions/SimplifyPolynomial.kt \
						$(SRC_FOLDER)/computorv1/parser/extensions/ToPolynomialList.kt \
						$(SRC_FOLDER)/computorv1/parser/extensions/ToPolynomialTerm.kt \
						$(SRC_FOLDER)/computorv1/parser/ParserMain.kt \
						$(SRC_FOLDER)/computorv1/ComputorV1Main.kt

SRC_GLOBAL_EXTENSIONS =	$(SRC_FOLDER)/globalextensions/DataSetExtensions.kt \
						$(SRC_FOLDER)/globalextensions/NumberMathExtensions.kt

SRC_MODELS_DATASET =	$(SRC_FOLDER)/models/dataset/numeric/Complex.kt \
                    	$(SRC_FOLDER)/models/dataset/numeric/Numeric.kt \
                    	$(SRC_FOLDER)/models/dataset/numeric/SetNumber.kt \
                    	$(SRC_FOLDER)/models/dataset/wrapping/Brackets.kt \
                    	$(SRC_FOLDER)/models/dataset/wrapping/Fraction.kt \
                    	$(SRC_FOLDER)/models/dataset/wrapping/FunctionStack.kt \
                    	$(SRC_FOLDER)/models/dataset/wrapping/Wrapping.kt \
                    	$(SRC_FOLDER)/models/dataset/DataSet.kt \
                    	$(SRC_FOLDER)/models/dataset/Function.kt \
                    	$(SRC_FOLDER)/models/dataset/Matrix.kt

SRC_MODELS_EXCEPTION =	$(SRC_FOLDER)/models/exceptions/computorv1/ArgumentException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv1/ComputorV1Exception.kt \
						$(SRC_FOLDER)/models/exceptions/computorv1/NumberException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv1/PolynomialException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv1/SignException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv1/SolutionException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv2/calcexception/variable/FractionCalculationException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv2/calcexception/variable/MatrixCalculationException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv2/calcexception/variable/VariableCalculationException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv2/calcexception/CalculationException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv2/parserexception/sign/QuestionMarkException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv2/parserexception/variable/FunctionException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv2/parserexception/variable/MatrixException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv2/parserexception/variable/NumericException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv2/parserexception/variable/VariableParserException.kt \
						$(SRC_FOLDER)/models/exceptions/computorv2/ComputorV2Exception.kt \
						$(SRC_FOLDER)/models/exceptions/globalexceptions/parser/ParserException.kt \
						$(SRC_FOLDER)/models/exceptions/globalexceptions/parser/SignException.kt \
						$(SRC_FOLDER)/models/exceptions/globalexceptions/CalculationException.kt \
						$(SRC_FOLDER)/models/exceptions/ComputorException.kt

SRC_MODELS = 			$(SRC_MODELS_DATASET) $(SRC_MODELS_EXCEPTION) $(SRC_FOLDER)/models/Variables.kt

SRC_PARSER =			$(SRC_FOLDER)/parser/extensions/DeleteSpacesFromMultipleOperations.kt \
						$(SRC_FOLDER)/parser/extensions/GetStringWithFunctions.kt \
						$(SRC_FOLDER)/parser/extensions/PutSpaces.kt \
						$(SRC_FOLDER)/parser/extensions/UserCommands.kt \
						$(SRC_FOLDER)/parser/extensions/ValidateVariable.kt \
						$(SRC_FOLDER)/parser/getparseable/CheckIfFunction.kt \
						$(SRC_FOLDER)/parser/getparseable/CheckIfMatrix.kt \
						$(SRC_FOLDER)/parser/getparseable/GetParseableDataSet.kt \
						$(SRC_FOLDER)/parser/variable/numeric/ParseComplexFromListString.kt \
						$(SRC_FOLDER)/parser/variable/numeric/ParseNumericFromString.kt \
						$(SRC_FOLDER)/parser/variable/ParseFunctionFromListString.kt \
						$(SRC_FOLDER)/parser/variable/ParseMatrixFromListString.kt \
						$(SRC_FOLDER)/parser/ParserV2Main.kt

SRC =					$(SRC_COMPUTATION) $(SRC_COMPUTOR_V1) $(SRC_GLOBAL_EXTENSIONS) $(SRC_MODELS) $(SRC_PARSER) \
						$(SRC_FOLDER)/ComputorV2.kt



SRC_CALCULATION_FUNCTION_TEST = 		$(TEST_FOLDER)/calculationtests/variable/function/FunctionComplexTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/function/FunctionExpressionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/function/FunctionFunctionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/function/FunctionSetNumberTest.kt

SRC_CALCULATION_MATRIX_TEST = 			$(TEST_FOLDER)/calculationtests/variable/matrix/oldtests/MatrixDivisionExpressionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/matrix/oldtests/MatrixMinusExpressionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/matrix/oldtests/MatrixPlusExpressionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/matrix/oldtests/MatrixTimesExpressionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/matrix/MatrixBracketsTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/matrix/MatrixComplexTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/matrix/MatrixExpressionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/matrix/MatrixFractionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/matrix/MatrixFunctionStackTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/matrix/MatrixMatrixTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/matrix/MatrixPolynomialTermTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/matrix/MatrixSetNumberTest.kt \

SRC_CALCULATION_NUMERIC_TEST =			$(TEST_FOLDER)/calculationtests/variable/numeric/ComplexExpressionsTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/numeric/SetNumberExpressionsTest.kt

SRC_CALCULATION_BRACKETS_TEST =			$(TEST_FOLDER)/calculationtests/variable/wrapping/brackets/BracketsBracketsTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/brackets/BracketsComplexTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/brackets/BracketsExpressionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/brackets/BracketsFractionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/brackets/BracketsFunctionStackTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/brackets/BracketsFunctionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/brackets/BracketsMatrixTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/brackets/BracketsPolynomialTermTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/brackets/BracketsPolynomialTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/brackets/BracketsSetNumberTest.kt \

SRC_CALCULATION_FRACTION_TEST =			$(TEST_FOLDER)/calculationtests/variable/wrapping/fraction/FractionBracketsTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/fraction/FractionComplexTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/fraction/FractionExpressionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/fraction/FractionFractionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/fraction/FractionFunctionStackTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/fraction/FractionFunctionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/fraction/FractionMatrixTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/fraction/FractionPolynomialTermTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/fraction/FractionSetNumberTest.kt \

SRC_CALCULATION_FUNCTION_STACK_TEST =	$(TEST_FOLDER)/calculationtests/variable/wrapping/functionstack/FunctionStackBracketsTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/functionstack/FunctionStackComplexTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/functionstack/FunctionStackExpressionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/functionstack/FunctionStackFractionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/functionstack/FunctionStackFunctionStackTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/functionstack/FunctionStackFunctionTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/functionstack/FunctionStackMatrixTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/functionstack/FunctionStackPolynomialTermTest.kt \
										$(TEST_FOLDER)/calculationtests/variable/wrapping/functionstack/FunctionStackSetNumberTest.kt \

SRC_CALCULATION_TESTS =					$(SRC_CALCULATION_FUNCTION_TEST) $(SRC_CALCULATION_MATRIX_TEST) \
										$(SRC_CALCULATION_NUMERIC_TEST) $(SRC_CALCULATION_BRACKETS_TEST) \
										$(SRC_CALCULATION_FRACTION_TEST) $(SRC_CALCULATION_FUNCTION_STACK_TEST) \
										$(TEST_FOLDER)/calculationtests/PolishNotationTest.kt

SRC_COMPUTOR_V1_TEST =					$(TEST_FOLDER)/computorv1/ParserTests.kt \
										$(TEST_FOLDER)/computorv1/SolverTests.kt

SRC_PARSER_TESTS =						$(TEST_FOLDER)/parsertests/extensions/PutSpacesTest.kt \
										$(TEST_FOLDER)/parsertests/getparseable/GetParseableComplexTest.kt \
										$(TEST_FOLDER)/parsertests/getparseable/GetParseableFunctionTest.kt \
										$(TEST_FOLDER)/parsertests/getparseable/GetParseableMatrixTest.kt \
										$(TEST_FOLDER)/parsertests/getparseable/GetParseableSetNumberTest.kt \
										$(TEST_FOLDER)/parsertests/variable/numeric/ParseNumericFromStringTest.kt \
										$(TEST_FOLDER)/parsertests/variable/ParseMatrixFromListStringTest.kt \
										$(TEST_FOLDER)/parsertests/variable/ValidateVariableTest.kt \
										$(TEST_FOLDER)/parsertests/ParserMainTest.kt

SRC_TEST = 								$(SRC_CALCULATION_TESTS) $(SRC_COMPUTOR_V1_TEST) $(SRC_PARSER_TESTS) \
										$(TEST_FOLDER)/ComputorTest.kt \
										$(TEST_FOLDER)/MyAssertEquals.kt



BUILD_TEST = $(subst $(TEST_FOLDER)/,,$(SRC_TEST))

all:
	@bash $(SCRIPTS_FOLDER)/GetDependencies.bash
	@$(COMPILER) $(SRC) -include-runtime -d $(NAME).jar

tests:
	@bash $(SCRIPTS_FOLDER)/GetTestDependencies.bash
	@$(COMPILER) -cp $(JUNIT_FOLDER)/junit.jar:. $(SRC_TEST) $(SRC)
	@$(COMPILER_FOLDER)/kotlin -cp $(JUNIT_FOLDER)/junit.jar:$(JUNIT_FOLDER)/hamcrest-core.jar:. org.junit.runner.JUnitCore $(BUILD_TEST:.kt=.class)

clean:
	@rm -rf $(NAME).jar

fclean: clean
	@rm -rf $(DEPENDENCIES_FOLDER)

re: clean all