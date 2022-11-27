import java.lang.Exception

class BinaryTranslator(private val symbolTable: MutableMap<String, Int>) {
    private var nextVarAddress = 16

    private val compMap = mapOf(
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

    private val destMap = mapOf(
        "" to "000",
        "M" to "001",
        "D" to "010",
        "DM" to "011",
        "A" to "100",
        "AM" to "101",
        "AD" to "110",
        "ADM" to "111"
    )

    private val jumpMap = mapOf(
        "" to "000",
        "JGT" to "001",
        "JEQ" to "010",
        "JGE" to "011",
        "JLT" to "100",
        "JNE" to "101",
        "JLE" to "110",
        "JMP" to "111"
    )

    private fun binaryString(number: Int): String {
        val binaryNumber = Integer.toBinaryString(number)
        return String.format("%16s", binaryNumber).replace(' ', '0')
    }

    private fun binaryA(parsed: ParsedA): String {
        try {
            val maybeAddressOrConstant = parsed.instruction.toIntOrNull()
            if (maybeAddressOrConstant != null)
                return binaryString(maybeAddressOrConstant)

            val maybeR = "R(1[0-5]|[0-9])".toRegex().find(parsed.instruction)
            if (maybeR != null)
                return binaryString(maybeR.groupValues[1].toInt())

            return binaryString(
                when (parsed.instruction) {
                    "SP" -> 0
                    "LCL" -> 1
                    "ARG" -> 2
                    "THIS" -> 3
                    "THAT" -> 4
                    "SCREEN" -> 16384
                    "KBD" -> 24576
                    else -> {
                        when (val address = symbolTable[parsed.instruction]) {
                            null -> {
                                symbolTable[parsed.instruction] = nextVarAddress
                                nextVarAddress++
                                nextVarAddress - 1
                            }
                            else -> address
                        }
                    }
                }
            )
        } catch (e: Exception) {
            throw Exception("A-instruction \"@${parsed.instruction}\" could not be translated to binary.")
        }
    }

    private fun binaryC(parsed: ParsedC): String {
        val (dest, comp, jump) = parsed.instruction
        val (aDigit, compBinary) = compMap[comp]!!.toList()
        val destBinary = destMap[dest.toCharArray().sorted().joinToString("")]
        return "111${aDigit}${compBinary}$destBinary${jumpMap[jump]}"
    }

    fun translate(parsed: List<ParsedInstruction>): List<String> {
        return parsed.flatMap {
            when(it) {
                is ParsedA -> listOf(binaryA(it))
                is ParsedC -> listOf(binaryC(it))
                is ParsedL -> emptyList()
            }
        }
    }
}