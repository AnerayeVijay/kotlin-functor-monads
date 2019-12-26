## Kotlin Functor

### What is functor
 - a functor is simply something that can be mapped over.
 - To apply map to **lambda**
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
    - We need  Class Wrapped Around a Value
    - A functor is any class that holds data and can be mapped over.
        ```kotlin
          class Functor<T> {
              infix fun <B> map(function: (T) -> B ): Functor<B> {
                      return Functor(function(value))
                  }
          }   
        ```
 - Functions can be Functors too! When you use map on a function, you’re just doing function composition!
 - In category theory,  Functor is transformation between two category . For example, if there are `a` and `b` are two categories, Functor of a an b can then be written as f(a) : a -> b

### Why Are Functors Useful?
 - Functors are useful because they let us use map with collections, replacing for loops
 - Chaining: Because Functor.map returns another Functor with the result of the function passed to map I can chain multiple map functions together:
