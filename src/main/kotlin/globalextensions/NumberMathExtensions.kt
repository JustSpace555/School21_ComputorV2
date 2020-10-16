package globalextensions



fun Number.tryCastToInt(): Number =
		if (this.toDouble() - this.toInt() == 0.0)
			this.toInt()
		else
			this.toDouble()