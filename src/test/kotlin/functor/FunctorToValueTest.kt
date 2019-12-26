package functor

import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class FunctorToValueTest {

    @Test
    fun `add three into value 2`() {
        val result = Option.Some(2).map (::sumThree)
        assertThat(result,equalTo(Option.some(5)))
    }
}

fun sumThree(n: Int) = n + 3


sealed class Option<out A> {
    object None : Option<Nothing>()
    data class Some<out A>(val value: A) : Option<A>()

    inline infix fun <B> map(f: (A) -> B): Option<B> = when (this) {
        is None -> this
        is Some -> Some(f(value))
    }
    companion object {
        fun <A> some(value: A): Option<A> = Some(value)
    }

}