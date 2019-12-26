## Kotlin Functor

### What is functor
 - A Functor is basically a Lambda (value,class or function), but one that is wrapped in an object
 - A Class Wrapped Around a Value
 - A functor is any class that holds data and can be mapped over.
 - In category theory,  Functor is transformation between two category . For example, if there are `a` and `b` are two categories, Functor of a an b can then be written as f(a) : a -> b

### Why Are Functors Useful?
 - Functors are useful because they let us use map with collections, replacing for loops
 - Chaining: Because Functor.map returns another Functor with the result of the function passed to map I can chain multiple map functions together:
