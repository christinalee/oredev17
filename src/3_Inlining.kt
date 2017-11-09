import support.callBaz
import support.collectionSizeOrDefault




















// Types --> Generics
// Generics --> 🤔

















class SampleClass {
    fun sampleFun() {
        val array = arrayListOf<String>()

        // Types ✅
        // Generics ✅
        array.map {  }
    }
}














// Types ✅
// Generics ✅
// "Inline" 🤔

public inline fun <T, R> Iterable<T>.map2(transform: (T) -> R): List<R> {
    return mapTo(ArrayList<R>(collectionSizeOrDefault(10)), transform)
}














/*



What is the deal with
 _____      _ _       _
|_   _|    | (_)     (_)
  | | _ __ | |_ _ __  _ _ __   __ _
  | || '_ \| | | '_ \| | '_ \ / _` |
 _| || | | | | | | | | | | | | (_| |
 \___/_| |_|_|_|_| |_|_|_| |_|\__, |  ?
                               __/ |
                              |___/


*/















// Let's say we start with this example:

fun sampleFun() {
    // (1)
    val array = arrayListOf<Int>(1, 2, 3)

    // (2)
    val plusOne = array.map { it + 1 }

    // (3)
    println(plusOne)
}















// The definition of the map call looks like:

public inline fun <T, R, C : MutableCollection<in R>> Iterable<T>.mapTo(destination: C, transform: (T) -> R): C {

    // 👇👇👇 This is the part we care about
    for (item in this)
        destination.add(transform(item))
    return destination
    // ☝️☝️☝️

}










// If we look at the decompiled version of the example it -> it + 1 func
// we get something similar to:
fun decompiledMap() {
    val array = arrayListOf<Int>()
    val `$receiver$iv` = array
    val `destination$iv$iv` = arrayListOf(`$receiver$iv`.collectionSizeOrDefault(10))
    val var5 = `$receiver$iv`.iterator()

    while (var5.hasNext()) {
        val `item$iv$iv` = var5.next()
        val it = (`item$iv$iv` as Number).toInt()
        val var12 = it + 1
        `destination$iv$iv`.add(var12)
    }

    val var10000 = `destination$iv$iv` as List<*>
}










// Cleaned up:
fun decompiledMapCleanedUp(currentArray: ArrayList<Int>): List<Int> {
    val newList = arrayListOf(currentArray.size)                                // Please note that this line is not a correct translation. This is a list of [num] not a list of size Num
    val iterator = currentArray.iterator()

    while (iterator.hasNext()) {
        val item = iterator.next()
        newList.add(item + 1)
    }

    return newList
}













// So returning to this function and the question of "What is inlining?"
fun sampleFun3() {
    val array = arrayListOf<Int>(1, 2, 3)

    val plusOne = array.map { it + 1 }

    println(plusOne)
}















// We can answer that by taking a look at what gets generated!


















// What we get is:

fun sampleFunRawDecompile() {
    fun sampleFun() {
        val array = arrayListOf(*arrayOf(Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3)))
        val `$receiver$iv` = array as Iterable<Any>
        val `destination$iv$iv` = java.util.ArrayList<Int>(`$receiver$iv`.collectionSizeOrDefault<Any>(10)) as MutableCollection<Int>
        val var6 = `$receiver$iv`.iterator()

        while (var6.hasNext()) {
            val `item$iv$iv` = var6.next()
            val it = (`item$iv$iv` as Number).toInt()
            val var13 = it + 1
            `destination$iv$iv`.add(var13)
        }

        val plusOne = `destination$iv$iv`
        println(plusOne)
    }
}



/*
 __   __  _   _   _____   _   __  _
 \ \ / / | | | | /  __ \ | | / / | |
  \ V /  | | | | | /  \/ | |/ /  | |
   \ /   | | | | | |     |    \  | |
   | |   | |_| | | \__/\ | |\  \ |_|
   \_/    \___/   \____/ \_| \_/ (_)

 */










// Cleaned up:

fun sampleFunCleanedUp() {
    // Same as before
    val currentArray = arrayListOf(1, 2, 3)


    // 👇👇👇 LOOK! It's our map code! 👇👇👇
    val newList = arrayListOf(currentArray.size)                                // Please note that this line is not a correct translation. This is a list of [num] not a list of size Num
    val iterator = currentArray.iterator()

    while (iterator.hasNext()) {
        val item = iterator.next()
        newList.add(item + 1)
    }

    val plusOne = newList
    // 👆👆👆



    // Same as before
    println(plusOne)
}

















// Q: What does inlining do?
// A: Inject the code directly into the calling function.

















// So if we have:
inline fun sayHelloWorld() {
    println("Hello World! 👋")
}








// We go from:
fun foo() {
    sayHelloWorld()
}

// to:
fun foo2() {
    println("Hello World! 👋")
}



















// And it's an equal opportunity employer!
// (Specifically, inlining affects both lambdas and functions)

fun sayHelloWithLambda(lambda: () -> Unit) {
    println("Hello World! 👋")
    lambda()
}








// We go from:
fun bar() {
    sayHelloWithLambda(lambda = { for (i in 1..5) { println("Nice to meet you!") } })
}

// to:
fun bar2() {
    println("Hello World! 👋")
    for (i in 1..5) { println("Nice to meet you!") }
}














// But why would we want to do this?





















/*


______          __                                          _
| ___ \        / _|                                        | |
| |_/ /__ _ __| |_ ___  _ __ _ __ ___   __ _ _ __   ___ ___| |
|  __/ _ \ '__|  _/ _ \| '__| '_ ` _ \ / _` | '_ \ / __/ _ \ |
| | |  __/ |  | || (_) | |  | | | | | | (_| | | | | (_|  __/_|
\_|  \___|_|  |_| \___/|_|  |_| |_| |_|\__,_|_| |_|\___\___(_)



*/




















// AKA: Lambdas have overhead.


















 /* 👇👇 */

   inline fun myInlineFun(i: Int, s: String) {

   }














   // ✅ Who   (inline)
   // ✅ What  (injects code directly into method)
   // ✅ Where (method signature)
   // ✅ Why   (performance)
   // ✅ When  (when using lambdas)



















   // ❓ How





















   // How do you use inlining?
   // Are there any things you should look out for?


   /*

    __   _______ _____ _
    \ \ / /  ___/  ___| |
     \ V /| |__ \ `--.| |
      \ / |  __| `--. \ |
      | | | |___/\__/ /_|
      \_/ \____/\____/(_)


   */


















// For instance: You generally don't want to inline large lambdas.

// Why? Because you're copying a large block of code everywhere you call it!




















// But also:

inline fun <T, R> crossinline(crossinline transform: (T) -> R,
                              noinline transform2: (T) -> R) { }



/*    OH NO! What are these?!

       .----------.
      /  .-.  .-.  \
     /   | |  | |   \
     \   `-'  `-'  _/
     /\     .--.  / |
     \ |   /  /  / /
     / |  `--'  /\ \
      /`-------'  \ \

 */




















// The short of it?

// There's not enough time today to cover it!



















// However, their functionality is all documented here:
// https://kotlinlang.org/docs/reference/inline-functions.html



// Terms to google in relation to inline functions:
// 1. reified generics (combat type erasure, made possible by inline)
// 2. crossinline (non-local returns can cause problems)
// 3. noinline (some lambdas shouldn't be inlined)




// Be on the lookout for a talk in the future!















/*









 ________  __                            __
/        |/  |                          /  |
$$$$$$$$/ $$ |____    ______   _______  $$ |   __
   $$ |   $$      \  /      \ /       \ $$ |  /  |
   $$ |   $$$$$$$  | $$$$$$  |$$$$$$$  |$$ |_/$$/
   $$ |   $$ |  $$ | /    $$ |$$ |  $$ |$$   $$<
   $$ |   $$ |  $$ |/$$$$$$$ |$$ |  $$ |$$$$$$  \
   $$ |   $$ |  $$ |$$    $$ |$$ |  $$ |$$ | $$  |
   $$/    $$/   $$/  $$$$$$$/ $$/   $$/ $$/   $$/


                                                         Christina Lee
                               __                        @RunChristinaRun
                              /  |
 __    __   ______   __    __ $$ |
/  |  /  | /      \ /  |  /  |$$ |
$$ |  $$ |/$$$$$$  |$$ |  $$ |$$ |
$$ |  $$ |$$ |  $$ |$$ |  $$ |$$/
$$ \__$$ |$$ \__$$ |$$ \__$$ | __
$$    $$ |$$    $$/ $$    $$/ /  |
 $$$$$$$ | $$$$$$/   $$$$$$/  $$/
/  \__$$ |
$$    $$/
 $$$$$$/


























 */







