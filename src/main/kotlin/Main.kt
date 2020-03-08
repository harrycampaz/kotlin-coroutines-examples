import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
   // suspendExample2()
   // dispatcher()
    //launch()
   // exampleJobs()
   // Thread.sleep(5000)

   // asyncAwaitExample()

   // asyncAwaitDeferredExample()

//    println(measureTimeMillis {
//        asyncAwaitExample()
//    }.toString())
//
//    println(measureTimeMillis {
//        asyncAwaitDeferredExample()
//    }.toString())
//
//    println(measureTimeMillis {
//       withContextIO()
//    }.toString())

    cancelCoroutines()

}

fun longTaskMessage(msg: String){

    Thread.sleep(4000)
    println(msg + Thread.currentThread().name)

}

fun blockExample(){
    println("Tarea 1: " + Thread.currentThread().name)
    longTaskMessage("Tarea 2: ")
    println("Tarea 3: " + Thread.currentThread().name)
}

fun suspendExample(){
    println("Tarea 1: " + Thread.currentThread().name)

    // longTaskMessage("Tarea 2: ")

    runBlocking {
        delayCoroutine("Tarea 2: ")
    }


    println("Tarea 3: " + Thread.currentThread().name)
}

fun suspendExample2() = runBlocking{
    println("Tarea 1: " + Thread.currentThread().name)

    // longTaskMessage("Tarea 2: ")


    delayCoroutine("Tarea 2: ")


    println("Tarea 3: " + Thread.currentThread().name)
}
suspend fun delayCoroutine(msg: String){
    delay(4000)
    println(msg + Thread.currentThread().name)
}

fun dispatcher(){
    runBlocking {
        println("Hilo en que se ejecuta 1 ${Thread.currentThread().name}")
    }

    runBlocking(Dispatchers.Unconfined) {
        println("Hilo en que se ejecuta 2 ${Thread.currentThread().name}")
    }

    runBlocking(Dispatchers.Default) {
        println("Hilo en que se ejecuta 3 ${Thread.currentThread().name}")
    }

    runBlocking(Dispatchers.IO) {
        println("Hilo en que se ejecuta 4 ${Thread.currentThread().name}")
    }

    runBlocking(newSingleThreadContext("MiHilo")) {
        println("Hilo en que se ejecuta 5 ${Thread.currentThread().name}")
    }

//    runBlocking(Dispatchers.Main) {
//        println("Hilo en que se ejecuta 6 ${Thread.currentThread().name}")
//    }
}

fun  launch(){

    println("Tarea 1: " + Thread.currentThread().name)
    // longTaskMessage("Tarea 2: ")

    GlobalScope.launch {
        delayCoroutine("Tarea 2: ")
    }

    println("Tarea 3: " + Thread.currentThread().name)
}

fun exampleJobs(){
    println("Tarea 1: " + Thread.currentThread().name)
    // longTaskMessage("Tarea 2: ")

    val job = GlobalScope.launch {
        delayCoroutine("Tarea 2: ")
    }


    println("Tarea 3: " + Thread.currentThread().name)
    job.cancel()
}

suspend fun calcularHard(): Int{
    delay(2000)
    return  10
}

fun  asyncAwaitExample() = runBlocking {
    // println(System.currentTimeMillis().toString())

    val num1 = async {
        calcularHard()
    }.await()
    //println(System.currentTimeMillis().toString())
    val num2 = async { calcularHard() }.await()
   // println(System.currentTimeMillis().toString())

    println("Datos ${num1} + ${num2} = ${num1 + num2}")
}

fun  asyncAwaitDeferredExample() = runBlocking {
    // println(System.currentTimeMillis().toString())
    val num1: Deferred<Int> = async {
        calcularHard()
    }
   // println(System.currentTimeMillis().toString())

    val num2: Deferred<Int> = async {
        calcularHard()
    }

    //println(System.currentTimeMillis().toString())

    println("Datos ${num1.await()} + ${num2.await()} = ${num1.await() + num2.await()}")
}

fun withContextIO() = runBlocking {
    val num1 = withContext(Dispatchers.IO){
        calcularHard()
    }
    val num2 = withContext(Dispatchers.IO){
        calcularHard()
    }
    println("Datos ${num1} + ${num2} = ${num1 + num2}")
}

fun cancelCoroutines(){
    runBlocking {
        val job = launch {
            repeat(1000){
                i ->
                println("Jobs $i")
                kotlinx.coroutines.delay(500L)
            }
        }

        kotlinx.coroutines.delay(1400)
        job.cancel()
        println("main: Esperando")
    }
}