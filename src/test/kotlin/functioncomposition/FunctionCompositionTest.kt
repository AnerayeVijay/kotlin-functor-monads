package functioncomposition

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class FunctionCompositionTest {

    @Test
    fun addOneAndMultiplyBy3UsingComposition() {
        val addOneThenMul3 = compose(multiplyByThree, addByOne)
        assertThat(addOneThenMul3(10),equalTo(33))
    }

    @Test
    fun `input string of two digit seperated by comma and and output divisision between tow`() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    @Test
    fun `call composition with callable references`() {
        val oddLength = compose(::isOdd, ::length)
        val strings = listOf("a", "ab", "abc")
        assertThat(strings.filter(oddLength).size, equalTo(2))
    }
}

val addByOne : (Int) -> Int = { x-> x + 1 }
val multiplyByThree : (Int) ->Int = { x-> x * 3 }
fun <A,B,C> compose (f1: (B) -> C, f2 : (A) -> B ) : (A) -> C {
    return {x -> f1(f2(x)) }
}

fun isOdd(x: Int) = x % 2 != 0
fun length(s: String) = s.length
