object Binary {
    private val CompMap = mapOf(
        "0" to Pair("0", "101010"),
        "1" to Pair("0", "111111"),
        "-1" to Pair("0", "111010"),
        "D" to Pair("0", "001100"),
        "A" to Pair("0", "110000"),
        "!D" to Pair("0", "001101"),
        "!A" to Pair("0", "110001"),
        "-D" to Pair("0", "001111"),
        "-A" to Pair("0", "110011"),
        "D+1" to Pair("0", "011111"),
        "A+1" to Pair("0", "110111"),
        "D-1" to Pair("0", "001110"),
        "A-1" to Pair("0", "110010"),
        "D+A" to Pair("0", "000010"),
        "D-A" to Pair("0", "010011"),
        "A-D" to Pair("0", "000111"),
        "D&A" to Pair("0", "000000"),
        "D|A" to Pair("0", "010101"),
        "M" to Pair("1", "110000"),
        "!M" to Pair("1", "110001"),
        "M+1" to Pair("1", "110111"),
        "M-1" to Pair("1", "110010"),
        "D+M" to Pair("1", "000010"),
        "D-M" to Pair("1", "010011"),
        "M-D" to Pair("1", "000111"),
        "D&M" to Pair("1", "000000"),
        "D|M" to Pair("1", "010101"),
    )

    private val DestMap = mapOf(
        "" to "000",
        "M" to "001",
        "D" to "010",
        "DM" to "011",
        "A" to "100",
        "AM" to "101",
        "AD" to "110",
        "ADM" to "111"
    )

    private val JumpMap = mapOf(
        "" to "000",
        "JGT" to "001",
        "JEQ" to "010",
        "JGE" to "011",
        "JLT" to "100",
        "JNE" to "101",
        "JLE" to "110",
        "JMP" to "111"
    )

    private fun binaryA(parsed: ParsedA): String {
       val binaryNumber = Integer.toBinaryString(parsed.instruction.toInt())
        return String.format("%16s", binaryNumber).replace(' ', '0')
    }

    private fun binaryC(parsed: ParsedC): String {
        val (dest, comp, jump) = parsed.instruction
        val (aDigit, compBinary) = CompMap[comp]!!.toList()
        val destBinary = DestMap[dest.toCharArray().sorted().joinToString("")]
        return "111${aDigit}${compBinary}$destBinary${JumpMap[jump]}"
    }

    private fun binaryL(parsed: ParsedL): String {
        return "" // TODO
    }

    fun binary(parsed: ParsedInstruction): String {
        return when(parsed) {
            is ParsedA -> binaryA(parsed)
            is ParsedC -> binaryC(parsed)
            is ParsedL -> binaryL(parsed)
        }
    }
}