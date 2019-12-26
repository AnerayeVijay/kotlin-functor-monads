package functor

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class LambdaFunctorTest {

    infix fun IntFunction.map(g: IntFunction): IntFunction {
        return { x -> this(g(x)) }
    }

    val addTwoAndFive = { a: Int -> a + 2 } map { a: Int -> a + 3 }

    @Test
    fun `check map on lambda`() {
        assertThat(addTwoAndFive(10),equalTo(15))
    }

}
typealias IntFunction = (Int) -> Int
