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
    fun `input string of two digit seperated by comma and and output division between tow`() {
        val actual : Double = 42.0
        val result= compose(compose(::division,::parseTwoStringToDouble),::splitString)
        assertThat(result("126,3"), equalTo(actual))
    }

    @Test
    fun `call composition with callable references`() {
        val oddLength = compose(::isOdd, ::length)
        val strings = listOf("a", "ab", "abc")
        assertThat(strings.filter(oddLength).size, equalTo(2))
    }
}

val parseToDouble : (Pair<String,String>) -> Pair<Double,Double> = {d -> d.first.toDouble() to  d.second.toDouble()}


val addByOne : (Int) -> Int = { x-> x + 1 }
val multiplyByThree : (Int) ->Int = { x-> x * 3 }

fun <A,B,C> compose (f1: (B) -> C, f2 : (A) -> B ) : (A) -> C {
    return {x -> f1(f2(x)) }
}

fun isOdd(x: Int) = x % 2 != 0
fun length(s: String) = s.length

fun splitString(s:String) : Pair<String,String> {
    return s.split(",").first() to s.split(",").last()
}
fun parseTwoStringToDouble(value:Pair<String,String>): Pair<Double,Double> {
    return value.first.toDouble() to value.second.toDouble()
}

fun division(doubleValue:Pair<Double,Double>) = doubleValue.first/doubleValue.second

