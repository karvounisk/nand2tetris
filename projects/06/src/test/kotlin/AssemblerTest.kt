import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource

internal class AssemblerTest {

    private fun readLines(resourcePath: String): List<String> {
        return AssemblerTest::class.java.getResourceAsStream(resourcePath)?.bufferedReader()?.readLines()!!
    }

    @ParameterizedTest
    @ValueSource(strings = ["add/Add", "max/MaxL", "pong/PongL", "rect/RectL"])
    fun testWithoutSymbols(program: String) {
        val inputAsm = readLines("$program.asm")
        val expectedHack = readLines("$program.hack")
        val actualHack = Assembler.assemble(inputAsm)
        assertEquals(expectedHack, actualHack)
    }
}