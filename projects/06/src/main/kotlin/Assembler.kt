object Assembler {
    fun assemble(lines: List<String>): List<String> {
        val linesCleaned = Parser.clean(lines)
        val symbolTable = Parser.findSymbols(linesCleaned)
        val binaryTranslator = BinaryTranslator(symbolTable)
        return binaryTranslator.translate(linesCleaned.map { Parser.symbol(it) })
    }
}