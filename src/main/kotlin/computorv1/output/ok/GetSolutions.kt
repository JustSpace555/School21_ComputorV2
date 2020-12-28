package computorv1.output.ok

import computorv1.models.Discriminant

fun getSolutions(answer: Triple<Discriminant, Any, Any?>, degree: Int): String {
	val sb = StringBuffer()

	if (degree == 2)
		sb.append("Discriminant: ${answer.first}\n")

	sb.append(
		when {
		answer.third != null -> "The two solutions are:\n${answer.second}\n${answer.third}\n"
		else -> "The solution is: ${answer.second}\n"
		}
	)

	return sb.toString()
}