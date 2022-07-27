object Parser {
    fun clean(line: String): String {
        return line.trim().split("//")[0]
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
}
