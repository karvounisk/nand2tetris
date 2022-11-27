object Parser {
    fun clean(lines: List<String>): List<String> {
        return lines
            .map { it.split("//")[0].trim() }
            .filter { it.isNotEmpty() }
    }

    private fun instructionType(line: String): InstructionType {
        return if (line[0]=='@') InstructionType.A_INSTRUCTION
        else if (line[0]=='(') InstructionType.L_INSTRUCTION
        else InstructionType.C_INSTRUCTION
    }

    private fun symbolA(line: String): String  {
        return line.substring(1)
    }

    private fun symbolL(line: String): String  {
        return line.substring(1, line.length - 1)
    }

    private fun symbolC(line: String): Triple<String, String, String> {
        val (dest, rest) = if (line.contains('=')) line.split('=') else listOf("", line)
        val (comp, jump) = if (rest.contains(';')) rest.split(';') else listOf(rest, "")
        return Triple(dest, comp, jump)
    }

    fun symbol(line: String): ParsedInstruction {
        return when(instructionType(line)) {
            InstructionType.A_INSTRUCTION -> ParsedA(symbolA(line))
            InstructionType.C_INSTRUCTION -> ParsedC(symbolC(line))
            InstructionType.L_INSTRUCTION -> ParsedL(symbolL(line))
        }
    }

    fun findSymbols(lines: List<String>): MutableMap<String, Int> {
        val symbolTable: MutableMap<String, Int> = mutableMapOf()
        var lineIndex = 0

        lines
            .forEach { line ->
                when(val parsedLine = symbol(line)) {
                    is ParsedA -> lineIndex++
                    is ParsedC -> lineIndex++
                    is ParsedL -> symbolTable[parsedLine.instruction] = lineIndex
                }
            }

        return symbolTable
    }
}
