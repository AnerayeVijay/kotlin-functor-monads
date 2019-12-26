## Kotlin Functor

### What is functor
 - A functor is simply something that can be mapped over.
 - Functors are basically containers for types
 - Functor is a data type that defines how map applies on it
 - To apply map to **lambda**
    - ***Functions*** or ***Lambda*** can be Functors too! When you use map on a function, you’re just doing function composition!
    - We need to wrapped Lambda in an object so that we can apply map
        ```kotlin
         typealias IntFunction = (Int) -> Int
         // write map function on IntFunction
         infix fun IntFunction.map(g: IntFunction): IntFunction {
             return { x -> this(g(x)) }
         }
       
         val foo = { a: Int -> a + 2 } map { a: Int -> a + 3 }
         print(foo(10)) //print 15
        ```
 - To apply map function on any **Value** 
    - We need  Class Wrapped Around a ***Value***
    - A functor is any class that holds data and can be mapped over.
        ```kotlin
          class Functor<T> {
              infix fun <B> map(function: (T) -> B ): Functor<B> {
                      return Functor(function(value))
                  }
          }   
      
          fun inc(value: Int): Int = value + 1
      
          val increment = Functor(3).map {::inc} //OR
      
          val increment = Functor(3).map {it + 1}
      
        ```
 - In category theory,  Functor is transformation between two category . For example, if there are `a` and `b` are two categories, Functor of a an b can then be written as f(a) : a -> b

### Why Are Functors Useful?
 - Because we want to reuse code.
 - Functor generalizes how to map a function from one value to another. We used the Maybe type as an example to show why and how to use a generic mapMaybe function without having to deal with the empty case. And the mapMaybe is the implementation of Functor for Maybe type.
 - Functors are useful because they let us use map with collections, replacing for loops
 - Chaining: Because Functor.map returns another Functor with the result of the function passed to map I can chain multiple map functions together:

## Applicative
- Because Functor can only map a function which takes one argument. If we have a function that takes multiple arguments, we need Applicative.
- In Applicative our functions are wrapped in a context too!
- Applicative provides abstraction for how to apply a function that takes multiple arguments over multiple values. We used apply, which is the implementation of Applicative, as an example to show how to map a function over multiple Maybe values without dealing with the case of any value being Nothing.
- In Applicative,We can define an ```apply``` function for every type supporting Applicative, which knows how to apply a function wrapped in the context of the type to a value wrapped in the same context:

    ```kotlin
          infix fun <A, B> Option<(A) -> B>.apply(v: Option<A>): Option<B> =
            when (this) {
                is Option.None -> Option.None
                is Option.Some -> v.map(this.value)
            }
    ```
 -  If you look carefully you will see that our operator only works in this specific order: ```Option(function) apply Option(value)```
    - ***Apply a function that takes two arguments to two wrapped values?***
        ```kotlin
            fun curriedAddition(a: Int) = { b: Int ->
                a + b
            }
            Some(3) map ::curriedAddition map Some(2) // => COMPILER ERROR
        
            // Use applicative
            Some(3) map ::curriedAddition apply Some(2)
        ```
    
    - ***Apply triple product function:***
    
         ```kotlin
          fun tripleProduct(a: Int, b: Int, c: Int) = a * b * c
          fun <A, B, C, D> curry(f: (A, B, C) -> D): (A) -> (B) -> (C) -> D = { a -> { b -> { c -> f(a, b, c) } } }
          Some(3) map curry(::tripleProduct) apply Some(5) apply Some(4)
          // => Some(60)
        ```
## Monads
- Before we start with Monads, it is important to understand ***function composition*** in kotlin
  ### Function Composition
    - Function composition is a technique to build functions using existing functions
    - Takes the result of invoking the right-hand function as the parameter for the left-hand function.
        ```kotlin
      val add : (Int) -> Int = {x -> x + 1 }
      val mult : (Int) -> Int = { y -> y * 3}
      fun <A,B,C>composeF(f: (B) -> C, g: (A) ->B) {
        return { x -> f(g(x)) }
      }
      
      val addOneThenMul3 = composeF(::mul3, ::add1)
      
        fun isOdd(x: Int) = x % 2 != 0
        fun length(s: String) = s.length
        
        fun <A, B, C> compose(f: (B) -> C, g: (A) -> B): (A) -> C {
            return { x -> f(g(x)) }
        }
      
       val oddLength = compose(::isOdd, ::length)
       fun isOdd(x: Int) = x % 2 != 0
       fun length(s: String) = s.length
      
        ```
  ### Monads
  - Monads are the mechanism which makes automatic composition of special kids of functions
  - In another word, ***Monad*** is minimal amount of structure needed to overload functional composition in a way to perform extra computation on tht intermediate value 
  - Understand Monad with Use Case
  