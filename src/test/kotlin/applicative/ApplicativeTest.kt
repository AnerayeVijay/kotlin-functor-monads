package applicative

import functor.Functor
import functor.Functor.Companion.some
import functor.Functor.None
import functor.Functor.Some
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class ApplicativeTest {

    @Test
    fun `apply function that takes two arguments to wrapped values`() {
        assertThat(Some(2) map ::curriedAddition apply Some(3), equalTo(some(5)))
        assertThat(Some { a: Int -> a + 3 } apply Some(2), equalTo(some(5)))
    }

    @Test
    fun `apply function that takes three arguments to wrapped value`() {
        assertThat( Some(3) map curry(::tripleProduct) apply Some(5) apply Some(4), equalTo((some(60))))
     }
}

infix fun <A, B> Functor<(A) -> B>.apply(f: Functor<A>): Functor<B> =
    when (this) {
        is None -> None
        is Some -> f.map(this.value)
    }

fun curriedAddition(a: Int) = { b: Int ->
    a + b
}

fun tripleProduct(a: Int, b: Int, c: Int) = a * b * c
fun <A, B, C, D> curry(f: (A, B, C) -> D): (A) -> (B) -> (C) -> D = { a -> { b -> { c -> f(a, b, c) } } }
