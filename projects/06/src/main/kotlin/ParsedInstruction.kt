sealed interface ParsedInstruction

data class ParsedA(val instruction: String): ParsedInstruction
data class ParsedC(val instruction: Triple<String, String, String>): ParsedInstruction
data class ParsedL(val instruction: String): ParsedInstruction