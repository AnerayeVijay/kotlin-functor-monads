package functor

import functor.Functor.Some
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class FunctorToValueTest {

    @Test
    fun `add three into value 2`() {
        val result = Some(2).map (::sumThree)
        assertThat(result,equalTo(Functor.some(5)))
    }
}

fun sumThree(n: Int) = n + 3


sealed class Functor<out A> {

    object None : Functor<Nothing>()

    data class Some<out A>(val value: A) : Functor<A>()

    inline infix fun <B> map(f: (A) -> B): Functor<B> = when (this) {
        is None -> this
        is Some -> Some(f(value))
    }
    companion object {
        fun <A> some(value: A): Functor<A> = Some(value)
    }

    infix fun <A, B> Functor<(A) -> B>.apply(f: Functor<A>): Functor<B> =
        when (this) {
            is None -> None
            is Some -> f.map(this.value)
        }

}
