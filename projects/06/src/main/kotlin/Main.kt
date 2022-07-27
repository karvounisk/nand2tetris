import java.io.File

fun main(args: Array<String>) {
    val path = args[0]
    val file = File(path)
    val lines = file.readLines()

    val binaryInstructions = Assembler.assemble(lines)

    val outputPath = path.substring(0, path.indexOfLast { it == '.' }) + ".hack"
    val outputFile = File(outputPath)
    outputFile.bufferedWriter().use { out ->
        binaryInstructions.forEach { line ->
            out.write(line)
            out.newLine()
        }
    }
}