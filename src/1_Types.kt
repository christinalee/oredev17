import support.Dog
import support.PlatformTypes.getAListFromJava
import support.PlatformTypes.returnsNullList















/*
░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
░░░░░░░░████░░████░░░████░░░░░░░
░░░░░░░░████░░████░░░░░░░░░░░░░░
░░░░░░░░████░░████░░░████░░░░░░░
░░░░░░░░████▄▄████░░░████░░░░░░░
░░░░░░░░██████████░░░████░░░░░░░
░░░░░░░░████▀▀████░░░████░░░░░░░
░░░░░░░░████░░████░░░████░░░░░░░
░░░░░░░░████░░████░░░████░░░░░░░
░░░░░░░░████░░████░░░████░░░░░░░
░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░░
My name is Christina.
I'm an Engineer at Pinterest.
*/



















// The Kotlin type system is the basis
// for many really cool things we can do.















// A NOTE ABOUT SUBTYPES


// Subtype: A type A is a subtype of type B if A can be used anywhere B can
// E.g. Cat is a subtype of animal because anywhere you need an animal
// a cat will suffice

// In simple cases, the type can be used interchangeably with the class name
val str: String = "string" // <-- String is the class and the type

// But this quickly falls apart with nullables
val str2: String? = "string" // <-- String is the class, String? is the type


















// In the simplest cases, classes can create two types.
// With Collections, there can be near infinite

val coll1: List<Int> = TODO()
val coll2: List<String>? = TODO() // <-- List is the class
val coll3: List<List<Pair<Int, List<String>>>> = TODO()

























// Why is this cool? Well we have null safety, because you can
// only assign a value to something that is a super type of it

val num: Number = Int.MAX_VALUE // Number is a super type of Int
val nullableStr: String? = null
val nonNullableStr: String = nullableStr // Won't compile, because
                                         // String? is not a subtype
                                         // of String





























// Fast blast Nothing type rundown
fun notImplemented(): List<List<List<Dog>>> {
    // Why does this work?
    TODO()
}


fun unreachable(someNullable: String?) {
    // Won't ever assign if null
    val nonNull: String = someNullable ?: throw NotImplementedError()
    nonNull.last()

    val thing: Nothing = TODO() // Nothing type indicates
                                // the function never completes.
                                // That's why the assignment
                                // is unreachable
}













// Any type vs object

// Any is the default super type of all classes
// (in the same way unit is the default return type)
class Something
class Something2: Any()

fun primitiveNiceties() {
    val pseudo_primitive: Int = 1
    val anyField: Any = pseudo_primitive // Kotlin "primitives" are all
                                         // considered subtypes of Any
}










// Any corresponds to java.lang.Object (check the bytecode)
// but it's not the same as Object because it exposes only
// hashCode, equals, and toString methods.
fun any() {
    val value: Any = Any()
    value.hashCode()
    value.equals("other")
    value.toString()

    // Still have access to the object, but it's a warning
    (value as Object).notify()

    // As expected, Any is a subtype of Any?
    val any3: Any? = 2
    val any4: Any = any3 // But the reverse doesn't work
}
















// Unit

// "Void and unit are essentially the same concept.
// They're used to indicate that a function is called
// only for its side effects. The difference is that
// unit actually has a value. That means that unit functions
// can be used generically wherever a generic first
// class function is required. The C derived languages
// miss out by treating void functions as functions that
// don't return anything instead of as functions that
// don't return anything meaningful."
// - James Iry

fun defaultReturn(): Unit { }

val thing: Unit = Unit

// Why is this useful?
interface ResultOrError<out R: Any, out E: Exception> {
    // some apis return boolean == success
    // some api are side effects who's proper result type would be Unit
}
class ExampleResult : ResultOrError<Unit, UninitializedPropertyAccessException>


















/*


.-,--. .      .  ,_
 '|__/ |  ,-. |- |_ ,-. ,-. ,-,-.
 .|    |  ,-| |  |  | | |   | | |
 `'    `' `-^ `' |  `-' '   ' ' '
                 '

,--,--'
`- | . . ,-. ,-. ,-.
 , | | | | | |-' `-.
 `-' `-| |-' `-' `-'
      /| |
     `-' '


 */


















// REMEMBER THIS?

// In simple cases, the type can be used interchangeably with the class name
val str3: String = "string" // <-- String is the class and the type

// But this quickly falls apart with nullables
val str4: String? = "string" // <-- String is the class, String? is the type















// We are predominantly familiar with two types:

val nullable: String?
val notNullable: String


// But there is actually a third possibility:

// imNotSure: String!











/*

                WWWWWWWW
        WNXK0000000000000000KXNW
     NK0000KXNWW        WWNXK0000KN
  WXOO0XW                      WX0OOKW
 XOOXW                            WXOOX
KkKW       Hi, my name is          WKkK
xX         Platform Type.             Kx
xK                                     0x
xN         I don't know                Xx
xX         whether you are            Kx
OON        nullable or not.          NkO
N0kKW                              WKk0W
 WXOOKN                          NKOOXW
   WNkkN                    WNK0000XW
    NOOWWNK000000KKKKKKK0000000KNW
   WOk00OO0XNNXXKKKKKKKKKXNW
  WOox0
  WKK
  W

 */













val nullable__________Question: String?
val notNullable_______Statement_of_Fact: String

//  platformType______Screaming: String!

















/**
 *   NOTATION, NOT SYNTAX!
 *
 *   "... Platform types cannot be mentioned explicitly in the program,
 *   so there's no syntax for them in the language. Nevertheless,
 *   the compiler and IDE need to display them sometimes (in error
 *   messages, parameter info etc), so we have a mnemonic notation for them..."
 *
 */
















// Question: When would you not know the type of a value?

// Answer: When working in a language that doesn't
// require you to keep track of those types of things.
















fun ISpyAPlatformType() {

    val myJavaList = getAListFromJava()

    // Hmmm 🤔
    myJavaList?.add("Thing 1")
    myJavaList.add("Thing 2")


    // vs.

    val myKotlinList: MutableList<String>? = mutableListOf<String>()

    myKotlinList?.add("Thing 3")
    myKotlinList.add("Thing 4")

}

























// Why do we care?

/*
             N    N
 (•_•)       NN   N
 <)   )╯     N N  N
  /   \      N   NN
             N    N

             PPPPPP
  ( •_•)     P    P
 \(   (>     PPPPPP
  /   \      P
             P

             EEEEEEE
 (•_•)       E
 <)   )╯     EEEE
  /   \      E
             EEEEEEE

 */
























/*

             Actual value is:          Actual value is:
                  Null                     Not Null
         ---------------------------------------------------
         |                        |                        |
         |                        |                        |
         |                        |                        |
Type is: |                        |                        |
   T     |      CRASHHHHH!        |           OK           |
         |                        |                        |
         |                        |                        |
         |                        |                        |
         |                        |                        |
         ---------------------------------------------------
         |                        |                        |
         |                        |                        |
         |                        |                        |
Type is: |                        |                        |
   T?    |          OK            |      Redundant :(      |
         |                        |                        |
         |                        |                        |
         |                        |                        |
         |                        |                        |
         ---------------------------------------------------


 */


















// NPE's seem bad....
// Why would Jetbrains do this to us?


















/**
 * Language Principles:
 *
 * 1. Concise
 * 2. Pragmatic
 * 3. Safe
 * 4. Interoperable
 *
 */


















/**
 *  Three > One:
 *
 * 1. Concise                 |
 * 2. Pragmatic          <- versus ->           3. Safe
 * 4. Interoperable           |
 *
 */
















// So what do I do? I don't like crashes.


















/**
 * Platform types best practices:
 *
 * 1. Annotate your Java code
 */

fun callingFromJava1() {
    val list = getAListFromJava()

    list.plus("A String")
}











/**
 * Platform types best practices:
 *
 * If you can't do:
 * 1. Annotate your Java code
 *
 * Then at least do:
 * 2. Explicitly type code coming from Java
 */
















fun errorWithoutExplicitType() {
    val list = returnsNullList()
    list.size
/*       ^^^^
      _   ______  ______  */
    // | / / __ \/ ____/
   //  |/ / /_/ / __/
  // /|  / ____/ /___
 //_/ |_/_/   /_____/

}













fun errorWithExplicitType() {

    // Better Error:
    // "ILLEGAL STATE EXCEPTION: <insert name> must not be null"

    val list2: List<String> = returnsNullList()
    list.size

}















// Recap:

// The type system gives us cool functionality

// Unit, Any, and Nothing are interesting

// Platform types can be dangerous, but you have tools!






































