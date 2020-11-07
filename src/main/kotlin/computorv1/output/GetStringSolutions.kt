package computorv1.output

import computorv1.models.Discriminant
import models.math.dataset.numeric.Numeric

internal fun getStringSolutions(answer: Triple<Discriminant, Numeric, Numeric?>, degree: Int): String {
	var output = ""

	if (degree == 2) output += "Discriminant: ${answer.first}\n"
	output += if (answer.third != null) {
		"The two solutions are:\n${answer.second}\n${answer.third}"
	} else {
		"The solution is: ${answer.second}"
	}

	return output
}