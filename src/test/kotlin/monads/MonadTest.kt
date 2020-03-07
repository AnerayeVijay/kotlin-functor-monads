package monads

import monads.Result.Companion.some
import monads.Result.Some
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test

class MonadTest {

    @Test
    fun `split two digit parse it and divide it`() {
        val  result = split("126,3").flatMap(parse).flatMap(divide)
        assertThat(result, equalTo(some(42.0)))
    }


}

val split : (String) -> Result<Pair<String,String>> = { s ->
    val listString = s.split(",")
    Some(listString.first() to listString.last())
}
val parse : (Pair<String,String>) -> Result<Pair<Double,Double>> = {pair -> Some(pair.first.toDouble() to pair.second.toDouble()) }

val divide : (Pair<Double,Double>) -> Result<Double> = {pair -> Some(pair.first.div(pair.second)) }

sealed class Result<out T> {
    object None : Result<Nothing>()
    data class Some<out T>(val value: T) : Result<T>()

    inline fun <B> flatMap(f: (T) -> Result<B>): Result<B> =
        when (this) {
            is None -> this
            is Some -> f(value)
        }

    companion object {
        fun <A> some(value: A): Result<A> = Result.Some(value)
    }
}