object Assembler {
    fun assemble(lines: List<String>): List<String> {
        return lines
            .map { Parser.clean(it) }
            .filter { it.isNotEmpty() }
            .map { Parser.symbol(it) }
            .map { Binary.binary(it) }
    }
}